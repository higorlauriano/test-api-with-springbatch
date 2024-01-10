package br.com.higorlauriano.springbatchtest.controller.movie;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.higorlauriano.springbatchtest.commons.AbstractCrudController;
import br.com.higorlauriano.springbatchtest.model.movie.Movie;

@RestController
@RequestMapping("/movie")
public class MovieController extends AbstractCrudController<Movie> {

}
