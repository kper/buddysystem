package wtf.juridicum.buddy;

import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    static BuddyRequest getBuddyRequest() {
        BuddyRequest req = new BuddyRequest();
        req.setEmail("buddy@email.com");
        req.setExamDate(LocalDate.MAX);
        req.setOnCreate(LocalDateTime.now());
        return req;
    }

    static Course getCourse() {
        Course course = new Course();
        course.setName("course");
        return course;
    }
}
