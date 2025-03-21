package ch.dboeckli.guru.jpa.intro;

import ch.dboeckli.guru.jpa.intro.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
@Slf4j
class Spring6ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void contextLoads() {
        log.info("Testing Spring 6 Application {}", applicationContext.getApplicationName());

        assertNotNull(applicationContext, "Application context should not be null");
    }

    @Test
    void testBookRepository() {
        assertEquals(2, bookRepository.count());
    }

}
