package com.nth_ntq.controllers;

import com.nth_ntq.configs.VNPayConfig;
import com.nth_ntq.pojo.CartItems;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.pojo.Carts;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.pojo.Payments;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.CartItemService;
import com.nth_ntq.services.CartService;
import com.nth_ntq.services.EnrollmentService;
import com.nth_ntq.services.PaymentService;
import com.nth_ntq.services.UserService;
import com.nth_ntq.utils.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiPaymentController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VNPayUtils vnPayUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/secure/cart/checkout")
    public ResponseEntity<?> checkoutVNPay(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(Map.of("error", "Chưa đăng nhập"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(Map.of("error", "Người dùng không tồn tại"));
            }

            BigDecimal amount = cartService.getCartTotal(user.getUserId());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Giỏ hàng trống"));
            }

            String orderInfo = "Thanh toan don hang: " + user.getUserId();
            String clientIp = getClientIpAddress(request);
            String bankCode = request.getParameter("bankCode");
            String paymentUrl = vnPayUtils.createOrder(amount.intValue(), orderInfo, clientIp, bankCode, username);

            return ResponseEntity.ok(Map.of("vnpUrl", paymentUrl));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/payment/vnpay_return")
    public ResponseEntity<?> handleVNPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int result = vnPayUtils.handleReturn(request);
//        String username = (String) request.getAttribute("username");

        if (result == 1) {
            String encoded = request.getParameter("vnp_TxnRef");
            String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
            String username = decoded.split(":")[0];
            String transactionId = request.getParameter("vnp_TransactionNo");

            // ✅ 1. Lấy thông tin người dùng
            Users user = userService.getUserByUsername(username);
            Carts cart = cartService.getCartByUser(user.getUserId());

            if (cart != null && cart.getCartItemsSet() != null && !cart.getCartItemsSet().isEmpty()) {
                // ✅ 2. Tạo bản ghi thanh toán
                Payments payment = new Payments();
                payment.setUserId(user); // Dùng @ManyToOne Users
                payment.setAmount(cartItemService.getTotal(cart)); // Trả về BigDecimal
                payment.setMethod("VNPAY");
                payment.setTransactionId(request.getParameter("vnp_TransactionNo"));
                payment.setStatus("COMPLETED");
                payment.setCreatedAt(new Date());

                paymentService.save(payment);

                // ✅ 3. Lưu Enrollments & gán payment vào CartItems
                for (CartItems item : cart.getCartItemsSet()) {
                    // Cập nhật payment_id trong cart_items
                    item.setPaymentId(payment);
                    cartItemService.update(item); // bạn cần có hàm update nếu chưa có

                    Enrollments enrollment = new Enrollments();
                    enrollment.setUserId(user);
                    enrollment.setCourseId(item.getCourseId());
                    enrollment.setPaymentId(payment);
                    enrollment.setEnrolledAt(new Date());

                    enrollmentService.save(enrollment);
                }

//                cartItemService.clearCart(cart.getCartId());
            }

            String redirectUrl = String.format(
                    "http://localhost:3000/cart?statusPay=success&user=%s&transactionId=%s",
                    URLEncoder.encode(username, StandardCharsets.UTF_8),
                    URLEncoder.encode(transactionId, StandardCharsets.UTF_8)
            );

            response.sendRedirect(redirectUrl);
        }

        return ResponseEntity.badRequest().body(Map.of(
                "message", "Xác thực chữ ký thất bại hoặc thanh toán không hợp lệ"
        ));
    }

    @PostMapping("/secure/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, String> body, HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            Long courseId = Long.parseLong(body.get("courseId"));
            cartService.addToCart(user.getUserId(), courseId);

            return ResponseEntity.ok(Map.of("message", "Đã thêm vào giỏ hàng"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/secure/cart/total")
    public ResponseEntity<?> getCartTotal(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            Carts cart = cartService.getCartByUser(user.getUserId());
            if (cart == null || cart.getCartItemsSet().isEmpty()) {
                return ResponseEntity.ok(Map.of("total", BigDecimal.ZERO));
            }

            BigDecimal total = cart.getCartItemsSet()
                    .stream()
                    .filter(item -> item.getPaymentId() == null)
                    .map(item -> item.getCourseId().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return ResponseEntity.ok(Map.of("total", total));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }

    @GetMapping("/secure/cart/items")
    public ResponseEntity<?> getCartItems(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            Carts cart = cartService.getCartByUser(user.getUserId());
            if (cart == null || cart.getCartItemsSet().isEmpty()) {
                return ResponseEntity.ok(Map.of("items", List.of())); // Trả về mảng rỗng nếu không có gì
            }

            // Trả về danh sách cartItems kèm thông tin khóa học
            List<Map<String, Object>> items = new ArrayList<>();

            for (CartItems item : cart.getCartItemsSet()) {
                Map<String, Object> map = new HashMap<>();
                map.put("cartItemId", item.getCartItemId());
                map.put("courseId", item.getCourseId().getCourseId());
                map.put("title", item.getCourseId().getTitle());
                map.put("imageUrl", item.getCourseId().getImageUrl());
                map.put("price", item.getCourseId().getPrice());
                items.add(map);
            }

            return ResponseEntity.ok(Map.of("items", items));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/secure/cart/items/{itemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable(value = "itemId") Long itemId,
            HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            CartItems item = cartItemService.getById(itemId);
            if (item == null || !item.getCartId().getUserId().getUserId().equals(user.getUserId())) {
                return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN)
                        .body(Map.of("error", "Bạn không có quyền xóa mục này"));
            }

            cartItemService.delete(itemId);
            return ResponseEntity.ok(Map.of("message", "Đã xoá mục khỏi giỏ hàng"));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/secure/cart/clear")
    public ResponseEntity<?> clearCart(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            Carts cart = cartService.getCartByUser(user.getUserId());
            if (cart != null) {
                cartItemService.clearCart(cart.getCartId());
            }

            return ResponseEntity.ok(Map.of("message", "Đã xoá toàn bộ giỏ hàng"));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/secure/cart/unpaid")
    public ResponseEntity<?> getUnpaidCartItems(HttpServletRequest request) {
        try {
            String username = (String) request.getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Chưa đăng nhập hoặc token không hợp lệ"));
            }

            Users user = userRepo.getUserByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(Map.of("error", "Người dùng không tồn tại"));
            }

            Carts cart = cartService.getCartByUser(user.getUserId());
            if (cart == null || cart.getCartItemsSet().isEmpty()) {
                return ResponseEntity.ok(Map.of("items", List.of()));
            }

            List<Map<String, Object>> items = new ArrayList<>();

            for (CartItems item : cart.getCartItemsSet()) {
                if (item.getPaymentId() == null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("cartItemId", item.getCartItemId());
                    map.put("courseId", item.getCourseId().getCourseId());
                    map.put("title", item.getCourseId().getTitle());
                    map.put("imageUrl", item.getCourseId().getImageUrl());
                    map.put("price", item.getCourseId().getPrice());
                    items.add(map);
                }
            }

            return ResponseEntity.ok(Map.of("items", items));

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
        }
    }
}
