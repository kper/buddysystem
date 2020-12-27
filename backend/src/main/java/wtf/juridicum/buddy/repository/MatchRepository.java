package wtf.juridicum.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wtf.juridicum.buddy.entity.Match;

import java.time.LocalDate;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    void deleteAllByExamDateBefore(LocalDate today);
}
