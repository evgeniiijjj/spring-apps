package com.example.contactlist.services;

import com.example.contactlist.entities.Contact;
import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    void save(Contact contact);

    void deleteById(int id);

    List<Contact> getContacts();

    Optional<Contact> findContactById(int id);
}
