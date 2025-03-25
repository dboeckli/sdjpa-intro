package ch.dboeckli.guru.jpa.intro.repository.h2;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.example.uuid.AuthorUuid;
import ch.dboeckli.guru.jpa.intro.repository.AuthorUuidRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
// we are using the h2 in compatible mode with mysql. to assure that it is not replaced with h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataInitializer.class)
@Slf4j
class AuthorUuidRepositoryWithH2Test {

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Test
    void testJpaTestSplice() {
        long countBefore = authorUuidRepository.count();

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Raul");
        authorUuid.setLastName("Pedro");
        authorUuidRepository.save(authorUuid);

        long countAfter = authorUuidRepository.count();

        assertEquals(1, countBefore);
        assertThat(countBefore).isLessThan(countAfter);

        authorUuidRepository.findAll().forEach(authorUuidFound -> log.info("AuthorUuid: " + authorUuidFound));
    }
}