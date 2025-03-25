package ch.dboeckli.guru.jpa.intro.bootstrap;

import ch.dboeckli.guru.jpa.intro.domain.example.uuid.AuthorUuid;
import ch.dboeckli.guru.jpa.intro.domain.Book;
import ch.dboeckli.guru.jpa.intro.domain.example.uuid.BookUuid;
import ch.dboeckli.guru.jpa.intro.repository.AuthorUuidRepository;
import ch.dboeckli.guru.jpa.intro.repository.BookRepository;
import ch.dboeckli.guru.jpa.intro.repository.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    @Override
    public void run(String... args) {
        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();
        bookUuidRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        bookRepository.save(bookDDD);

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(book -> {
            log.info("Book Id: " + book.getId());
            log.info("Book Title: " + book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        authorUuidRepository.save(authorUuid);

        authorUuidRepository.findAll().forEach(authorUuidFound -> log.info("AuthorUuid: " + authorUuidFound));

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("All About UUIDs");
        bookUuidRepository.save(bookUuid);

        bookUuidRepository.findAll().forEach(bookUuidFound -> log.info("BookUuid: " + bookUuidFound));
    }
}
