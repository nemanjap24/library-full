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
}