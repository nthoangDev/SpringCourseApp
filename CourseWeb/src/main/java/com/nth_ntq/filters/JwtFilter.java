/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.filters;

import com.nth_ntq.utils.JwtUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author pc
 */
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String context = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI().substring(context.length());

        // Áp dụng cho mọi /api/, trừ /api/login, /api/users, /api/courses
        // (phong cách DHT: kiểm tra startsWith, rồi loại trừ tương tự NTH_NTQ)
        if (uri.startsWith("/api/")
                && !uri.equals("/api/login")
                && !uri.equals("/api/users")
                && !uri.equals("/api/courses")
                && !uri.equals("/api/tags")) {

            String header = httpRequest.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                httpResponse.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Missing or invalid Authorization header."
                );
                return;
            }

            String token = header.substring(7);
            try {
                String username = JwtUtils.validateTokenAndGetUsername(token);
                if (username != null) {
                    // lưu username vào request, nếu cần
                    httpRequest.setAttribute("username", username);

                    // set Authentication cho Spring Security context
                    UsernamePasswordAuthenticationToken auth
                            = new UsernamePasswordAuthenticationToken(
                                    username, null, null
                            );
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(auth);

                    System.out.println(">> URI = " + uri);
                    System.out.println(">> Full RequestURI = " + httpRequest.getRequestURI());
                    System.out.println(">> Context = " + context);

                    // tiếp tục chain
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                httpResponse.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Token không hợp lệ hoặc hết hạn"
                );
                return;
            }
        }

        // Với tất cả các request khác, tiếp tục chain bình thường
        chain.doFilter(request, response);
    }
}
