package com.polo.librarybackend.repository;

import com.polo.librarybackend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser_UserIdOrderByDateDesc(Long userId);

    @Query("SELECT t FROM Transaction t WHERE " +
            "t.user.userId = :userId AND " +
            "t.action = 'borrow' AND NOT EXISTS " +
            "(SELECT t2 FROM Transaction t2 WHERE " +
            "t2.user.userId = t.user.userId AND " +
            "t2.book.bookId = t.book.bookId AND " +
            "t2.action = 'return' AND " +
            "t2.date > t.date)")
    List<Transaction> findActiveBookBorrows(@Param("userId") Long userId);

    @Query("SELECT t FROM Transaction t WHERE " +
            "t.user.userId = :userId AND " +
            "t.book.bookId = :bookId AND " +
            "t.action = 'borrow' AND NOT EXISTS " +
            "(SELECT t2 FROM Transaction t2 WHERE " +
            "t2.user.userId = t.user.userId AND " +
            "t2.book.bookId = t.book.bookId AND " +
            "t2.action = 'return' AND " +
            "t2.date > t.date)")
    List<Transaction> findActiveBookBorrows(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query("SELECT t FROM Transaction t WHERE t.action = 'borrow' AND NOT EXISTS " +
            "(SELECT t2 FROM Transaction t2 WHERE t2.user.userId = t.user.userId " +
            "AND t2.book.bookId = t.book.bookId AND t2.action = 'return' AND t2.date > t.date)")
    List<Transaction> findAllActiveBorrows();
}