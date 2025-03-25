package ch.dboeckli.guru.jpa.intro.repository;

import ch.dboeckli.guru.jpa.intro.domain.example.uuid.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
