package br.com.higorlauriano.springbatchtest.commons;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.nonNull;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    public Boolean equals(AbstractEntity other) {

        return nonNull(other)
                && nonNull(this.getId())
                && this.getId().equals(other.getId());
    }

}
