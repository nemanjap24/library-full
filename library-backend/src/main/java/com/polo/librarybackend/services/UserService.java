package com.polo.librarybackend.services;

import com.polo.librarybackend.entity.User;
import com.polo.librarybackend.exception.UserNotFoundException;
import com.polo.librarybackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(long id){
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public Optional<User> validateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
