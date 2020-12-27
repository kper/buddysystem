package wtf.juridicum.buddy;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wtf.juridicum.buddy.entity.Match;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.MatchRepository;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class CleanUpDatabaseTask {
    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private MatchRepository matchRepository;
    private BuddyRequestRepository buddyRequestRepository;

    public CleanUpDatabaseTask(MatchRepository matchRepository, BuddyRequestRepository buddyRequestRepository) {
        this.matchRepository = matchRepository;
        this.buddyRequestRepository = buddyRequestRepository;
    }

    @Scheduled(cron = "0 0 7 * * *")
    @Transactional
    public void cleanUp() {
        LOGGER.info("Cleaning up {}", dateFormat.format(new Date()));

        matchRepository.deleteAllByExamDateBefore(LocalDate.now());
        buddyRequestRepository.deleteAllByExamDateBefore(LocalDate.now());
    }
}
