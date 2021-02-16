package xyz.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xyz.juridicum.buddy.entity.BuddyRequest;
import xyz.juridicum.buddy.entity.Match;
import xyz.juridicum.buddy.service.IEmailService;

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
    public void requestConfirmEmail(BuddyRequest req) {
        LOGGER.info("Requesting email confirmation for {}", req.getEmail());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply@juridicum.wtf");
        mail.setTo(req.getEmail());
        mail.setSubject("Buddy-Request Email bestätigen");
        mail.setText("Hallo,\n\n" +
                "Öffne die Seite https://juridicum.wtf/confirm/" + req.getId() + "?token=" +
                req.getToken() + " um deine Email zu bestätigen.");

        emailSender.send(mail);
    }

    @Override
    public void sendRegistration(BuddyRequest req) {
        LOGGER.info("Sending registration confirmation for {}", req.getEmail());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply@juridicum.wtf");
        mail.setTo(req.getEmail());
        mail.setSubject("Buddy-Request Bestätigung");

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        mail.setText("Hallo,\n\n" +
                "folgendes wurde erfasst: \n\n" +
                "1. Email " + req.getEmail() + "\n" +
                "2. LVA \"" + req.getCourse().getName() + "\"\n" +
                "3. Prüfungsdatum " + req.getExamDate().format(fmt) + "\n\n" +
                "Wir suchen dir eine/n PrüfungspartnerIn. Wir melden uns wieder.\n\n" +
                "Öffne die Seite https://juridicum.wtf/clear/" + req.getId() + "?token=" +
                req.getToken() + " um deine Anfrage zu löschen. Nachdem " + req.getExamDate().format(fmt) +
                " wird sie automatisch gelöscht.");

        emailSender.send(mail);
    }

    @Override
    public void sendDeleteConfirmationEmail(BuddyRequest req) {
        LOGGER.info("Sending removal confirmation for {}", req.getEmail());

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply@juridicum.wtf");
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

    private void sendMatchConfirmation(Match match, String person1, String person2) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply@juridicum.wtf");
        mail.setTo(person1);
        mail.setSubject("Match gefunden");

        DateTimeFormatter fmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        mail.setText("Hallo,\n\n" +
                "Für die Prüfung der LVA \"" + match.getCourse().getName() + "\" am " + match.getExamDate().format(fmt) + "\n" +
                "wurde ein(e) PartnerIn gefunden. " + person2 + "\n\n" +
                "Du solltest ihn/sie anschreiben!");

        emailSender.send(mail);
    }

    @Override
    public void sendMatchConfirmation(Match match) {
        LOGGER.info("Sending match info to {} and {}", match.getBuddy1(), match.getBuddy2());

        sendMatchConfirmation(match, match.getBuddy1(), match.getBuddy2());
        sendMatchConfirmation(match, match.getBuddy2(), match.getBuddy1());
    }
}
