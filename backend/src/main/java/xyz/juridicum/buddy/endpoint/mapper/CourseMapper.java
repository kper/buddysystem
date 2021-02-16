package xyz.juridicum.buddy.endpoint.mapper;

import org.mapstruct.Mapper;
import xyz.juridicum.buddy.endpoint.dto.CourseDto;
import xyz.juridicum.buddy.entity.Course;

import java.util.List;

@Mapper
public interface CourseMapper {
    CourseDto map(Course course);
    List<CourseDto> map(List<Course> courses);
}
