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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class BuddyRequestEndpointTest implements TestData {
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

    @Test
    public void givenIdAndTokenThenRemoveCorrect() throws Exception {
        BuddyRequest req = TestData.getBuddyRequest();
        req.setToken("test");

        Course course = TestData.getCourse();
        courseRepository.save(course);

        req.setCourse(course);
        buddyRequestRepository.save(req);

        MvcResult mvcResult = this.mockMvc.perform(delete(BUDDY_REQUEST_URL + "/" + req.getId() + "?token=test"))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(buddyRequestRepository.findById(req.getId()).isEmpty(), "Buddy request should not exist anymore");
    }

    @Test
    public void givenTwoBuddiesThenCreateCorrect() throws Exception {
        Course course = TestData.getCourse();
        courseRepository.save(course);

        BuddyRequest req1 = TestData.getBuddyRequest();
        BuddyRequest req2 = TestData.getBuddyRequest();
        req2.setEmail("toni@email.com");


        CreateBuddyRequestDto dto1 = buddyRequestMapper.map2(req1);
        dto1.setCourseId(course.getId());
        String body1 = objectMapper.writeValueAsString(dto1);

        MvcResult mvcResult1 = this.mockMvc.perform(post(BUDDY_REQUEST_URL + "/")
                .content(body1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response1 = mvcResult1.getResponse();

        assertEquals(HttpStatus.OK.value(), response1.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response1.getContentType());

        // req 2

        CreateBuddyRequestDto dto2 = buddyRequestMapper.map2(req2);
        dto2.setCourseId(course.getId());
        String body2 = objectMapper.writeValueAsString(dto2);

        MvcResult mvcResult2 = this.mockMvc.perform(post(BUDDY_REQUEST_URL + "/")
                .content(body2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();

        assertEquals(HttpStatus.OK.value(), response2.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response2.getContentType());
    }
}
