package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.PasswordResetToken;
import com.example.CuoiKy.entity.User;
import com.example.CuoiKy.repository.IPasswordResetTokenRepository;
import com.example.CuoiKy.repository.IRoleRepository;
import com.example.CuoiKy.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private EmailService emailService;


    @Autowired
    private IPasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user)
    {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != 0 && userId != 0) {
            userRepository.addRoleToUser(userId, roleId);
        }
        // Gửi email xác thực
        String verificationLink = "http://localhost:8080/verify?username=" + user.getUsername();
        String emailContent = "Xác nhận gmail vui lòng <a href=\"" + verificationLink + "\">ấn vào đây</a>";
        emailService.sendEmail(user.getEmail(), "Email Verification", emailContent);
    }
    public boolean verifyEmail(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (passToken == null) {
            return "invalidToken";
        }

        User user = passToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        return null;
    }


    public User getUserByPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token).getUser();
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}