package com.random.security.user;

import com.random.security.exceptions.ObjectNotValidException;
import com.random.security.role.Role;
import com.random.security.role.RoleRepository;
import com.random.security.user.request.ChangeRoleRequest;
import com.random.security.user.request.RoleRequest;
import com.random.security.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User findByUsername(String username){

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Hibás felhasználói adatok"));
    }
    public User findByUsernameAccount(String username){

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nincs ilyen felhasználó"));
    }
    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public List<UserResponse> getAllUser(){
        List<User> users =  userRepository.findAll();
        return users.stream()
                .map(user ->
                     UserResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .loginAt(user.getLoginAt())
                            .roles(user.getRoles())
                            .username(user.getUsername())
                            .build()
                ).collect(Collectors.toList());
    }

    public void deleteUserById(int userId) {
        User user = userRepository.findAllById(userId);
        userRepository.delete(user);
    }


    public void changeRole(ChangeRoleRequest request) {
        List<Role> tempRole = new java.util.ArrayList<>(Collections.emptyList());
        for(String roleName : request.getRoles()){
            tempRole.add(roleRepository.findByName(roleName).orElseThrow());
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        List<Role> roles = user.getRoles();
        roles.clear();
        roles.addAll(tempRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public UserResponse getUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return UserResponse.builder()
                .id(user.getId())
                .loginAt(user.getLoginAt())
                .name(user.getName())
                .roles(user.getRoles())
                .username(user.getUsername())
                .build();
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return UserResponse.builder()
                .id(user.getId())
                .loginAt(user.getLoginAt())
                .name(user.getName())
                .roles(user.getRoles())
                .username(user.getUsername())
                .build();
    }

    public User findUserById(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public void addRole(RoleRequest request) {
        List<Role> tempRole = new java.util.ArrayList<>(Collections.emptyList());
        Role role = roleRepository.findByName(request.getRole()).orElseThrow();
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        List<Role> roles = user.getRoles();
        tempRole.addAll(roles);
        if(!tempRole.contains(role)){
           tempRole.add(role);
        }
        roles.clear();
        roles.addAll(tempRole);
        user.setRoles(roles);
        userRepository.save(user);

    }

    public void removeRole(RoleRequest request) {
        Role role = roleRepository.findByName(request.getRole()).orElseThrow();
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        List<Role> roles = user.getRoles();
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
