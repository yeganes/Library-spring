package com.library.service;


import com.library.dao.BorrowDAO;
import com.library.exceptions.MemberNotFoundException;
import com.library.entity.Member;
import com.library.dao.MemberDAO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * سرویس کراد ممبر ها
 */

@Service
public class MemberService {
    private final MemberDAO memberDAO;

    Member person = null;
    public static ArrayList<Member> listPerson = new ArrayList<>();
    public static Integer borrowLimit = 2;
    public static Integer borrowedBooks = 0;
    public static boolean isActive = true;

    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public Member create(String inputName , int inputAge , String inputPhoneNumber , Member.Gender gender) throws IOException {

        //validations
        if (inputName == null || inputName.isEmpty() ){
            throw new IllegalArgumentException(" The name can't be empty");
        }
        if (inputAge <= 0) {
            throw new IllegalArgumentException(" Age must be positive");
        }
        int id = 0 ;
        id ++;

        person = new Member( inputName, inputAge, inputPhoneNumber, gender,borrowLimit , borrowedBooks , isActive);

        listPerson.add(person);

        memberDAO.save(person);

        return  person;
    }

    public  Member readByName(String member){
        if (member == null || member.isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be null or empty");
        }
        List<Member> a = memberDAO.selectByName(member);
        Member result = null;
        for (Member m : a){
            if (member.equalsIgnoreCase(m.getName())){
                result = m;
                break ;
            }
        }
        return result;
    }
    public Member readMemberById(Integer id) throws RuntimeException , MemberNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }
        return memberDAO.select(id);

    }


    public Member update(Integer id , String name , String phoneNumber , Integer age ) throws MemberNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        Member m = readMemberById(id);

        if (m==null){
            return null;
        }

        if (name != null && !name.isEmpty()){
            m.setName(name);
            memberDAO.updateName(id , name);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()){
            m.setPhoneNumber(phoneNumber);
            memberDAO.updatePhoneNumber(id , phoneNumber);
        }

        if (age != null  ){

            m.setAge(age);
            memberDAO.updateAge(id , age);
        }
        return m;
    }
    public Member delete(int enteredId , int number) throws MemberNotFoundException {
        if (enteredId <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        Member m = readMemberById(enteredId);
        if (m == null){
            return null;
        }
        else {
            if (number == 1 ){
                memberDAO.delete(enteredId);

            } else if (number == 2 ) {
                return m;
            }
        }
        return person;
    }


}