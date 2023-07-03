package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String userName);
    public void changePassword(String password, User user);
    public int create(UserDTO userDTO);

    public User findUserByEmail(String email);

    public User findUserByName(String name);
    public Optional<User> findById(int id);
    public int update(UserDTO userDTO);
}
