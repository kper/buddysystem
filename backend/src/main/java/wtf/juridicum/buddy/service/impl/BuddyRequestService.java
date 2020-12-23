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
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.IEmailService;
import wtf.juridicum.buddy.service.IMatchService;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.lang.invoke.MethodHandles;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BuddyRequestService implements IBuddyRequestService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private BuddyRequestRepository buddyRequestRepository;
    private CourseRepository courseRepository;
    private IMatchService matchService;
    private IEmailService emailService;

    private final int TOKEN_LENGTH = 10;

    public BuddyRequestService(BuddyRequestRepository buddyRequestRepository,
                               CourseRepository courseRepository,
                               IEmailService emailService,
                               IMatchService matchService) {
        this.buddyRequestRepository = buddyRequestRepository;
        this.courseRepository = courseRepository;
        this.emailService = emailService;
        this.matchService = matchService;
    }

    @Override
    @Transactional
    public BuddyRequest create(BuddyRequest request, Long courseId) {
        request.setOnCreate(LocalDateTime.now());
        request.setToken(RandomStringUtils.random(TOKEN_LENGTH, 0, 0, true, true,
                null, new SecureRandom()));

        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        if (buddyRequestRepository.findBuddyRequestByEmailAndCourseAndExamDate(request.getEmail(),
                course.get(), request.getExamDate()).isPresent()) {
            throw new ValidationException("A request has been already made for this exam.");
        }

        request.setCourse(course.get());
        buddyRequestRepository.save(request);

        this.emailService.requestConfirmEmail(request);

        return request;
    }

    @Override
    @Transactional
    public void deleteRequest(Long id, String token) {
        LOGGER.info("Deleting request for {}", id);
        Optional<BuddyRequest> req = buddyRequestRepository.findBuddyRequestByIdAndToken(id, token);

        if (req.isPresent()) {
            buddyRequestRepository.delete(req.get());
            LOGGER.debug("Sending confirmation email to {}", req.get().getEmail());
            this.emailService.sendDeleteConfirmationEmail(req.get());
        }
    }

    @Override
    @Transactional
    public void confirmEmail(Long id, String token) {
        LOGGER.info("Confirming email for {}", id);

        Optional<BuddyRequest> req = buddyRequestRepository.findBuddyRequestByIdAndToken(id, token);

        if (req.isPresent()) {
            if (!req.get().isConfirmed()) {
                req.get().setConfirmed(true);
                LOGGER.debug("Confirmation correct for {}", id);

                // Matching
                Optional<Match> match = matchService.checkMatchesAndCreate(req.get());
                if (match.isEmpty()) {
                    LOGGER.info("No match found, therefore sending registration info");
                    this.emailService.sendRegistration(req.get());
                }
                else {
                    LOGGER.info("A match found, therefore *not* sending registration info");
                }
            } else {
                throw new ValidationException("Already confirmed");
            }
        } else {
            throw new NotFoundException("Id with token doesn't match");
        }
    }
}
