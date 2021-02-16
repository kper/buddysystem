package xyz.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.juridicum.buddy.entity.BuddyRequest;
import xyz.juridicum.buddy.entity.Queue;
import xyz.juridicum.buddy.repository.BuddyRequestRepository;
import xyz.juridicum.buddy.service.IQueueService;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueService implements IQueueService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    BuddyRequestRepository buddyRequestRepository;
    private final int queueLength = 5;

    public QueueService(BuddyRequestRepository buddyRequestRepository) {
        this.buddyRequestRepository = buddyRequestRepository;
    }

    @Override
    public List<Queue> getAll() {
        LOGGER.debug("getAll queues");

        Pageable page = PageRequest.of(0, this.queueLength);
        List<BuddyRequest> requests = buddyRequestRepository.findAllByConfirmedOrderByOnCreateDescIdDesc(true, page);

        List<Queue> queues = requests.stream().map(w -> {
            Queue q = new Queue();
            q.setCourse(w.getCourse());
            q.setExamDate(w.getExamDate());
            q.setOnCreate(w.getOnCreate());
            return q;
        }).collect(Collectors.toList());

        return queues;
    }
}
