package xyz.juridicum.buddy.service;

import xyz.juridicum.buddy.entity.Course;

import java.util.List;

public interface ICourseService {

    /**
     * Get all courses from the database
     * @return a list of courses, ordered by name
     */
    List<Course> getAll();
}
