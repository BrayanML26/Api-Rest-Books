package com.api.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.crud.models.BookModel;
import com.api.crud.services.BookServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookServices bookService;

    @GetMapping()
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        Optional<BookModel> book = bookService.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<BookModel> saveBook(@RequestBody BookModel book) {
        BookModel savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody BookModel book) {
        BookModel updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        String message = bookService.deleteBook(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
