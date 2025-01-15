package com.example.volunteer_platform.user_interface;

import com.example.volunteer_platform.controller.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CustomerMenuUI {

    private final CustomerController customerController;
    private final Scanner scanner;

    @Autowired
    public CustomerMenuUI(CustomerController customerController) {
        this.customerController = customerController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Customer Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createCustomerAccount() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        ResponseEntity<String> response = customerController.createCustomer(email, password, username);

        System.out.println(response.getBody());
    }

}
