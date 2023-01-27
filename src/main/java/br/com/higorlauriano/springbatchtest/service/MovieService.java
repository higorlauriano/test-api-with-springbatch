package br.com.higorlauriano.springbatchtest.service;

import br.com.higorlauriano.springbatchtest.commons.AbstractService;
import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService extends AbstractService<Movie> {

}
