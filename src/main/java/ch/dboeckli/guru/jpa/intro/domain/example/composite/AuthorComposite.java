package ch.dboeckli.guru.jpa.intro.domain.example.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(NameId.class)
@Getter
@Setter
public class AuthorComposite {
    @Id
    private String firstName;

    @Id
    private String lastName;
    private String country;
}
