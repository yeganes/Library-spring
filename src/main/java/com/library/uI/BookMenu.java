package com.library.uI;




import com.library.entity.Book;
import com.library.dao.BookDAO;
import com.library.service.BookService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class BookMenu {
    BookDAO bookDAO = new BookDAO();
    BookService bookService = new BookService();
    static Scanner inputInfo = new Scanner(System.in);


    public void ask() {

        int chosenNumber = 0;

        do {

            System.out.println("please choose a number: \n 1 : Add book \n 2 : Search \n 3 : Update the status\n 4 : Delete \n 5 : EXIT");

            if (inputInfo.hasNextInt()){
                //the output of hasnextint is always a boolean
                chosenNumber = Integer.parseInt(inputInfo.nextLine());
            }

            else{
                System.out.println("Invalid input!");
                inputInfo.nextLine();
                continue;
            }

            //input.next == clears the buffer
            switch (chosenNumber) {

                case 1:
                    System.out.println("you selected number 1, let's add the book");

                    System.out.println("How many books are you going to add? ");
                    int j;

                    while (true) {
                        try {
                            j = Integer.parseInt(inputInfo.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("invalid input, please add a number");
                        }
                    }

                    for (int i = 0; i < j; i++) {

                        try {

                            System.out.println("Enter the book's title: ");
                            String inputTitle = inputInfo.nextLine();

                            System.out.println("Enter the book's author: ");
                            String inputAuthor = inputInfo.nextLine();

                            System.out.println("How many pages does it have? ");
                            Integer inputPage = Integer.valueOf(inputInfo.nextLine());

                            System.out.println("How many edition have you got of this book? ");
                            int bookStock = Integer.parseInt(inputInfo.nextLine());

                            bookService.add(inputTitle, inputAuthor, inputPage , bookStock );

                            System.out.println("Book added successfully ✔");

                        } catch (IllegalArgumentException e) {
                            System.out.println("error: " + e.getMessage());
                            i--; // دوباره همین کتاب رو بگیر
                        } catch (Exception e) {
                            System.out.println("unexpected error: " + e.getMessage());
                        }
                    }
                    break;


                case 2:
                    System.out.println("you selected number 2 , let's Search through the books");
                    System.out.println(" 1.exact match \n 2.know the beginning \n 3.Exit");
                    int number = Integer.parseInt(inputInfo.nextLine());
                    switch (number){
                        case 1:
                            while(true){
                                try{
                                    System.out.println("enter the book you are searching for : ");
                                    List<Book> books = bookDAO.readAllBooks();
                                    for (Book b : books) {
                                        System.out.println(b);
                                    }
                                    String givenTitle = inputInfo.nextLine();
                                    List<Book> bookList = bookService.search(givenTitle);
                                    for ( Book book : bookList){
                                        String msg = MessageFormat.format("the {0} book is written by {1} and it has got {2} pages the availability status is {3}",
                                                book.getTitle(),
                                                book.getAuthor(),
                                                book.getTotalPages(),
                                                book.isAvailable());
                                        System.out.println(msg);
                                    }
                                    break;
                                }catch(NullPointerException e ){
                                    System.out.println("error!" + e.getMessage());
                                }
                            }
                            break;
                        case 2:
                            List<Book> allBooks1 = bookDAO.readAllBooks();
                            while(true){
                                try{
                                    System.out.println("search here:");
                                    for (Book b : allBooks1){
                                        System.out.println(b);
                                    }
                                    String prefix = inputInfo.nextLine();
                                    List<Book> book = bookService.findPrefix(prefix);
                                    for (Book b : book){
                                        System.out.println(b.getTitle() + " " + b.getAuthor());
                                    }

                                    break;
                                }catch(NullPointerException e){
                                    System.out.println("error!" + e.getMessage());
                                }
                            }
                        case 3 :
                            break;
                    }
                    break;


                case 3:
                    System.out.println("you selected number 3 , let's update the book's status");
                    while (true) {
                        List<Book> books = bookDAO.readAllBooks();
                        for (Book b : books){
                            System.out.println(b);
                        }

                        try {
                            System.out.println("enter the book's ID you're going to update : ");
                            int id = Integer.parseInt(inputInfo.nextLine());
                            Book book = bookDAO.readById(id);

                            String msg = MessageFormat.format("the {0} book availability is {1} if you  wanna change it press enter",
                                    book.getTitle(),
                                    book.isAvailable());
                            System.out.println(msg);
                            boolean chosen = inputInfo.nextLine().isEmpty();
                            Book b = bookService.update(id, chosen);
                            System.out.println("the book availability is  " + b.isAvailable());

                            break;
                        } catch (NullPointerException e) {
                            System.out.println("error!" + e.getMessage());
                        }
                    }


                    break;
                case 4:
                    System.out.println("you selected number 4, deleting a book!");
                    List<Book> books = bookDAO.readAllBooks();
                    for (Book b : books){
                        System.out.println(b);
                    }
                    System.out.println("enter the book's id:");
                    int givenID = Integer.parseInt(inputInfo.nextLine());
                    System.out.println(bookDAO.readById(givenID));
                    System.out.println("Do you want to delete this book? 1.yes 2.no");
                    try {
                        int choice = Integer.parseInt(inputInfo.nextLine());
                        if (choice == 1) {
                            boolean deleted = bookService.delete(givenID);
                            if (deleted) {
                                System.out.println("Book successfully deleted.");
                            } else {
                                System.out.println("Book not found.");
                            }
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input, please enter a valid number.");
                    }
                    break;


                case 5:
                    System.out.println("you selected number 5 , Bye");
                    break;
            }





        } while (chosenNumber != 5);
    }
}

