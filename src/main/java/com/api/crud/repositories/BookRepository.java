package com.api.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.models.BookModel;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
}
