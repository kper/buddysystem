package wtf.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import wtf.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import wtf.juridicum.buddy.entity.BuddyRequest;

@Mapper
public interface BuddyRequestMapper {
    BuddyRequestDto map(BuddyRequest req);
    BuddyRequest map(BuddyRequestDto req);
}
