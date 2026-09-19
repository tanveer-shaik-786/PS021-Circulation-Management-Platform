package com.bibliotech.book_service.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.bibliotech.book_service.model.Book;
import com.bibliotech.book_service.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bs;

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bs.addBook(book);
    }

    @GetMapping("/show")
    public List<Book> getAllBooks() {
        return bs.getAllBooks();
    }

    @GetMapping("/showby/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bs.getBookById(id);
    }

    @PutMapping("/updateby/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @RequestBody Book book) {
        return bs.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bs.deleteBook(id);
        return "Book deleted successfully";
    }
}