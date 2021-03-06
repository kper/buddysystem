package xyz.juridicum.buddy.service;

import xyz.juridicum.buddy.entity.BuddyRequest;

public interface IBuddyRequestService {
    /**
     * Creates a new buddy request with the given parameters.
     *
     * @param request  owns the data
     * @param courseId identifes the course to which someone wants a buddy
     * @return the created request from the database.
     */
    BuddyRequest create(BuddyRequest request, Long courseId);

    /**
     * Deletes a buddy request with `id` when `token` is correct and sends an email.
     * @param id identifies a request
     * @param token is a secret for deletion
     */
    void deleteRequest(Long id, String token);

    /**
     * Confirm that a email belongs to the `id`.
     * @param id identifies the buddy request
     * @param token is the shared secret
     */
    void confirmEmail(Long id, String token);
}
