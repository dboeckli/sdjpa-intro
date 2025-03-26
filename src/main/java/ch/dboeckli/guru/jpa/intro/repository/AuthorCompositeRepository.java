package ch.dboeckli.guru.jpa.intro.repository;

import ch.dboeckli.guru.jpa.intro.domain.example.composite.AuthorComposite;
import ch.dboeckli.guru.jpa.intro.domain.example.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
