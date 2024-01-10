package br.com.higorlauriano.springbatchtest.repository.producer;

import org.springframework.data.jpa.repository.Query;

import br.com.higorlauriano.springbatchtest.commons.AbstractRepository;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;

public interface ProducerRepository extends AbstractRepository<Producer> {

    @Query("select p from Producer p where p.name = ?1")
    Producer findByName(String name);

}
