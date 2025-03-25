package ch.dboeckli.guru.jpa.intro.domain.example.natural;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BookNatural {

    @Id
    private String title;

    private String isbn;
    private String publisher;
}
