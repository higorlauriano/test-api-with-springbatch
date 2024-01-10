package br.com.higorlauriano.springbatchtest.batch;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;
import br.com.higorlauriano.springbatchtest.repository.movie.MovieRepository;
import br.com.higorlauriano.springbatchtest.repository.producer.ProducerRepository;
import br.com.higorlauriano.springbatchtest.repository.studio.StudioRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BatchConfiguration {

    private final @NonNull MovieRepository movieRepository;
    private final @NonNull StudioRepository studioRepository;
    private final @NonNull ProducerRepository producerRepository;

    @Bean
    public Job setupDatabaseJob(Step insertProducerStep, Step insertStudioStep, Step insertMovieStep, JobRepository jobRepository) {

        return new JobBuilder("setupDatabaseJob", jobRepository)
                .start(insertProducerStep)
                .next(insertStudioStep)
                .next(insertMovieStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step insertMovieStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("insertMoviesStep", jobRepository)
                .<Map<String, String>, Movie>chunk(200, transactionManager)
                .reader(fileReader())
                .processor(movieProcessor())
                .writer(movieRepository::saveAll)
                .build();
    }

    @Bean
    public Step insertProducerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("insertProducersStep", jobRepository)
                .<Map<String, String>, List<Producer>>chunk(200, transactionManager)
                .reader(fileReader())
                .processor(producerProcessor())
                .writer(new ItemListWriter<>(producerRepository::saveAll))
                .build();
    }

    @Bean
    public Step insertStudioStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("insertMoviesStep", jobRepository)
                .<Map<String, String>, List<Studio>>chunk(200, transactionManager)
                .reader(fileReader())
                .processor(studioProcessor())
                .writer(new ItemListWriter<>(studioRepository::saveAll))
                .build();
    }

    @Bean
    public ItemReader<Map<String, String>> fileReader() {

        return new FlatFileItemReaderBuilder<Map<String, String>>()
                .name("reader")
                .resource(new FileSystemResource("./src/test/resources/data/movielist.csv"))
                .linesToSkip(1)
                .lineMapper((line, lineNumber) -> {
                    var map = new HashMap<String, String>();
                    var attributes = new String[]{"year", "title", "studios", "producers", "winner"};
                    var values = line.split(";");

                    for (int i = 0; i < values.length; i++) {
                        map.putIfAbsent(attributes[i], values[i]);
                    }

                    return map;
                })
                .build();
    }

    private ItemProcessor<Map<String, String>, Movie> movieProcessor() {
        return (Map<String, String> map) -> {

            var movie = new Movie();
            movie.setYear(Integer.parseInt(map.get("year")));
            movie.setTitle(map.get("title"));

            movie.setProducers(Stream.of(map.get("producers").split(" and "))
                    .map(String::trim)
                    .map(strProducer1 -> Stream.of(strProducer1.split(","))
                            .map(String::trim)
                            .map(producerRepository::findByName)
                            .toList())
                    .flatMap(Collection::stream)
                    .toList());

            movie.setStudios(Stream.of(map.get("studios").split(","))
                    .map(String::trim)
                    .map(studioRepository::findByName)
                    .toList());

            if (isNull(map.get("winner"))) {
                movie.setWinner(Boolean.FALSE);
            } else {
                movie.setWinner(map.get("winner").equals("yes"));
            }

            return movie;
        };
    }

    private ItemProcessor<Map<String, String>, List<Producer>> producerProcessor() {
        return (Map<String, String> map) -> Stream.of(map.get("producers").split(" and "))
                .map(String::trim)
                .map(strProducer1 -> Stream.of(strProducer1.split(","))
                        .map(String::trim)
                        .map(strProducer2 -> {
                            var producer = new Producer();
                            producer.setName(strProducer2);
                            return producer;
                        })
                        .toList())
                .flatMap(Collection::stream)
                .toList();
    }

    private ItemProcessor<Map<String, String>, List<Studio>> studioProcessor() {
        return (Map<String, String> map) -> Stream.of(map.get("studios").split(","))
                .map(String::trim)
                .map(strStudio -> {
                    var studio = new Studio();
                    studio.setName(strStudio);
                    return studio;
                })
                .toList();
    }

}
