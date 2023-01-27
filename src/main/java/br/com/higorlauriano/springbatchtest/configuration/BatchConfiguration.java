package br.com.higorlauriano.springbatchtest.configuration;

import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static java.util.Objects.isNull;

@Configuration
public class BatchConfiguration {

    @Bean
    public Job job(Step step, JobRepository jobRepository) {

        return new JobBuilder("setupDatabaseJob", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(ItemReader<Movie> reader, ItemWriter<Movie> writer, JobRepository jobRepository,
                    PlatformTransactionManager transactionManager) {

        return new StepBuilder("insertDataFromCsvFileStep", jobRepository)
                .<Movie, Movie>chunk(200, transactionManager)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public ItemWriter<Movie> writer(DataSource dataSource) {

        var sqlInsert = """
                INSERT INTO
                    movie (year, title, studio, producers, winner)
                VALUES
                    (:year, :title, :studio, :producers, :winner)
                """;

        return new JdbcBatchItemWriterBuilder<Movie>()
                .dataSource(dataSource)
                .sql(sqlInsert)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public ItemReader<Movie> reader() {

        var lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("year", "title", "studio", "producers", "winner");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4);
        lineTokenizer.setDelimiter(";");

        return new FlatFileItemReaderBuilder<Movie>()
                .name("reader")
                .resource(new FileSystemResource("./src/main/resources/data/movielist.csv"))
                .targetType(Movie.class)
                .linesToSkip(1)
                .lineTokenizer(lineTokenizer)
                .build();
    }

    private ItemProcessor<Movie, Movie> processor() {
        return (Movie movie) -> {

            if (isNull(movie.getWinner())) {
                movie.setWinner(Boolean.FALSE);
            }

            System.out.println("Inserting movie: " + movie);

            return movie;
        };
    }

}
