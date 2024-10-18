package com.example.CuoiKy.controller;

import com.example.CuoiKy.config.Config;
import com.example.CuoiKy.entity.Invoice;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.repository.IInvoiceRepository;
import com.example.CuoiKy.service.CardService;
import com.example.CuoiKy.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;
    @Autowired
    private IInvoiceRepository invoiceRepository;

    @PostMapping("/create-payment")
    public void createPayment(@RequestParam("months") int months, HttpServletResponse response, HttpSession session) throws IOException {
        long amount = months * 50000; // Sửa giá trị nhân đúng số tiền mỗi tháng
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay yêu cầu số tiền là đơn vị đồng x 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/api/payment/vnpay-return");
        vnp_Params.put("vnp_IpAddr", "13.160.92.202");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        // Lưu số tháng vào session
        session.setAttribute("months", months);

        response.sendRedirect(paymentUrl);
    }

    @GetMapping("/vnpay-return")
    public void vnPayReturn(@RequestParam Map<String, String> params, HttpServletResponse response, HttpSession session, Principal principal) throws IOException {
        // Lấy số tháng từ session
        Integer months = (Integer) session.getAttribute("months");

        if (months == null) {
            response.sendRedirect("http://localhost:8080/user/profile?paymentStatus=failure");
            return;
        }

        // Xóa số tháng khỏi session để tránh dùng lại nhiều lần
        session.removeAttribute("months");

        User user = userService.findByUsername(principal.getName());

        // Giả sử bạn đã xác thực thành công thanh toán từ VNPay
        boolean paymentSuccess = true; // Thay đổi điều kiện này dựa trên phản hồi thực tế từ VNPay

        if (paymentSuccess) {
            // Kiểm tra xem có phải tạo thẻ mới hay gia hạn thẻ
            Invoice invoice = new Invoice();
            invoice.setUser(user);
            invoice.setInvoiceType(cardService.hasCard(user) ? "renew" : "create");
            invoice.setAmount((long) months * 50000);
            invoice.setCreateDate(new Date());
            invoiceRepository.save(invoice);
            if (cardService.hasCard(user)) {
                cardService.renewCard(user, months);
            } else {
                cardService.createCard(user, months);
            }
            response.sendRedirect("http://localhost:8080/user/profile?paymentStatus=success");
        } else {
            response.sendRedirect("http://localhost:8080/user/profile?paymentStatus=failure");
        }
    }
}
