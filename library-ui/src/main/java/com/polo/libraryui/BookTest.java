package com.polo.libraryui;

public class BookTest {
    private String title;
    private String isbn;
    private String author;
    private String lendDate;
    private String coverImagePath; // Path to the book's cover image

    public BookTest(String title, String isbn, String author, String lendDate, String coverImagePath) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.lendDate = lendDate;
        this.coverImagePath = coverImagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getLendDate() {
        return lendDate;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

}
