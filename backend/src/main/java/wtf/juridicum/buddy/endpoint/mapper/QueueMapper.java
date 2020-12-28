package wtf.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import wtf.juridicum.buddy.endpoint.dto.CourseDto;
import wtf.juridicum.buddy.endpoint.dto.QueueDto;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.entity.Queue;

import java.util.List;

@Mapper
public interface QueueMapper {
    List<QueueDto> map(List<Queue> queues);
}
