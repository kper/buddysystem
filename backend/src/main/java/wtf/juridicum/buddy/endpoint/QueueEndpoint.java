package wtf.juridicum.buddy.endpoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wtf.juridicum.buddy.endpoint.dto.CourseDto;
import wtf.juridicum.buddy.endpoint.dto.QueueDto;
import wtf.juridicum.buddy.endpoint.mapper.CourseMapper;
import wtf.juridicum.buddy.endpoint.mapper.QueueMapper;
import wtf.juridicum.buddy.service.ICourseService;
import wtf.juridicum.buddy.service.IQueueService;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/queue")
public class QueueEndpoint {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    QueueMapper queueMapper;
    IQueueService queueService;

    public QueueEndpoint(QueueMapper queueMapper, IQueueService queueService) {
        this.queueMapper = queueMapper;
        this.queueService = queueService;
    }

    @GetMapping
    public List<QueueDto> getAll() {
        LOGGER.info("GET /api/v1/queue/");
        return queueMapper.map(queueService.getAll());
    }
}
