package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
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
        var contactOld = contactRepo.findById(contact.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setDescription(contact.getDescription());
        contactOld.setAddress(contact.getAddress());
        contactOld.setWebSiteLink(contact.getWebSiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld); 
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
    public List<Contact> getByUserId(String userId) {
        
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String sortDireection) {
        
        Sort sort = sortDireection.equals("desc") ? Sort.by(sortBy).descending() :  Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByName(String name, int page, int size, String sortBy, String sortDireection, User user) {
        
        Sort sort = sortDireection.equals("desc") ? Sort.by(sortBy).descending() :  Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email,int page, int size, String sortBy, String sortDireection, User user) {
        Sort sort = sortDireection.equals("desc") ? Sort.by(sortBy).descending() :  Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String sortDireection,User user) {
        Sort sort = sortDireection.equals("desc") ? Sort.by(sortBy).descending() :  Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }

    @Override
    public Page<Contact> searchByFavourite(int page, int size, String sortBy, String sortDirection, User user) {

    Sort sort = sortDirection.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return contactRepo.findByUserAndFavoriteTrue(user, pageable);
}

}
