package com.example.waikan.services;

import com.example.waikan.models.User;
import com.example.waikan.models.enums.Role;
import com.example.waikan.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private static final Logger log =
            LoggerFactory.getLogger(UserService.class);
    @Value("${mail.username}")
    private String emailFrom;
    @Value("${host.name}")
    private String hostName;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public boolean createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendEmailMessage(user.getEmail(), user.getActivationCode(), user.getNikName());
        return true;
    }

    private void sendEmailMessage(String userEmail, String activationCode, String nikName) {
        String messageText = String.format("Привет, %s!" +
                " Благодарим вас за проявленный интерес к торговой площадке Waikan." +
                " Чтобы активировать ваш аккаунт нужно перейти по ссылке %s", nikName,
                hostName + "activate/" + activationCode);
        SimpleMailMessage messageToActivateUser = new SimpleMailMessage();
        messageToActivateUser.setTo(userEmail);
        messageToActivateUser.setFrom(emailFrom);
        messageToActivateUser.setSubject("Подтверждение адреса электронной почты");
        messageToActivateUser.setText(messageText);

        mailSender.send(messageToActivateUser);
    }

    public boolean activateUser(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            log.info("Activate user: {}, email: {}", user.getNikName(), user.getEmail());
            user.setActivationCode("activate");
            user.setActive(true);
            userRepository.save(user);
            return true;
        } else return false;
    }

}
