package com.polo.libraryui.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.libraryui.model.Book;
import com.polo.libraryui.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookService {
    private static final String API_BASE_URL = "http://localhost:8080/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BookService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<List<Book>> getAllBooks(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/books"))
                .header("Authorization", "Bearer " + user.getToken())
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(),
                                    new TypeReference<List<Book>>() {});
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse books", e);
                        }
                    }
                    throw new RuntimeException("Failed to fetch books: " + response.statusCode());
                });
    }
    public CompletableFuture<List<Book>> getBorrowedBooks(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/transactions/borrowed/" + user.getUserId()))
                .header("Authorization", "Bearer " + user.getToken())
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objectMapper.readValue(response.body(),
                                    new TypeReference<List<Book>>() {});
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse borrowed books", e);
                        }
                    }
                    throw new RuntimeException("Failed to fetch borrowed books: " + response.statusCode());
                });
    }
    public CompletableFuture<Void> registerBook(User user, Book book) {
        try {
            String requestBody = objectMapper.writeValueAsString(book);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/books"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + user.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() != 201) {
                            throw new RuntimeException("Failed to register book: " + response.body());
                        }
                    });

        } catch (Exception e) {
            CompletableFuture<Void> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }
    public CompletableFuture<Void> updateBook(User user, Book book) {
        try {
            String requestBody = objectMapper.writeValueAsString(book);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/books/" + book.getBookId()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + user.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() != 200) {
                            throw new RuntimeException("Failed to update book: " + response.body());
                        }
                    });

        } catch (Exception e) {
            CompletableFuture<Void> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }

    public CompletableFuture<Void> deleteBook(User user, Long bookId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/books/" + bookId))
                .header("Authorization", "Bearer " + user.getToken())
                .DELETE()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() != 204) {
                        String errorMessage = response.body();
                        throw new RuntimeException(errorMessage);
                    }
                });
    }
}