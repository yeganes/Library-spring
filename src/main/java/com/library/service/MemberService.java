package com.library.service;


import com.library.exceptions.MemberNotFoundException;
import com.library.entity.Member;

import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * سرویس کراد ممبر ها
 */

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    Member person = null;
    public static ArrayList<Member> listPerson = new ArrayList<>();
    public static Integer borrowLimit = 2;
    public static Integer borrowedBooks = 0;
    public static boolean isActive = true;

    public MemberService( MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(String inputName , int inputAge , String inputPhoneNumber , Member.Gender gender) throws IOException {

        //validations
        if (inputName == null || inputName.isEmpty() ){
            throw new IllegalArgumentException(" The name can't be empty");
        }
        if (inputAge <= 0) {
            throw new IllegalArgumentException(" Age must be positive");
        }

        person = new Member( inputName, inputAge, inputPhoneNumber, gender,borrowLimit , borrowedBooks , isActive);

        listPerson.add(person);

        memberRepository.save(person);

        return  person;
    }

    public  Member readByName(String member){
        if (member == null || member.isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be null or empty");
        }
        List<Member> a = memberRepository.findByNameContaining(member);
        Member result = null;
        for (Member m : a){
            if (member.equalsIgnoreCase(m.getName())){
                result = m;
                break ;
            }
        }
        return result;
    }

    public List<Member> readAllMembers(){
        return memberRepository.findAll();
    }

    public Member readMemberById(Integer id) throws  MemberNotFoundException {
        Member member = memberRepository.findMemberByMemberId(id);

        if (member == null) {
            throw new MemberNotFoundException("member not found");
        }

        return member;
    }

    public void updateName (Integer id , String name){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }
        Member member = memberRepository.findMemberByMemberId(id);

        if (name != null && !name.isEmpty()){
            member.setName(name);
            memberRepository.save(member);



        }
    }
    public void updateAge (Integer id , Integer age ){
        if (id == null || id <= 0) {
        throw new IllegalArgumentException("Invalid member ID");
    }
        Member member = memberRepository.findMemberByMemberId(id);

        if (age != null ){
            member.setAge(age);
            memberRepository.save(member);


        }

    }
    public void updatePhoneNumber (Integer id , String phoneNumber ){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }
        Member member = memberRepository.findMemberByMemberId(id);

        if (phoneNumber != null && !phoneNumber.isEmpty() ){
            member.setPhoneNumber(phoneNumber);

            memberRepository.save(member);

        }

    }

    public List<Member> findActiveMember(){
        return memberRepository.findByActiveTrue();
    }
    public List<Member> readDeletedMembers(){
        return memberRepository.findByActiveFalse();
    }



    public void delete(int enteredId , int number) throws MemberNotFoundException {
        if (enteredId <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        Member m = readMemberById(enteredId);
            if (number == 1 ){
                Member member = memberRepository.findMemberByMemberId(enteredId);
                member.setActive(false);
                member.setDeletedAt(LocalDateTime.now());
                memberRepository.save(member);

        }


    }


}