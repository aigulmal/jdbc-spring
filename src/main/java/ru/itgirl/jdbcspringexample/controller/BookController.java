package ru.itgirl.jdbcspringexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itgirl.jdbcspringexample.model.Book;
import ru.itgirl.jdbcspringexample.repository.BookRepository;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private static BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book/all")
    public List<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }
    @GetMapping("/book/{id}")
    public static Book getBookById(@PathVariable("id") Long id) {
        System.out.println("mapping"+id);
        System.out.println(bookRepository.findBookById(id));
        return bookRepository.findBookById(id);
    }
}