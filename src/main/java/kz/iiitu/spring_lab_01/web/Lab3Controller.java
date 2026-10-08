package kz.iiitu.spring_lab_01.web;

import kz.iiitu.spring_lab_01.config.AppProperties;
import kz.iiitu.spring_lab_01.config.EnvironmentBanner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(AppProperties props,
                          EnvironmentBanner banner,
                          Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        return Map.of(
                "owner", props.owner(),
                "group", props.group(),
                "mail", props.mail(),
                "rateLimit", props.rateLimit(),   // инд. задание
                "banner", banner.describe(),
                "activeProfiles", Arrays.asList(environment.getActiveProfiles())
        );
    }
}