package wtf.juridicum.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuddyRequestRepository extends JpaRepository<BuddyRequest, Long> {
    Optional<BuddyRequest> findBuddyRequestByIdAndToken(Long id, String token);

    List<BuddyRequest> findAllByCourseAndAndExamDateAndEmailNotAndConfirmedOrderByOnCreateAsc(Course course, LocalDate examDate, String initiator, boolean confirmed);

    Optional<BuddyRequest> findBuddyRequestByEmailAndCourseAndExamDate(String email, Course course, LocalDate examDate);
}
