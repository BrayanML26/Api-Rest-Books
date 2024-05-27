package com.api.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserServices;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userServices.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserModel> saveUser(@RequestBody UserModel user) {
        UserModel savedUser = userServices.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        Optional<UserModel> user = userServices.getById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUserById(@PathVariable Long id, @RequestBody UserModel request) {
        UserModel updatedUser = userServices.updateById(request, id);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        Boolean deleted = userServices.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>("User with ID " + id + " deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> loginUser(@RequestBody UserModel request) {
        // Obtener el usuario por correo electrónico desde la base de datos
        Optional<UserModel> user = userServices.authenticateUser(request.getEmail(), request.getPassword());
        
        // Verificar si el usuario existe y si la contraseña coincide
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK); // Usuario autenticado
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Credenciales incorrectas
        }
    }

}
