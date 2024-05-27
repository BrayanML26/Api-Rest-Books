package com.api.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.crud.models.BookModel;
import com.api.crud.repositories.BookRepository;

@Service
public class BookServices {
    @Autowired
    private BookRepository bookRepository;

    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookModel> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public BookModel saveBook(BookModel book) {
        return bookRepository.save(book);
    }

    public BookModel updateBook(Long id, BookModel book) {
        BookModel existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setDescription(book.getDescription());
            existingBook.setCover(book.getCover());
            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }

    public String deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return "Book with ID " + id + " deleted successfully.";
        } else {
            return "Book with ID " + id + " not found.";
        }
    }
}
