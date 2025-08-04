package com.nth_ntq.utils;

import com.nth_ntq.configs.VNPayConfig;
import static com.nth_ntq.configs.VNPayConfig.hmacSHA512;
import static com.nth_ntq.configs.VNPayConfig.vnp_HashSecret;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class VNPayUtils {

    public String createOrder(int total, String orderInfo, String baseReturnUrl, String bankCode, String username) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
//        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_TxnRef = Base64.getEncoder().encodeToString((username + ":" + System.currentTimeMillis()).getBytes());

        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "billpayment";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//        vnp_Params.put("vnp_BankCode", "NCB");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        vnp_Params.forEach((k, v) -> System.out.println(k + " = " + v));
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                try {
                    hashData.append(fieldName).append("=")
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                            .append("=")
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        return VNPayConfig.vnp_PayUrl + "?" + query.toString();
    }

    public int handleReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String value = request.getParameter(param);
            if (value != null && !value.isEmpty()) {
                fields.put(param, value);
            }
        }

        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        String signValue = VNPayUtils.hashAllFields(fields);

        System.out.println("=== DEBUG VNPAY RETURN ===");
        System.out.println("VNPay SecureHash     : " + vnp_SecureHash);
        System.out.println("Server Calculated Hash: " + signValue);
        System.out.println("Sorted Fields: " + fields);
        System.out.println("==========================");

        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1; // Thanh toán thành công
            } else {
                return 0; // Giao dịch thất bại
            }
        } else {
            return -1; // Sai checksum
        }
    }

    public static String hashAllFields(Map<String, String> fields) {
        SortedMap<String, String> sortedFields = new TreeMap<>(fields);
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : sortedFields.entrySet()) {
            try {
                String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString());
                String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                sb.append(encodedKey).append('=').append(encodedValue).append('&');
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Xóa ký tự `&` cuối
        }

        return VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, sb.toString());
    }

}
