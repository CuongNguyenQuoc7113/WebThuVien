package com.example.CuoiKy.controller;

import com.example.CuoiKy.config.Config;
import com.example.CuoiKy.entity.BorrowDetail;
import com.example.CuoiKy.entity.Invoice;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.repository.IInvoiceRepository;
import com.example.CuoiKy.service.BorrowDetailService;
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
@RequestMapping("/fine")
public class FineController {

    @Autowired
    private BorrowDetailService borrowDetailService;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private UserService userService;
    // Other autowired services and repositories...

    @PostMapping("/create-payment")
    public void createPayment(@RequestParam("fineAmount") Double fineAmount,
                              @RequestParam("borrowDetailId") Long borrowDetailId,
                              HttpServletResponse response,
                              HttpSession session) throws IOException {
        long amount = fineAmount.longValue();
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay requires the amount in đồng
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/fine/vnpay-return");
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

        // Save borrowDetailId to session for processing after payment
        session.setAttribute("borrowDetailId", borrowDetailId);
        session.setAttribute("amount", amount);
        response.sendRedirect(paymentUrl);
    }

    @GetMapping("/vnpay-return")
    public void vnPayReturn(@RequestParam Map<String, String> params,
                            Principal principal,
                            HttpServletResponse response,
                            HttpSession session) throws IOException {

        Long borrowDetailId = (Long) session.getAttribute("borrowDetailId");

        if (borrowDetailId == null) {
            response.sendRedirect("http://localhost:8080/user/profile?paymentStatus=failure");
            return;
        }

        session.removeAttribute("borrowDetailId");

        // Check if payment was successful based on VNPay response
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        boolean paymentSuccess = "00".equals(vnp_ResponseCode); // VNPay success response code
        User user = userService.findByUsername(principal.getName());
        if (paymentSuccess) {
            // Update isFines field of BorrowDetail with borrowDetailId
            Invoice invoice = new Invoice();
            invoice.setUser(user);
            invoice.setInvoiceType("fine");
            invoice.setAmount((Long) session.getAttribute("amount"));
            invoice.setCreateDate(new Date());
            invoiceRepository.save(invoice);
            borrowDetailService.updateIsFines(borrowDetailId);

            //xóa session
            session.removeAttribute("amount");

            response.sendRedirect("http://localhost:8080/notification");
        } else {
            response.sendRedirect("http://localhost:8080/user/profile?paymentStatus=failure");
        }
    }
}
