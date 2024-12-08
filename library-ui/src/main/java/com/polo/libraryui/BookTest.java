package com.polo.libraryui;

public class BookTest {
    private String title;
    private String isbn;
    private String author;
    private String lendDate;
    private String coverImagePath; // Path to the book's cover image
    public int copies;

    public BookTest(String title, String isbn, String author, String lendDate, String coverImagePath, int copies) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.lendDate = lendDate;
        this.coverImagePath = coverImagePath;
        this.copies = copies;
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

    public int getCopies() {
        return copies;
    }

    public void incrementCopies() {
        copies++;
    }
}
