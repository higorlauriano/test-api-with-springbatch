package br.com.higorlauriano.springbatchtest.controller;

import br.com.higorlauriano.springbatchtest.commons.AbstractCrudController;
import br.com.higorlauriano.springbatchtest.model.movie.Movie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController extends AbstractCrudController<Movie> {

}
