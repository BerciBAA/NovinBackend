package com.random.security.user;

import com.random.security.user.request.ChangeRoleRequest;
import com.random.security.user.request.RoleRequest;
import com.random.security.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable(value="userId") int userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('USER')" + " || hasRole('ACCOUNTANT')" + " || hasRole('ADMIN')")
    @GetMapping ("/get-user/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value="userId") int userId){
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('ACCOUNTANT')" + " || hasRole('USER')")
    @PostMapping ("/get-user-by-name/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable(value="username") String username){
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/add-role")
    public ResponseEntity<List<User>> addRole(@RequestBody RoleRequest request){
        userService.addRole(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/remove-role")
    public ResponseEntity<List<User>> removeRole(@RequestBody RoleRequest request){
        userService.removeRole(request);
        return ResponseEntity.ok().build();
    }


}
