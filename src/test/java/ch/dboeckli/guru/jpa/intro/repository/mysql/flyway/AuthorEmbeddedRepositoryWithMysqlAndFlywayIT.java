package ch.dboeckli.guru.jpa.intro.repository.mysql.flyway;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.example.composite.AuthorEmbedded;
import ch.dboeckli.guru.jpa.intro.domain.example.composite.NameId;
import ch.dboeckli.guru.jpa.intro.repository.AuthorEmbeddedRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test_mysql_with_flyway")
@DirtiesContext
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // to assure that it is not replaced with h2
@Import(DataInitializer.class)
@Slf4j
class AuthorEmbeddedRepositoryWithMysqlAndFlywayIT {

    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;

    @Test
    void authorEmbeddedTest() {
        NameId nameId = new NameId("John", "T");
        AuthorEmbedded authorEmbedded = new AuthorEmbedded(nameId);

        AuthorEmbedded saved = authorEmbeddedRepository.save(authorEmbedded);
        assertThat(saved).isNotNull();

        AuthorEmbedded fetched = authorEmbeddedRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }

}
