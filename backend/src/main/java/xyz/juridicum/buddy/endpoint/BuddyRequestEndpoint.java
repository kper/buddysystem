package xyz.juridicum.buddy.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import xyz.juridicum.buddy.endpoint.dto.CreateBuddyRequestDto;
import xyz.juridicum.buddy.endpoint.mapper.BuddyRequestMapper;
import xyz.juridicum.buddy.entity.BuddyRequest;
import xyz.juridicum.buddy.service.IBuddyRequestService;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/buddyrequest")
public class BuddyRequestEndpoint {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    BuddyRequestMapper buddyRequestMapper;
    IBuddyRequestService buddyRequestService;

    public BuddyRequestEndpoint(BuddyRequestMapper buddyRequestMapper, IBuddyRequestService buddyRequestService) {
        this.buddyRequestMapper = buddyRequestMapper;
        this.buddyRequestService = buddyRequestService;
    }

    @PostMapping
    public BuddyRequestDto createRequest(@Valid @RequestBody CreateBuddyRequestDto body) {
        LOGGER.info("POST /api/v1/buddyrequest");
        BuddyRequest request =  buddyRequestMapper.map(body);

        return buddyRequestMapper.map(buddyRequestService.create(request, body.getCourseId()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void confirmEmail(@PathVariable Long id, @RequestParam(value = "token", required = true) String token) {
        LOGGER.info("PUT /api/v1/buddyrequest/{id}", id);

        buddyRequestService.confirmEmail(id, token);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteRequest(@PathVariable Long id, @RequestParam(value = "token", required = true) String token) {
        LOGGER.info("DELETE /api/v1/buddyrequest/{id}", id);

        buddyRequestService.deleteRequest(id, token);
    }
}
