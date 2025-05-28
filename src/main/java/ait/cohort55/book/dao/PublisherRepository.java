package ait.cohort55.book.dao;

import ait.cohort55.book.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
    Stream<Publisher> findDistinctPublisherByBooksAuthorsNameIgnoreCase(String authorName);
}
