package wtf.juridicum.buddy.service;

import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Match;

public interface IEmailService {
    /**
     * Send a confirmation for registering a buddy request.
     * This email will be send to the defined email in `req.email`.
     * @param req is the corresponding buddy request
     */
    void sendRegistration(BuddyRequest req);

    /**
     * Send a confirmation for removing a buddy request.
     * @param buddyRequest is the corresponding buddy request
     */
    void sendDeleteConfirmationEmail(BuddyRequest buddyRequest);

    /**
     * Inform both buddies about the match.
     * @param match
     */
    void sendMatchConfirmation(Match match);
}
