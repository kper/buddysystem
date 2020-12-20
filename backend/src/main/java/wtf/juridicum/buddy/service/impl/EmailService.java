package wtf.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.service.IEmailService;

import java.lang.invoke.MethodHandles;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class EmailService implements IEmailService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendRegistration(BuddyRequest req) {
        LOGGER.info("Sending registration confirmation for {}", req.getEmail());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("admin@juridicum.wtf");
        mail.setTo(req.getEmail());
        mail.setSubject("Buddy-Request Bestätigung");

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        mail.setText("Hallo,\n\n" +
                "folgendes wurde erfasst: \n\n" +
                "1. Email " + req.getEmail() + "\n" +
                "2. LVA \"" + req.getCourse().getName() + "\"\n" +
                "3. Prüfungsdatum " + req.getExamDate().format(fmt) + "\n\n" +
                "Wir suchen dir eine/n PrüfungspartnerIn. Wir melden uns wieder.\n\n" +
                "Öffne die Seite https://juridicum.wtf/buddyrequest/" + req.getId() + "?token=" +
                req.getToken() + " um deine Anfrage zu löschen. Nach dem " + req.getExamDate().format(fmt) +
                " wird sie automatisch gelöscht.");

        emailSender.send(mail);
    }

    @Override
    public void sendDeleteConfirmationEmail(BuddyRequest req) {
        LOGGER.info("Sending removal confirmation for {}", req.getEmail());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("admin@juridicum.wtf");
        mail.setTo(req.getEmail());
        mail.setSubject("Buddy-Request Lösch-Bestätigung");

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        mail.setText("Hallo,\n\n" +
                "1. Email " + req.getEmail() + "\n" +
                "2. LVA \"" + req.getCourse().getName() + "\"\n" +
                "3. Prüfungsdatum " + req.getExamDate().format(fmt) + "\n\n" +
                "Deine Anfrage wurde gelöscht.");

        emailSender.send(mail);
    }
}
