package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.services.UserService;
import com.nth_ntq.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userService;

    /**
     * Tạo mới user (có thể k kèm avatar). consumes = multipart/form-data
     */
    @PostMapping(path = "/users",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String rawPassword,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestPart(name = "avatar", required = false) MultipartFile avatar
    ) {
        // Validate bắt buộc
        if (username.isBlank() || email.isBlank() || rawPassword.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Thiếu username, email hoặc password");
        }

        try {
            // Chuyển vào service
            Users created = userService.addUser(username, email, rawPassword, fullName, avatar);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(created);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo user: " + ex.getMessage());
        }

    }

    /**
     * API đăng nhập trả về JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users u) {
        boolean ok = userService.authenticate(u.getUsername(), u.getPassword());
        if (!ok) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Sai thông tin đăng nhập");
        }

        try {
            String token = JwtUtils.generateToken(u.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo JWT");
        }
    }

    /**
     * Lấy thông tin user đang đăng nhập
     */
    @GetMapping("/secure/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Chưa đăng nhập");
        }
        Users me = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(me);
    }
}
