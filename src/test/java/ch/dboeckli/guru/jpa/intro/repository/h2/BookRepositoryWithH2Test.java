package ch.dboeckli.guru.jpa.intro.repository.h2;

import ch.dboeckli.guru.jpa.intro.bootstrap.DataInitializer;
import ch.dboeckli.guru.jpa.intro.domain.Book;
import ch.dboeckli.guru.jpa.intro.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
// we are using the h2 in compatible mode with mysql. to assure that it is not replaced with h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataInitializer.class)
class BookRepositoryWithH2Test {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();

        bookRepository.save(new Book("My Book", "1235555", "Self", null));

        long countAfter = bookRepository.count();

        assertEquals(2, countBefore);
        assertThat(countBefore).isLessThan(countAfter);
    }

}