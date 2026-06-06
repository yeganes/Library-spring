package com.library;

import com.library.exceptions.LimitBorrowedException;
import com.library.exceptions.MemberNotFoundException;
import com.library.uI.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class SpringAppLibrary implements CommandLineRunner {
    private final Menu menu;
    public SpringAppLibrary(Menu menu){
        this.menu = menu;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAppLibrary.class, args);
    }
    public void run(String... args) throws MemberNotFoundException {
        menu.showMenu();
    }
}