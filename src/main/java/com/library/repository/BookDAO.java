package com.library.repository;

import com.library.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO {
    public void print() {
        System.out.println("Repository called");
    }





    public void save(Book book){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.persist(book);

        tx.commit();
    }


    public Book readById(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        Book book = session.get(Book.class , id);

        tx.commit();
        return book;
    }

    public List<Book> readAllBooks(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        Query<Book> bookQuery = session.createQuery("FROM Book ", Book.class);
        List<Book> books = bookQuery.list();

        tx.commit();
        return books;

    }

    public List<Book> readByTitleContains(String text) {

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be empty");
        }

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();



        Query<Book> query = session.createQuery("FROM Book WHERE lower(title) LIKE :text", Book.class);

        query.setParameter("text", "%" + text.toLowerCase() + "%");
        List<Book> books = query.list();

        tx.commit();

        return books;


    }


    public void updateStatus(int id , boolean status){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Book book = session.get(Book.class , id);

        book.setAvailable(status);

        tx.commit();


    }

    public void updateStock(int id , int stock){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Book book = session.get(Book.class , id);

        book.setBookStock(stock);



        tx.commit();


    }

    public void delete(int id){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Book book = session.get(Book.class, id);
        session.remove(book);

        tx.commit();

    }
}