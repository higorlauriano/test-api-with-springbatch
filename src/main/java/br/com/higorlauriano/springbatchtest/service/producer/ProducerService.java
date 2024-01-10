package br.com.higorlauriano.springbatchtest.service.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.higorlauriano.springbatchtest.commons.AbstractService;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;
import br.com.higorlauriano.springbatchtest.repository.producer.ProducerRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerService extends AbstractService<Producer> {

    private final @NonNull ProducerRepository producerRepository;

    public Producer findByName(String name) {
        return producerRepository.findByName(name);
    }

}
