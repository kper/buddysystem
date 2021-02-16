package xyz.juridicum.buddy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import xyz.juridicum.buddy.entity.Course;
import xyz.juridicum.buddy.repository.CourseRepository;
import xyz.juridicum.buddy.service.ICourseService;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CourseService implements ICourseService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        LOGGER.info("getAll()");
        return courseRepository.findAllByOrderByName();
    }
}
