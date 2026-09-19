package com.bibliotech.book_service.model;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String title;
    private String author;
    private int availableCopies;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + bookId
                + ", title=" + title
                + ", author=" + author
                + ", availableCopies=" + availableCopies + "]";
    }

    public Book(String title, String author, int availableCopies) {
        super();
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }
	
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}