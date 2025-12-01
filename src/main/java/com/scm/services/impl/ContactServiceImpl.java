package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact saveContact(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateContact'");
    }

    @Override
    public List<Contact> getAllContacts() {
        
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactById(String id) {
        
        return contactRepo.findById(id)
                            .orElseThrow(()-> new ResourceNotFoundException("Contact not found with id "+id));
    }

    @Override
    public void deleteContact(String id) {
        var contact = contactRepo.findById(id)
                            .orElseThrow(()-> new ResourceNotFoundException("Contact not found with id "+id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> searchContact(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchContact'");
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        
        return contactRepo.findByUserId(userId);
    }

}
