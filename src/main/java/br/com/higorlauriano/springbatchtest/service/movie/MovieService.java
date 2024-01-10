package br.com.higorlauriano.springbatchtest.service.movie;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.higorlauriano.springbatchtest.commons.AbstractService;
import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;
import br.com.higorlauriano.springbatchtest.service.producer.ProducerService;
import br.com.higorlauriano.springbatchtest.service.studio.StudioService;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService extends AbstractService<Movie> {

    private final @NonNull ProducerService producerService;
    private final @NonNull StudioService studioService;

    @Override
    public Movie save(Movie movie) {

        adjustProducersRelationship(movie);
        adjustStudiosRelationship(movie);

        return super.save(movie);
    }

    @Override
    public Movie edit(Long id, Movie movie) {

        adjustProducersRelationship(movie);
        adjustStudiosRelationship(movie);

        return super.edit(id, movie);
    }

    private void adjustProducersRelationship(Movie movie) {

        if (isNotEmpty(movie.getProducers())) {

            List<Producer> existingProducers = movie.getProducers().stream()
                    .map(producer -> producerService.findByName(producer.getName()))
                    .filter(Objects::nonNull)
                    .toList();

            List<Producer> producersToPersist = new ArrayList<>();

            for (Producer producer : movie.getProducers()) {

                existingProducers.stream()
                        .filter(producer1 -> producer1.getName().equals(producer.getName()))
                        .findFirst()
                        .ifPresentOrElse(producersToPersist::add, () -> producersToPersist.add(producer));
            }

            movie.setProducers(producersToPersist);
        }
    }

    private void adjustStudiosRelationship(Movie movie) {

        if (isNotEmpty(movie.getStudios())) {

            List<Studio> existingStudios = movie.getStudios().stream()
                    .map(studio -> studioService.findByName(studio.getName()))
                    .filter(Objects::nonNull)
                    .toList();

            List<Studio> studiosToPersist = new ArrayList<>();

            for (Studio studio : movie.getStudios()) {

                existingStudios.stream()
                        .filter(studio1 -> studio1.getName().equals(studio.getName()))
                        .findFirst()
                        .ifPresentOrElse(studiosToPersist::add, () -> studiosToPersist.add(studio));
            }

            movie.setStudios(studiosToPersist);
        }
    }
}
