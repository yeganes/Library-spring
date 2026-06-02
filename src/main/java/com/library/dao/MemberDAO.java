package com.library.dao;



import com.library.entity.Member;
import com.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class MemberDAO {

    SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();


    public void save(Member member){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.persist(member);

        tx.commit();
    }


    public Member select(int id){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class, id);

        tx.commit();

        return member;
    }

    public List<Member> selectAllMembers(){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Member> bookQuery = session.createQuery("FROM Member ", Member.class);
        List<Member> members = bookQuery.list();

        tx.commit();

        return members;

    }

    public List<Member> selectByName(String name){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query<Member> query = session.createQuery("FROM Member WHERE name LIKE :name", Member.class);

        query.setParameter("name", "%" + name + "%");
        List<Member> members = query.list();

        tx.commit();
        return members;

    }

    public void updateName(int id , String name){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class , id);

        member.setName(name);

        tx.commit();


    }

    public void updatePhoneNumber(int id , String phoneNumber){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class , id);

        member.setPhoneNumber(phoneNumber);

        tx.commit();


    }

    public void updateAge(int id , int age){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class , id);

        member.setAge(age);



        tx.commit();


    }
    public void updateLimit(int id , int borrowLimit){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class , id);

        member.setBorrowLimit(borrowLimit);

        tx.commit();


    }
    public void updateBorrowedBooksNum(int id , int borrowedBooks){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class , id);

        member.setBorrowedBooksNum(borrowedBooks);

        tx.commit();


    }

    public void delete(int id){

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Member member = session.get(Member.class, id);
        member.setActive(false);
        member.setDeletedAt(LocalDateTime.now());

        tx.commit();

    }
}


