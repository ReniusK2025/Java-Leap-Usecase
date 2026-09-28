package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Model.Contact;
import com.example.SmartGreenhouseEnergy.Repository.ContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository repository;
    public ContactService(ContactRepository repository) { this.repository = repository; }
    public Contact save(Contact value) { return repository.save(value); }
    public List<Contact> findAll() { return repository.findAll(); }
    public Contact findById(Long id) { return repository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found: " + id)); }
    public Contact update(Long id, Contact v) { Contact x=findById(id); x.setName(v.getName()); x.setType(v.getType()); x.setEmail(v.getEmail()); x.setPhone(v.getPhone()); x.setAddress(v.getAddress()); return repository.save(x); }
    public void delete(Long id) { repository.delete(findById(id)); }
}
