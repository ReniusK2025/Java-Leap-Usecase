package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.Contact;
import com.example.SmartGreenhouseEnergy.Service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService service;
    public ContactController(ContactService service) { this.service=service; }
    @PostMapping public Contact create(@RequestBody Contact x) { return service.save(x); }
    @GetMapping public List<Contact> all() { return service.findAll(); }
    @GetMapping("/{id}") public Contact one(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Contact update(@PathVariable Long id, @RequestBody Contact x) { return service.update(id,x); }
    @DeleteMapping("/{id}") public ResponseEntity<String> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok("Contact deleted"); }
}
