package xyz.juridicum.buddy;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import xyz.juridicum.buddy.endpoint.dto.CourseDto;
import xyz.juridicum.buddy.endpoint.mapper.CourseMapper;
import xyz.juridicum.buddy.entity.Course;
import xyz.juridicum.buddy.repository.CourseRepository;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class CourseEndpointTest implements TestData {
    private static final String COURSES_BASE_URI = "/api/v1/course";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        courseRepository.deleteAll();
    }

    @Test
    public void whenFetchingCoursesThenCorrect() throws Exception {
        List<Course> courses = TestData.getCourses();
        courseRepository.saveAll(courses);

        MvcResult mvcResult = this.mockMvc.perform(get(COURSES_BASE_URI + "/"))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        CourseDto[] messageResponse = objectMapper
                .readValue(response.getContentAsString(), CourseDto[].class);

        assertEquals(courses.size(), messageResponse.length, "Returned list should have same length");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            assertNotNull(messageResponse[i].getId(), "Response should have an id");
            assertEquals(course.getName(), messageResponse[i].getName(), "Response should have the same name");
        }

}
}
