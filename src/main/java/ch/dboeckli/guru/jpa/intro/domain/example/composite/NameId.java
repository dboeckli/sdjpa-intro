package ch.dboeckli.guru.jpa.intro.domain.example.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class NameId implements Serializable {
    private String firstName;
    private String lastName;
}
