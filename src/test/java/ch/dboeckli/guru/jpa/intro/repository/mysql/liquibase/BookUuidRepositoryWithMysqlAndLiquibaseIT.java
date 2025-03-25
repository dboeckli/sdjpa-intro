package ch.dboeckli.guru.jpa.intro.repository.mysql.liquibase;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.example.uuid.BookUuid;
import ch.dboeckli.guru.jpa.intro.repository.BookUuidRepository;
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
class BookUuidRepositoryWithMysqlAndLiquibaseIT {

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Test
    void testJpaTestSplice() {
        long countBefore = bookUuidRepository.count();

        BookUuid bookUuid = new BookUuid();
        bookUuid.setIsbn("978-3-16-148410-0");
        bookUuid.setTitle("Clean Code");
        bookUuid.setPublisher("Prentice Hall");
        bookUuidRepository.save(bookUuid);

        long countAfter = bookUuidRepository.count();

        assertEquals(1, countBefore);
        assertThat(countBefore).isLessThan(countAfter);
    }
}