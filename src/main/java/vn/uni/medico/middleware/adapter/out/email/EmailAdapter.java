package vn.uni.medico.middleware.adapter.out.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import vn.uni.medico.middleware.domain.entity.Email;
import vn.uni.medico.shared.application.port.out.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmailAdapter implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    private static List<String> parseEmailAddresses(String text) {
        if (!StringUtils.isEmpty(text)) {
            return List.of(text.split("[,;]"));
        }
        return List.of();
    }

    @Override
    public void send(Email mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmailAddress);
            List<String> toAddresses = parseEmailAddresses(mail.getTo());
            for (String toAddress : toAddresses) {
                helper.addTo(toAddress);
            }
            List<String> ccAddresses = parseEmailAddresses(mail.getCc());
            for (String ccAddress : ccAddresses) {
                helper.addCc(ccAddress);
            }

            List<String> bccAddresses = parseEmailAddresses(mail.getBcc());
            for (String bccAddress : bccAddresses) {
                helper.addBcc(bccAddress);
            }

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody());
//            if (attachmentData != null) {
//                helper.addAttachment(StringUtil.valueOf(attachmentName, ""), new ByteArrayResource(attachmentData), StringUtil.valueOf(attachmentType, "application/octet-stream"));
//            }
            mailSender.send(message);
        } catch (MailException | MessagingException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
