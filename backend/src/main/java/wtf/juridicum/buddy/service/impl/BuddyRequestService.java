package wtf.juridicum.buddy.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.exception.NotFoundException;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.IEmailService;

import javax.transaction.Transactional;
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
    private IEmailService emailService;

    private final int TOKEN_LENGTH = 10;

    public BuddyRequestService(BuddyRequestRepository buddyRequestRepository,
                               CourseRepository courseRepository,
                               IEmailService emailService) {
        this.buddyRequestRepository = buddyRequestRepository;
        this.courseRepository = courseRepository;
        this.emailService = emailService;
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

        request.setCourse(course.get());
        buddyRequestRepository.save(request);

        this.emailService.sendRegistration(request);

        return request;
    }

    @Override
    @Transactional
    public void deleteRequest(Long id, String token) {
        Optional<BuddyRequest> req = buddyRequestRepository.findBuddyRequestByIdAndToken(id, token);

        if (req.isPresent()) {
            buddyRequestRepository.delete(req.get());
            this.emailService.sendDeleteConfirmationEmail(req.get());
        }
    }
}
