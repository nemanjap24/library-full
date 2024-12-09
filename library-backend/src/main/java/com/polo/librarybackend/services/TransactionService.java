package com.polo.librarybackend.services;

import com.polo.librarybackend.dto.TransactionDTO;
import com.polo.librarybackend.entity.Book;
import com.polo.librarybackend.entity.Transaction;
import com.polo.librarybackend.entity.User;
import com.polo.librarybackend.repository.BookRepository;
import com.polo.librarybackend.repository.TransactionRepository;
import com.polo.librarybackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private static final int BORROW_PERIOD_DAYS = 14;

    public TransactionService(TransactionRepository transactionRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Transaction borrowBook(TransactionDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        if (!transactionRepository.findActiveBookBorrows(user.getUserId(), book.getBookId()).isEmpty()) {
            throw new RuntimeException("User already has this book borrowed");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setAction("borrow");
        transaction.setDate(LocalDateTime.now().toString());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction returnBook(TransactionDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Transaction> activeBorrows = transactionRepository.findActiveBookBorrows(user.getUserId(), book.getBookId());
        if (activeBorrows.isEmpty()) {
            throw new RuntimeException("You have not borrowed this book");
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setAction("return");
        transaction.setDate(LocalDateTime.now().toString());

        return transactionRepository.save(transaction);
    }
    public List<Book> getUserBorrowedBooks(Long userId) {
        return transactionRepository.findAllActiveBorrows().stream()
                .filter(transaction -> transaction.getUser().getUserId().equals(userId))
                .map(Transaction::getBook)
                .toList();
    }

    public List<Transaction> getUserTransactionHistory(Long userId) {
        return transactionRepository.findByUser_UserIdOrderByDateDesc(userId);
    }
}