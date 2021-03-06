package xyz.juridicum.buddy.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.juridicum.buddy.endpoint.dto.CourseDto;
import xyz.juridicum.buddy.endpoint.mapper.CourseMapper;
import xyz.juridicum.buddy.service.ICourseService;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/course")
public class CourseEndpoint {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    CourseMapper courseMapper;
    ICourseService courseService;

    public CourseEndpoint(CourseMapper courseMapper, ICourseService courseService) {
        this.courseMapper = courseMapper;
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDto> getAll() {
        LOGGER.info("GET /api/v1/course/");
        return courseMapper.map(courseService.getAll());
    }
}
