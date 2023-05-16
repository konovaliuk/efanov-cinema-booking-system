package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.User;
import com.example.cinemabookingsystem.Repository.UserRepo;
import com.example.cinemabookingsystem.Repository.Util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepo userRepo;

    public User register(User user){
        user.setPassword(PasswordEncoder.encodePassword(user.getEmail(), user.getPassword()));
        return userRepo.save(user);
    }

    public void makeUserAdmin(Long id) throws IllegalArgumentException {
        Optional<User> maybeUser = userRepo.findById(id);
        if(maybeUser.isEmpty())
            throw new IllegalArgumentException("Invalid user id");
        userRepo.makeUserAdmin(id);
    }

    public User getUserByEmail(String email) throws IllegalArgumentException {
        Optional<User> maybeUser = userRepo.getUserByEmail(email);
        if(maybeUser.isEmpty())
            throw new IllegalArgumentException("Invalid user email");
        return maybeUser.get();
    }

    public User login(String email, String password) throws IllegalArgumentException {
        User userToLogin = getUserByEmail(email);
        if(PasswordEncoder.encodePassword(email, password).equals(userToLogin.getPassword()))
            return userToLogin;
        else
            throw new IllegalArgumentException("Invalid email or password");
    }

    public void updateUser(Long id, User user) throws IllegalArgumentException {
        Optional<User> maybeUserToUpdate = userRepo.findById(id);
        if(maybeUserToUpdate.isEmpty())
            throw new IllegalArgumentException("Invalid user");

        User userToUpdate = maybeUserToUpdate.get();
        if(user.getEmail() != null && !user.getEmail().isBlank())
            userToUpdate.setEmail(user.getEmail());
        if(user.getLogin() != null && !user.getLogin().isBlank())
            userToUpdate.setLogin(user.getLogin());
        if(user.getPassword() != null && !user.getPassword().isBlank())
            userToUpdate.setPassword(PasswordEncoder.encodePassword(user.getEmail(), user.getPassword()));

        userRepo.save(userToUpdate);
    }

    public User getUserById(Long id) throws IllegalArgumentException{
        Optional<User> mayberUser = userRepo.findById(id);
        if(mayberUser.isEmpty())
            throw new IllegalArgumentException("No such user");

        return mayberUser.get();
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }
}
