package org.example.bestelapp.RestController;

import org.example.bestelapp.Model.Role;
import org.example.bestelapp.Repository.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleDAO roleDAO;

    @GetMapping
    public List<Role> getRoles() {
        return roleDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable int id) {
        Optional<Role> role = roleDAO.findById(id);
        return role.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saved = roleDAO.save(role);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable int id) {
        if (roleDAO.existsById(id)) {
            roleDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
