package xyz.juridicum.buddy.service;

import xyz.juridicum.buddy.entity.BuddyRequest;
import xyz.juridicum.buddy.entity.Match;

import java.util.Optional;

public interface IMatchService {
    /**
     * Checks if there are any matches for the initiator and if yes, then create a match.
     *
     * @param initiator has created a buddy request
     * @return the match if created
     */
    Optional<Match> checkMatchesAndCreate(BuddyRequest initiator);

}
