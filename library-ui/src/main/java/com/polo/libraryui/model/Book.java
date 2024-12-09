package com.polo.libraryui.model;

public class Book {
    private long bookId;
    private String title;
    private String isbn;
    private String author;
    public int availableCopies;

    public Book(long bookId, String title, String isbn, String author, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public Book() {
    }

    public long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getAuthor() { return author; }
    public int getAvailableCopies() { return availableCopies; }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void incrementCopies() {
        availableCopies++;
    }

    public void decrementCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}