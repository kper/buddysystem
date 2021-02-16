package xyz.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import xyz.juridicum.buddy.endpoint.dto.QueueDto;
import xyz.juridicum.buddy.entity.Queue;

import java.util.List;

@Mapper
public interface QueueMapper {
    List<QueueDto> map(List<Queue> queues);
}
