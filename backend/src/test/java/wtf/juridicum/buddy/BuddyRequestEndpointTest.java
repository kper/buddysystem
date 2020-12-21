package wtf.juridicum.buddy;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import wtf.juridicum.buddy.entity.Match;
import wtf.juridicum.buddy.repository.BuddyRequestRepository;
import wtf.juridicum.buddy.repository.CourseRepository;
import wtf.juridicum.buddy.repository.MatchRepository;
import wtf.juridicum.buddy.service.IEmailService;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private MatchRepository matchRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEmailService emailService; // the CI doesn't like it when we interact with apps outside the project

    @BeforeEach
    public void beforeEach() {
        buddyRequestRepository.deleteAll();
        courseRepository.deleteAll();
        matchRepository.deleteAll();
    }

    @Test
    public void whenCreatingRequestThenCorrect() throws Exception {
        BuddyRequest req = TestData.getBuddyRequest();
        doNothing().when(emailService).requestConfirmEmail(req);

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
    public void whenCreatingRequestThenConfirmThenCorrect() throws Exception {
        BuddyRequest req = TestData.getBuddyRequest();
        doNothing().when(emailService).requestConfirmEmail(req);

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

        req = buddyRequestRepository.findById(messageResponse.getId()).get(); // we need the token

        // Confirm

        MvcResult mvcResult2 = this.mockMvc.perform(put(BUDDY_REQUEST_URL + "/" + messageResponse.getId() + "?token=" + req.getToken()))
                .andDo(print())
                .andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();

        assertEquals(HttpStatus.OK.value(), response2.getStatus());
    }

    @Test
    public void givenIdAndTokenThenRemoveCorrect() throws Exception {
        BuddyRequest req = TestData.getBuddyRequest();
        req.setToken("test");
        doNothing().when(emailService).sendDeleteConfirmationEmail(req);

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
        doNothing().when(emailService).sendRegistration(req1);
        doNothing().when(emailService).sendRegistration(req2);
        doNothing().when(emailService).requestConfirmEmail(req1);
        doNothing().when(emailService).requestConfirmEmail(req2);
        doNothing().when(emailService).sendMatchConfirmation(any(Match.class));

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

        BuddyRequestDto messageResponse1 = objectMapper
                .readValue(response1.getContentAsString(), BuddyRequestDto.class);

        req1 = buddyRequestRepository.findById(messageResponse1.getId()).get(); // we need the token
        assertNotNull(req1.getToken());
        assertFalse(req1.isConfirmed());
        assertTrue(matchRepository.findAll().isEmpty());

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

        BuddyRequestDto messageResponse2 = objectMapper
                .readValue(response2.getContentAsString(), BuddyRequestDto.class);

        req2 = buddyRequestRepository.findById(messageResponse2.getId()).get(); // we need the token
        assertNotNull(req2.getToken());
        assertFalse(req2.isConfirmed());
        assertTrue(matchRepository.findAll().isEmpty());

        // Confirm

        {
            MvcResult mvcResult = this.mockMvc.perform(put(BUDDY_REQUEST_URL + "/" + messageResponse1.getId() + "?token=" + req1.getToken()))
                    .andDo(print())
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            assertEquals(HttpStatus.OK.value(), response.getStatus());
        }

        {
            MvcResult mvcResult = this.mockMvc.perform(put(BUDDY_REQUEST_URL + "/" + messageResponse2.getId() + "?token=" + req2.getToken()))
                    .andDo(print())
                    .andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();

            assertEquals(HttpStatus.OK.value(), response.getStatus());
        }

        // Check if match exists

        List<Match> matches = matchRepository.findAll();
        assertTrue(!matches.isEmpty(), "Expecting a match");
        assertEquals(req2.getEmail(), matches.get(0).getBuddy1(), "Expecting that buddy's email match");
        assertEquals(req1.getEmail(), matches.get(0).getBuddy2(), "Expecting that buddy's email match");
        assertNotNull(matches.get(0).getCourse(), "Expecting that a course exists");
        assertEquals(course.getId(), matches.get(0).getCourse().getId(), "Expecting that course match");
        assertEquals(course.getId(), matches.get(0).getCourse().getId(), "Expecting that course match");
        assertEquals(req2.getExamDate(), matches.get(0).getExamDate(), "Expecting that exam dates match");
        assertEquals(req1.getExamDate(), matches.get(0).getExamDate(), "Expecting that exam dates match");
    }
}
