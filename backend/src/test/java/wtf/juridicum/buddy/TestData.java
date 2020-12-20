package wtf.juridicum.buddy;

import wtf.juridicum.buddy.entity.Course;

import java.util.ArrayList;
import java.util.List;

public interface TestData {
    static List<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setName("course " + i);
            courses.add(course);
        }

        return courses;
    }
}