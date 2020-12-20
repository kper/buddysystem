package wtf.juridicum.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wtf.juridicum.buddy.entity.BuddyRequest;

@Repository
public interface BuddyRequestRepository extends JpaRepository<BuddyRequest, Long> {
}
