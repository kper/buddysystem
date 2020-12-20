package wtf.juridicum.buddy.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wtf.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import wtf.juridicum.buddy.endpoint.dto.CourseDto;
import wtf.juridicum.buddy.endpoint.mapper.BuddyRequestMapper;
import wtf.juridicum.buddy.endpoint.mapper.CourseMapper;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.service.IBuddyRequestService;
import wtf.juridicum.buddy.service.ICourseService;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

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
    public BuddyRequestDto createRequest(@Valid @RequestBody BuddyRequestDto body) {
        LOGGER.info("POST /api/v1/buddyrequest");
        BuddyRequest request =  buddyRequestMapper.map(body);

        return buddyRequestMapper.map(buddyRequestService.create(request));
    }
}
