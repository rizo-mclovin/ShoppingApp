package com.example.waikan.services;

import com.example.waikan.facades.ImageFacade;
import com.example.waikan.models.Image;
import com.example.waikan.models.User;
import com.example.waikan.models.enums.Role;
import com.example.waikan.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;
import java.util.zip.Deflater;

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
    private final ImageFacade imageFacade;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       MailSender mailSender, ImageFacade imageFacade) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.imageFacade = imageFacade;
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

    public User getUpdateUserFromDb(User user) {
        return userRepository.findById(user.getId())
                .orElse(null);
    }

    private void sendEmailMessage(String userEmail, String activationCode, String nikName) {
        String messageText = String.format("Здраствуйте, %s!" +
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

    public void editProfile(String nikName, String phoneNumber, MultipartFile avatar, String email) throws IOException {
        User userFromDb = userRepository.findByEmail(email);
        if (userFromDb == null) throw new UsernameNotFoundException("Email " + email + " is not found");
        userFromDb.setNikName(nikName);
        userFromDb.setNumberPhone(phoneNumber);
        if (avatar.getSize() != 0) {
            Image imageAvatar = imageFacade.toEntity(avatar);
            imageAvatar.setBytes(compressBytes(imageAvatar.getBytes()));
            userFromDb.setAvatar(imageAvatar);
        }
        log.info("Edit user: " + userFromDb.getEmail());
        userRepository.save(userFromDb);
    }


    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - "
                + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public User getUserByPrincipal(Principal principal) {
        User defaultUser = new User();
        if (principal == null) return defaultUser;
        String name = principal.getName();
        User user = userRepository.findByEmail(name);
        if (user == null) return defaultUser;
        return user;
    }
}
