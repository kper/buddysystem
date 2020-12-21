package wtf.juridicum.buddy.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.entity.Match;
import wtf.juridicum.buddy.exception.NotFoundException;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;
import wtf.juridicum.buddy.repository.MatchRepository;
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.IEmailService;
import wtf.juridicum.buddy.service.IMatchService;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService implements IMatchService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private MatchRepository matchRepository;
    private IEmailService emailService;
    private BuddyRequestRepository buddyRequestRepository;

    public MatchService(MatchRepository matchRepository,
                        IEmailService emailService,
                        BuddyRequestRepository buddyRequestRepository) {
        this.matchRepository = matchRepository;
        this.emailService = emailService;
        this.buddyRequestRepository = buddyRequestRepository;
    }

    @Override
    @Transactional
    public Optional<Match> checkMatchesAndCreate(BuddyRequest initiator) {
        LOGGER.info("check for matches, initiated by {}", initiator.getEmail());
        List<BuddyRequest> matches = buddyRequestRepository
                .findAllByCourseAndAndExamDateAndEmailNotAndConfirmedOrderByOnCreateAsc(initiator.getCourse(), initiator.getExamDate(), initiator.getEmail(), true);

        if (matches.size() == 0) {
            LOGGER.info("No matches found");
            return Optional.empty();
        }

        LOGGER.info("Match found with {}", matches.get(0).getEmail());

        Match match = new Match();
        match.setBuddy1(initiator.getEmail());
        match.setBuddy2(matches.get(0).getEmail());
        match.setExamDate(initiator.getExamDate());
        match.setCourse(initiator.getCourse());
        match.setOnCreate(LocalDateTime.now());

        matchRepository.save(match);

        buddyRequestRepository.delete(initiator);
        buddyRequestRepository.delete(matches.get(0));

        emailService.sendMatchConfirmation(match);

        return Optional.of(match);
    }
}
