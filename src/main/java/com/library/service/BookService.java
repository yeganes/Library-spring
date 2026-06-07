package com.library.service;


import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }



    public Book add(String inputTitle, String inputAuthor, Integer inputPage , int bookStock) {


        boolean isAvailable = true;

        if (inputTitle==null || inputTitle.isEmpty()){
            throw new IllegalArgumentException("the title shouldn't be empty");

        }
        if (inputAuthor ==null || inputAuthor.isEmpty()){
            throw new IllegalArgumentException("the author shouldn't be empty");

        }
        if (inputPage==null || inputPage <= 0){
            throw new IllegalArgumentException("the page shouldn't be empty or smaller than zero");

        }
        Book book = new Book( inputTitle, inputAuthor, inputPage,  isAvailable , bookStock);
        bookRepository.save(book);
        return book;
    }
    public List<Book> findExactMatch (String title){
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        List<Book> allBooks = bookRepository.findBookByTitleContaining(title);
        ArrayList<Book> result = new ArrayList<>();

        for (Book b : allBooks) {

            if (b.getTitle().equalsIgnoreCase(title)) {
                result.add(b);
            }
        }

        return result ;
    }

    public Book findById(int id){

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        return bookRepository.findBookById(id);
    }

    public List<Book> readAllBooks(){
       return  bookRepository.findAll();
    }

    public List<Book> findPrefix(String preFix) {

        if (preFix == null || preFix.isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be empty");
        }
        List<Book> books =  readAllBooks();

        List<Book> result = new ArrayList<>(); // Separate list for results

        for (Book b : books) {
            String[] words = b.getTitle().split(" ");
            for (String w : words) {
                if (w.toLowerCase().startsWith(preFix.toLowerCase())) {
                    result.add(b);
                    break;
                }
            }
        }
        return result;
    }

    public List<Book> search(String givenTitle) {
        if (givenTitle == null || givenTitle.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }



        List<Book> b = null;

        b =  bookRepository.findBookByTitleContaining(givenTitle);

        if (b.isEmpty()) {
            throw new IllegalArgumentException("Book not found");            //left empty by purpose
        }
        return b;
    }

    public Book updateStatus(int id, boolean status) {

        Book book = findById(id);
            if (status) {
                if (book.isAvailable() == true) {
                    book.setAvailable(false);
                } else {
                    book.setAvailable(true);

                }
            }
            return book;
    }

    @Transactional
    public void delete(int id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }

        bookRepository.deleteById(id);
    }
}
