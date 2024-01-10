package br.com.higorlauriano.springbatchtest.commons;

import static java.util.Objects.nonNull;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    public boolean equals(AbstractEntity other) {

        return nonNull(other)
                && nonNull(this.getId())
                && this.getId().equals(other.getId());
    }
}
