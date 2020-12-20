package wtf.juridicum.buddy;


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
import wtf.juridicum.buddy.endpoint.dto.BuddyRequestDto;
import wtf.juridicum.buddy.endpoint.dto.CourseDto;
import wtf.juridicum.buddy.endpoint.dto.CreateBuddyRequestDto;
import wtf.juridicum.buddy.endpoint.mapper.BuddyRequestMapper;
import wtf.juridicum.buddy.endpoint.mapper.CourseMapper;
import wtf.juridicum.buddy.entity.BuddyRequest;
import wtf.juridicum.buddy.entity.Course;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class BuddyRequestEndpoint implements TestData {
    private static final String BUDDY_REQUEST_URL = "/api/v1/buddyrequest";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuddyRequestMapper buddyRequestMapper;

    @Autowired
    private BuddyRequestRepository buddyRequestRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        buddyRequestRepository.deleteAll();
    }

    @Test
    public void whenCreatingRequestThenCorrect() throws Exception {
        BuddyRequest req = TestData.getBuddyRequest();

        Course course = TestData.getCourse();
        courseRepository.save(course);

        CreateBuddyRequestDto dto = buddyRequestMapper.map2(req);
        dto.setCourseId(course.getId());
        String body = objectMapper.writeValueAsString(dto);

        MvcResult mvcResult = this.mockMvc.perform(post(BUDDY_REQUEST_URL + "/")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        BuddyRequestDto messageResponse = objectMapper
                .readValue(response.getContentAsString(), BuddyRequestDto.class);

        assertNotNull(messageResponse.getId(), "Response should have an id");
        assertNotNull(messageResponse.getOnCreate(), "Response should have a create date");
        assertEquals(req.getEmail(), messageResponse.getEmail(), "Response should have the same email");
        assertNotNull(messageResponse.getCourse().getId(), "Response's course should not be null");
        assertEquals(course.getName(),
                messageResponse.getCourse().getName(),
                "Response should fetch the name of the course");

    }
}
