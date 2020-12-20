package wtf.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.exception.NotFoundException;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.ICourseService;
import wtf.juridicum.buddy.service.IEmailService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BuddyRequestService implements IBuddyRequestService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private BuddyRequestRepository buddyRequestRepository;
    private CourseRepository courseRepository;
    private IEmailService emailService;

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

        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isEmpty()) {
            throw new NotFoundException("Course not found");
        }

        request.setCourse(course.get());
        buddyRequestRepository.save(request);

        this.emailService.sendRegistration(request);

        return request;
    }
}
