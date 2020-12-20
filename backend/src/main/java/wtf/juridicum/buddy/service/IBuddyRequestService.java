package wtf.juridicum.buddy.service;

import wtf.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import wtf.juridicum.buddy.entity.BuddyRequest;

public interface IBuddyRequestService {
    /**
     * Creates a new buddy request with the given parameters.
     *
     * @param request owns the data
     * @return the created request from the database.
     */
    BuddyRequest create(BuddyRequest request);
}
