package wtf.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import wtf.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import wtf.juridicum.buddy.endpoint.dto.CreateBuddyRequestDto;
import wtf.juridicum.buddy.entity.BuddyRequest;

@Mapper
public interface BuddyRequestMapper {
    BuddyRequestDto map(BuddyRequest req);
    BuddyRequest map(BuddyRequestDto req);

    BuddyRequest map(CreateBuddyRequestDto req);

    CreateBuddyRequestDto map2(BuddyRequest req); // for testing
}
