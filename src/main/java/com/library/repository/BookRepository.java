package com.library.repository;

import com.library.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , Integer> {

    Book findBookById(Integer id);


     List<Book> findBookByTitleContaining(String title) ;


}