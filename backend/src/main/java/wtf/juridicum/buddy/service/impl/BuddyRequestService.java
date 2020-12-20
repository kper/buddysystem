package wtf.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.ICourseService;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class BuddyRequestService implements IBuddyRequestService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private BuddyRequestRepository buddyRequestRepository;

    public BuddyRequestService(BuddyRequestRepository buddyRequestRepository) {
        this.buddyRequestRepository = buddyRequestRepository;
    }

    @Override
    @Transactional
    public BuddyRequest create(BuddyRequest request) {
        buddyRequestRepository.save(request);

        return request;
    }
}
