package br.com.higorlauriano.springbatchtest.controller.studio;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.higorlauriano.springbatchtest.commons.AbstractCrudController;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;

@RestController
@RequestMapping("/studio")
public class StudioController extends AbstractCrudController<Studio> {

}
