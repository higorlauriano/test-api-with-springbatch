package br.com.higorlauriano.springbatchtest.model.producer;

import org.hibernate.annotations.SQLInsert;

import br.com.higorlauriano.springbatchtest.commons.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "producer", uniqueConstraints = {@UniqueConstraint(name = "ui_producer_001", columnNames = "name")})
@SQLInsert(sql = "INSERT INTO producer (id, name) SELECT ?2, ?1 WHERE NOT EXISTS (SELECT 0 FROM producer WHERE name = ?1)")
public class Producer extends AbstractEntity {

    @Column
    private String name;

}

