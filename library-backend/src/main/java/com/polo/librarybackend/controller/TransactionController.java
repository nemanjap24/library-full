package com.polo.librarybackend.controller;

import com.polo.librarybackend.dto.TransactionDTO;
import com.polo.librarybackend.entity.Book;
import com.polo.librarybackend.entity.Transaction;
import com.polo.librarybackend.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.borrowBook(transactionDTO);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.returnBook(transactionDTO);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getUserTransactionHistory(userId));
    }
    @GetMapping("/borrowed/{userId}")
    public ResponseEntity<List<Book>> getUserBorrowedBooks(@PathVariable Long userId) {
        try {
            List<Book> borrowedBooks = transactionService.getUserBorrowedBooks(userId);
            return ResponseEntity.ok(borrowedBooks);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}