package com.library;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.uI.BookMenu;
import com.library.uI.MemberMenu;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication



public class Menu {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException, LimitBorrowedException, MemberNotFoundException {
        BookMenu bookMenu = new BookMenu();
        MemberMenu memberMenu = new MemberMenu();

        int clarification = 0;
        do {
            System.out.println("Hello please choose a number : \n 1.member \n 2.librarian\n 3.Exit");
            if (input.hasNextInt()){
                clarification = Integer.parseInt(input.nextLine());
            }
            else{
                System.out.println("invalid input ! please enter a number");
                input.nextLine();
                continue;
            }

            switch (clarification) {

                case 1:
                    memberMenu.ask();
                    break;

                case 2:
                    bookMenu.ask();
                    break;

                case 3 :
                    System.out.println("you selected number 3 , Bye");
                    break;

            }

        } while (clarification != 3);
    }
}

