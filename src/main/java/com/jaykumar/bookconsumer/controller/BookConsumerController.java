package com.jaykumar.bookconsumer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jaykumar.bookconsumer.exception.BookNotFoundException;
import com.jaykumar.bookconsumer.model.Book;

@RestController
@RequestMapping("/books")
public class BookConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book[] books = restTemplate.getForObject("http://localhost:8080/books", Book[].class);
        List<Book> bookList = Arrays.asList(books);
        
        Optional<Book> book = bookList.stream()
                                      .filter(b -> b.getId().equals(id))
                                      .findFirst();

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
        	 throw new BookNotFoundException(id);
        }
    }
}