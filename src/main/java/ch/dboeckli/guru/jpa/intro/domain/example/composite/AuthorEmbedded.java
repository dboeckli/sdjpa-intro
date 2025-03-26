package ch.dboeckli.guru.jpa.intro.domain.example.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AuthorEmbedded {

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }

    @EmbeddedId
    private NameId nameId;

    private String country;
}
