package xyz.juridicum.buddy;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FallbackRouter {
    @RequestMapping(value = "/**/{path:[^.]*}")
    public String redirect() {
        return "forward:/";
    }
}
