package wtf.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import wtf.juridicum.buddy.endpoint.dto.CourseDto;
import wtf.juridicum.buddy.entity.Course;

import java.util.List;

@Mapper
public interface CourseMapper {
    CourseDto map(Course course);
    List<CourseDto> map(List<Course> courses);
}
