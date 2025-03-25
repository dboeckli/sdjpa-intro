package ch.dboeckli.guru.jpa.intro.repository;

import ch.dboeckli.guru.jpa.intro.domain.example.uuid.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
