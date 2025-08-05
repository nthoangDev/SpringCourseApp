/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author trung
 */
@Service              // bỏ ("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    public UserServiceImpl(UserRepository userRepo /*, ... */) {
        this.userRepo = userRepo;
        // ...
    }

    @Override
    public List<Users> getUsersByKeyword(String kw) {
        return userRepo.getUsersByKeyword(kw);
    }

    @Override
    public Users getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        System.out.println("Tìm thấy user: " + u.getUsername());
        System.out.println("Password (from DB): " + u.getPassword());
        System.out.println(passwordEncoder.encode("123"));
        System.out.println("Role: " + u.getRole());
        System.out.println("Status: " + u.getStatus());
        System.out.println("User authenticated: " + u.getUsername());
        System.out.println("Matched password? " + passwordEncoder.matches("123", u.getPassword()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public Users addUser(Map<String, String> params, MultipartFile avatar) {
        Users u = new Users();
        u.setUsername(params.get("username"));
        u.setEmail(params.get("email"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setFullName(params.get("fullname"));
        u.setRole("USER");
        u.setStatus("ACTIVE");
        u.setCreatedAt(new Date());

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map<?, ?> res = cloudinary.uploader()
                        .upload(avatar.getBytes(),
                                ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return userRepo.addUser(u);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    @Transactional(readOnly = true)
    public Users getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> getAllTeachers() {
        // Giả sử role của giảng viên là "TEACHER"
        return userRepo.findByRole("TEACHER");
    }

    @Override
    public List<Users> getStudentsByCourseId(Long courseId) {
        return userRepo.getStudentsByCourseId(courseId);
    }
}
