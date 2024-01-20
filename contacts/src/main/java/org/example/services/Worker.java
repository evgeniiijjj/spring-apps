package org.example.services;

import org.example.utils.Printer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
@AllArgsConstructor
public class Worker {

    private final ContactService service;
    private final Scanner scanner = new Scanner(System.in);


    public void run() {
        if (service.isLoadContactsFromFile()) {
            service.loadContacts();
        }
        Printer.WELCOME.print();
        boolean run = true;
        while (run) {
            Printer.COMMANDS.print();
            String command = scanner
                    .nextLine()
                    .toUpperCase();
            try {
                switch (command) {
                    case "ADD" -> service.addNewContact();
                    case "DELETE" -> service.deleteContact();
                    case "EXIT" -> run = false;
                    case "PICK" -> service.pickContact();
                    case "SAVE" -> service.saveContacts();
                    case "SEARCH" -> service.searchContact();
                    default -> Printer.valueOf(command).print(service.getContacts());
                }
            } catch (Exception e) {
                Printer.WRONG_INPUT.print();
            }
        }
    }
}
