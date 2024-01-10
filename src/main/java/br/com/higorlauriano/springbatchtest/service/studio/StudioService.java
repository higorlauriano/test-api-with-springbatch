package br.com.higorlauriano.springbatchtest.service.studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.higorlauriano.springbatchtest.commons.AbstractService;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;
import br.com.higorlauriano.springbatchtest.repository.studio.StudioRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudioService extends AbstractService<Studio> {

    private final @NonNull StudioRepository studioRepository;

    public Studio findByName(String name) {
        return studioRepository.findByName(name);
    }
}
