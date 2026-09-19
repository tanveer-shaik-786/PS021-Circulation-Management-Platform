package com.bibliotech.book_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.bibliotech.book_service.model.Book;
import com.bibliotech.book_service.repository.BookRepository;

@Service
public class BookService {
	@Autowired
    BookRepository br;

    public Book addBook(Book book) {
        return br.save(book);
    }

    public List<Book> getAllBooks() {
        return br.findAll();
    }

    public Book getBookById(Long id) {
        return br.findById(id).orElse(null);
    }

    public Book updateBook(Long id, Book book) {

        Book existingBook = br.findById(id).orElse(null);

        if (existingBook == null) {
            return null;
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setAvailableCopies(book.getAvailableCopies());

        return br.save(existingBook);
    }

    public void deleteBook(Long id) {
        br.deleteById(id);
    }
}