package com.library.service;

import com.library.entity.Book;
import org.springframework.data.repository.Repository;

interface BookRepository extends Repository<Book, Integer> {
}
