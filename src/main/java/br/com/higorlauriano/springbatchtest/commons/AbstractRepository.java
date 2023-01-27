package br.com.higorlauriano.springbatchtest.commons;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {

}
