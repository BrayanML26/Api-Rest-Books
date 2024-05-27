package com.api.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.crud.models.UserModel;
import com.api.crud.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private IUserRepository userRepository;

    public ArrayList<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> getById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel request, Long id) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public Boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<UserModel> authenticateUser(String email, String password) {
        // Buscar un usuario por correo electrónico en la base de datos
        Optional<UserModel> userOptional = userRepository.findByEmail(email);

        // Verificar si se encontró un usuario y si la contraseña coincide
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return userOptional; // Usuario autenticado
            }
        }
        
        return Optional.empty(); // Usuario no encontrado o contraseña incorrecta
    }
}
