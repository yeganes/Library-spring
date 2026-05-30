package com.library.service;


import com.library.model.Book;
import com.library.dao.BookDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BookService {
    BookDAO bookDAO = new BookDAO();

    public static ArrayList<Book> listBook = new ArrayList<>();


    public int getMaxId(){
        int max = 0 ;
        for (Book book : listBook){
            if (book.getId() > max){
                max = book.getId();
            }
        }
        return max;
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
        int id = getMaxId() + 1 ;
        Book book = new Book( inputTitle, inputAuthor, inputPage,  isAvailable , bookStock);
        listBook.add(book);
        bookDAO.save(book);
        return book;
    }
    public List<Book> findExactMatch (String title){
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        List<Book> allBooks = bookDAO.readByTitleContains(title);
        ArrayList<Book> result = new ArrayList<>();

        for (Book b : allBooks) {

            if (b.getTitle().equalsIgnoreCase(title)) {
                result.add(b);
            }
        }

        return result ;
    }

    public Book findById( int id){
        Book book = bookDAO.readById(id);
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }
        return book;
    }


    public List<Book> findPrefix(String preFix) {

        if (preFix == null || preFix.isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be empty");
        }
        List<Book> books =  bookDAO.readAllBooks();

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


        bookDAO.readByTitleContains(givenTitle);

        List<Book> b = null;

        b = findExactMatch(givenTitle);

        if (b.isEmpty()) {
            throw new NullPointerException("the book's name doesn't match");
            //left empty by purpose
        }
        return b;
    }

    public Book update(int id, boolean chosen) {

        List<Book> book;
        book = Collections.singletonList(findById(id));
        for(Book b : book){
            if (chosen) {
                if (b.isAvailable() == true) {
                    b.setAvailable(false);
                    bookDAO.updateStatus(id , b.isAvailable());
                    b.isAvailable();
                } else {
                    b.setAvailable(true);
                    bookDAO.updateStatus(id,  b.isAvailable());
                    b.isAvailable();
                }
            }
            return b;

        }
        return null;
    }

    public boolean delete(int id)  {

        List<Book> allBooks = bookDAO.readAllBooks();

        Book book = findById(id);

        listBook.remove(book);
        bookDAO.delete(book.getId());
        allBooks.remove(book);

        return true;
    }
}
