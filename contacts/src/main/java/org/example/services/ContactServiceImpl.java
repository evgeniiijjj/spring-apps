package org.example.services;

import org.example.entities.Contact;
import org.example.utils.Printer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Component
public class ContactServiceImpl implements ContactService {

    @Value("${app.contacts.delimiter}")
    private String contactDelimiter;
    @Value("${app.contacts.path}")
    private String contactsFilePath;
    @Value("${app.contacts.load-from-file}")
    private boolean loadContactsFromFile;
    private final Scanner scanner;
    private TreeSet<Contact> contacts;


    public ContactServiceImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void loadContacts() {
        try {
            this.contacts = Files
                    .readAllLines(Paths.get(contactsFilePath))
                    .stream()
                    .map(contact -> contact.split(contactDelimiter))
                    .map(Contact::new)
                    .collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveContacts(){
        List<String> contactList = contacts
                .stream()
                .map(this::mapContact)
                .collect(Collectors.toList());
        try {
            Files.write(Paths.get(contactsFilePath), contactList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String mapContact(Contact contact) {
        return contact.getFullName()
                .concat(contactDelimiter)
                .concat(contact.getPhoneNumber())
                .concat(contactDelimiter)
                .concat(contact.getEmail());
    }

    @Override
    public void addNewContact() {
        String[] data = new String[3];
        Printer[] stages = {Printer.NAME, Printer.PHONE, Printer.EMAIL};
        int iter = 0;
        while (iter < stages.length) {
            stages[iter].print();
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                return;
            }
            if (input.matches(stages[iter].getRedex())) {
                data[iter] = input;
                iter++;
            } else {
                Printer.WRONG_DATA_INPUT.print();
            }
        }
        contacts.add(
                new Contact(data[0], "+7" + data[1], data[2])
        );
        Printer.SHOW.print(contacts);
    }

    @Override
    public void pickContact() {
        Printer.INPUT_CONTACT_ID.print();
    }

    @Override
    public void deleteContact() {
        Printer.CONTACT_CHOOSING.print();
        while (true) {
            Printer.SHOW.print(contacts);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")){
                break;
            }
            Contact pickedContact = null;
            if (input.matches(Printer.NUMBER.getRedex())) {
                int numInOrder = Integer.parseInt(input);
                pickedContact = contacts.stream()
                        .filter(cont -> cont.getNumInOrder().equals(numInOrder))
                        .findFirst().orElse(null);
            }
            if (input.matches(Printer.EMAIL.getRedex())) {
                pickedContact = contacts.stream()
                        .filter(cont -> cont.getEmail().equals(input))
                        .findFirst().orElse(null);
            }
            if (pickedContact != null) {
                contacts.remove(pickedContact);
                Printer.SHOW.print(contacts);
                Printer.DELETE_SUCCESS.print();
                return;
            }
            Printer.WRONG_DATA_INPUT.print();
        }
    }

    @Override
    public void searchContact() {
        Printer.SEARCH_INPUT.print();
        String input = scanner.nextLine().toLowerCase();
        Printer.SHOW_SEARCH_RESULT.print(
                contacts
                        .stream()
                        .filter(contact -> contactFieldsMatcher(contact, input))
                        .collect(Collectors.toSet())
        );
    }

    private boolean contactFieldsMatcher(Contact contact, String input) {
        return contact.getFullName().toLowerCase().contains(input) ||
                contact.getPhoneNumber().toLowerCase().contains(input) ||
                contact.getEmail().toLowerCase().contains(input);
    }

    @Override
    public boolean isLoadContactsFromFile() {
        return loadContactsFromFile;
    }

    @Override
    public Set<Contact> getContacts() {
        return contacts;
    }
}
