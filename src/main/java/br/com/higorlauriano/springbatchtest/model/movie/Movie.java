package br.com.higorlauriano.springbatchtest.model.movie;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import br.com.higorlauriano.springbatchtest.commons.AbstractEntity;
import br.com.higorlauriano.springbatchtest.model.producer.Producer;
import br.com.higorlauriano.springbatchtest.model.studio.Studio;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "movie")
public class Movie extends AbstractEntity {

    @Column
    private Integer year;

    @Column
    private String title;

    @Column
    private Boolean winner;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "movie_producer")
    @SQLDelete(sql = "DELETE FROM movie_producer WHERE movie_id = ? AND producer_id = ?")
    private List<Producer> producers = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "movie_studio")
    @SQLDelete(sql = "DELETE FROM movie_studio WHERE movie_id = ? AND studio_id = ?")
    private List<Studio> studios = new ArrayList<>();

}

