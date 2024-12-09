package com.polo.librarybackend.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "`transaction`") // Add backticks to escape table name
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "date", nullable = false)
    private String date;

    public Transaction() {
    }

    public Transaction(Long transactionId, User user, Book book, String action, String date) {
        this.transactionId = transactionId;
        this.user = user;
        this.book = book;
        this.action = action;
        this.date = date;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(user, that.user) && Objects.equals(book, that.book) && Objects.equals(action, that.action) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, user, book, action, date);
    }
}
