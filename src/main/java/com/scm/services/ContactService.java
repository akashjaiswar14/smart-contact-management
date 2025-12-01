package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;

public interface ContactService {

    Contact saveContact(Contact contact);

    Contact updateContact(Contact contact);

    List<Contact> getAllContacts();

    Contact getContactById(String id);

    void deleteContact(String id);

    List<Contact> searchContact(String name, String email, String phoneNumber);

    List<Contact> getByUserId(String userId);
}
