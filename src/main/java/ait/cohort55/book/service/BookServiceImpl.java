package ait.cohort55.book.service;

import ait.cohort55.book.dao.AuthorRepository;
import ait.cohort55.book.dao.BookRepository;
import ait.cohort55.book.dao.PublisherRepository;
import ait.cohort55.book.dto.AuthorDto;
import ait.cohort55.book.dto.BookDto;
import ait.cohort55.book.dto.exception.ConflictException;
import ait.cohort55.book.dto.exception.NotFoundException;
import ait.cohort55.book.model.Author;
import ait.cohort55.book.model.Book;
import ait.cohort55.book.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void addBook(BookDto bookDto) {
        if (bookRepository.existsById(bookDto.getIsbn())) {
            throw new ConflictException("Book with ISBN " + bookDto.getIsbn() + " already exists");
        }
        // publisher
        Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
                .orElseGet(() -> publisherRepository.save(new Publisher(bookDto.getPublisher())));
        // authors
        Set<Author> authors = bookDto.getAuthors().stream()
                .map(a -> authorRepository.findById(a.getName())
                        .orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
                .collect(Collectors.toSet());
        Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
        bookRepository.save(book);
    }

    @Override
    public BookDto findBookByIsbn(String isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(NotFoundException::new);
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookDto deleteBookByIsbn(String isbn) {
        return null;
    }

    @Override
    public BookDto updateBookTitle(String isbn, String title) {
        return null;
    }

    @Override
    public Iterable<BookDto> findBooksByAuthor(String authorName) {
        return null;
    }

    @Override
    public Iterable<BookDto> findBooksByPublisher(String publisherName) {
        return null;
    }

    @Override
    public Iterable<AuthorDto> findBookAuthors(String isbn) {
        return null;
    }

    @Override
    public Iterable<String> findPublishersByAuthor(String authorName) {
        return null;
    }

    @Override
    public AuthorDto deleteAuthor(String authorName) {
        return null;
    }
}
