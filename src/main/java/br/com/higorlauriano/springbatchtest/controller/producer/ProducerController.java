package br.com.higorlauriano.springbatchtest.controller.producer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.higorlauriano.springbatchtest.commons.AbstractCrudController;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;

@RestController
@RequestMapping("/producer")
public class ProducerController extends AbstractCrudController<Producer> {

}
