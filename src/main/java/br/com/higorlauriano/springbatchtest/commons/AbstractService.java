package br.com.higorlauriano.springbatchtest.commons;

import br.com.higorlauriano.springbatchtest.commons.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class AbstractService<T extends AbstractEntity> {

    @Autowired
    private AbstractRepository<T> abstractRepository;

    public Optional<T> findOne(Long id) {

        return abstractRepository.findById(id);
    }

    public Page<T> findPaged(Integer page, Integer size) {

        return abstractRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public T save(T object) {

        return abstractRepository.saveAndFlush(object);
    }

    public T edit(Long id, T object) {

        if (abstractRepository.existsById(id)) {
            object.setId(id);
            return abstractRepository.saveAndFlush(object);
        }

        throw new CustomException("Object with ID " + id + " not found to update");
    }

    public void delete(Long id) {

        if (abstractRepository.existsById(id)) {
            abstractRepository.deleteById(id);
            return;
        }

        throw new CustomException("Object with ID " + id + " not found to delete");
    }

}
