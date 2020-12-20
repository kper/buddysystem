package wtf.juridicum.buddy.service;

import wtf.juridicum.buddy.entity.BuddyRequest;

public interface IEmailService {
    /**
     * Send a confirmation for registering a buddy request.
     * This email will be send to the defined email in `req.email`.
     * @param req is the corresponding buddy request
     */
    void sendRegistration(BuddyRequest req);
}
