package com.polo.librarybackend.services;

import com.polo.librarybackend.entity.Book;
import com.polo.librarybackend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // CRUD operations
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    // Search operations
    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchByIsbn(String isbn) {
        return bookRepository.findByIsbnContaining(isbn);
    }

    // Availability filtering
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    // Inventory management
    public Book updateInventory(long bookId, long newCopies) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailableCopies(newCopies);
        return bookRepository.save(book);
    }
}