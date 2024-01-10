package br.com.higorlauriano.springbatchtest.repository.studio;

import org.springframework.data.jpa.repository.Query;

import br.com.higorlauriano.springbatchtest.commons.AbstractRepository;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;

public interface StudioRepository extends AbstractRepository<Studio> {

    @Query("select s from Studio s where s.name = ?1")
    Studio findByName(String name);

}
