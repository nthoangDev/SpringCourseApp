/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc
 */
@RestController
public class PasswordTestController {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordTestController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/test-password")
    public String testPassword(
            @RequestParam String raw,
            @RequestParam String encoded
    ) {
        boolean matched = passwordEncoder.matches(raw, encoded);
        return matched ? "✅ KHỚP MẬT KHẨU!" : "❌ KHÔNG KHỚP!";
    }
}
