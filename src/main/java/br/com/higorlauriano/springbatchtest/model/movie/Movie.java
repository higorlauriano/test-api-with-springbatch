package br.com.higorlauriano.springbatchtest.model.movie;

import br.com.higorlauriano.springbatchtest.commons.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Movie extends AbstractEntity {

    @Column
    private Long year;

    @Column
    private String title;

    @Column
    private String producers;

    @Column
    private String studio;

    @Column
    private Boolean winner;

}

