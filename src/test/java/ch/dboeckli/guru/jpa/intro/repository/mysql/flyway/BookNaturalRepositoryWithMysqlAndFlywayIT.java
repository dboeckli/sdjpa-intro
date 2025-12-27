package ch.dboeckli.guru.jpa.intro.repository.mysql.flyway;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.example.natural.BookNatural;
import ch.dboeckli.guru.jpa.intro.repository.BookNaturalRepository;
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
class BookNaturalRepositoryWithMysqlAndFlywayIT {

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Test
    void bookNaturalTest() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("My Book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural fetched = bookNaturalRepository.getReferenceById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

}