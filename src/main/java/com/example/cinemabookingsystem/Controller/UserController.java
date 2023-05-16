package com.example.cinemabookingsystem.Controller;

import com.example.cinemabookingsystem.Model.DTO.UserDTO;
import com.example.cinemabookingsystem.Model.User;
import com.example.cinemabookingsystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try {
            final User user = userService.getUserById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserDTO user){
        String email = user.email();
        String password = user.password();
        String login = user.login();
        User userAdded = userService.register(new User(email, password, login));
        String userURI = String.format("/users/%d", userAdded.getId());

        return ResponseEntity.created(URI.create(userURI)).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Void> changeUser(@PathVariable Long id, @RequestBody UserDTO user){
        String email = user.email();
        String password = user.password();
        String login = user.login();

        try{
            userService.updateUser(id, new User(email, password, login));
            return ResponseEntity.noContent().build();
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<Void> raiseUser(@PathVariable Long id){
        try {
            userService.makeUserAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
