package kz.iiitu.spring_lab_01.web;

import kz.iiitu.spring_lab_01.notify.NotificationService;
import org.springframework.web.bind.annotation.*;
import kz.iiitu.spring_lab_01.lifecycle.LifecycleDemo;
import java.util.List;
import kz.iiitu.spring_lab_01.scope.TicketOffice;
import java.util.Map;

import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final LifecycleDemo lifecycle;
    private final TicketOffice office;

    public Lab2Controller(NotificationService notifications, LifecycleDemo lifecycle, TicketOffice office) {
        this.notifications = notifications;
        this.lifecycle = lifecycle;
        this.office = office;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(@RequestParam(defaultValue = "Hello") String text) {
        return Map.of(
                "primary", notifications.viaPrimary(text),
                "console", notifications.viaConsole(text),
                "all", notifications.viaAll(text),
                "beanNames", notifications.names()
        );
    }
    @GetMapping("/lifecycle")
    public List<String> lifecycle() {
        return lifecycle.events();
    }
    @GetMapping("/scopes")
    public Map<String, Object> scopes() {
        return office.demo();
    }
}