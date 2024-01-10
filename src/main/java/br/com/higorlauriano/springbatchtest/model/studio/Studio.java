package br.com.higorlauriano.springbatchtest.model.studio;

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
@ToString(callSuper = true)
@Entity
@Table(name = "studio", uniqueConstraints = {@UniqueConstraint(name = "ui_studio_001", columnNames = "name")})
@SQLInsert(sql = "INSERT INTO studio (id, name) SELECT ?2, ?1 WHERE NOT EXISTS (SELECT 0 FROM studio WHERE name = ?1)")
public class Studio extends AbstractEntity {

    @Column
    private String name;

}

