package org.example.services;


import org.example.entities.Contact;

import java.util.Set;

public interface ContactService {

    void loadContacts();
    void saveContacts();
    void addNewContact();
    void pickContact();
    void deleteContact();
    void searchContact();
    boolean isLoadContactsFromFile();
    Set<Contact> getContacts();
}
