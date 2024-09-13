package emiresen.tennisleaguespring.service;

import emiresen.tennisleaguespring.document.EmailConfirmationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(EmailConfirmationToken emailConfirmationToken) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailConfirmationToken.getPlayer().getEmail());
        helper.setSubject("Confirm your E-Mail - Tennis Club");
        String htmlContent = loadHtmlTemplate(
                Map.of(
                        "firstName", emailConfirmationToken.getPlayer().getFirstname(),
                        "confirmationLink", generateConfirmationLink(emailConfirmationToken.getToken())
                )
        );
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    private String loadHtmlTemplate(Map<String, String> placeholders) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/" + "email-confirmation.html");
            String content = Files.readString(resource.getFile().toPath());

            // Replace placeholders
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return content;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load email template", e);
        }
    }

    private String generateConfirmationLink(String token){
        return "http://localhost:9090/api/v1/auth/verify-email?token="+token;
    }

}
