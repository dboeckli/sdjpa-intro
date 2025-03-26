package ch.dboeckli.guru.jpa.intro.repository;

import ch.dboeckli.guru.jpa.intro.domain.example.composite.AuthorEmbedded;
import ch.dboeckli.guru.jpa.intro.domain.example.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
