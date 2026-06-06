package com.library.uI;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.uI.BookMenu;
import com.library.uI.MemberMenu;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
@Component
public class Menu {

    private final BookMenu bookMenu;
    private final MemberMenu memberMenu;

    public Menu(MemberMenu memberMenu, BookMenu bookMenu) {
        this.memberMenu = memberMenu;
        this.bookMenu = bookMenu;
    }

    private static final Scanner input = new Scanner(System.in);

    public void showMenu() throws MemberNotFoundException {

        int clarification = 0;

        do {
            System.out.println("""
                    Hello please choose a number :
                    1. member
                    2. librarian
                    3. Exit
                    """);

            if (input.hasNextInt()) {
                clarification = Integer.parseInt(input.nextLine());
            } else {
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

                case 3:
                    System.out.println("Bye");
                    break;

                default:
                    System.out.println("Invalid option");
            }

        } while (clarification != 3);
    }
}