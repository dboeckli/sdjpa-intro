package ch.dboeckli.guru.jpa.intro.repository.mysql.liquibase;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.example.uuid.AuthorUuid;
import ch.dboeckli.guru.jpa.intro.repository.AuthorUuidRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test_mysql_with_liquibase")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // to assure that it is not replaced with h2
@Import(DataInitializer.class)
@Slf4j
class AuthorUuidRepositoryWithMysqlAndLiquibaseIT {

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