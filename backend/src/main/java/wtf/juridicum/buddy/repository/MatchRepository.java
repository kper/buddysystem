package wtf.juridicum.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wtf.juridicum.buddy.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
