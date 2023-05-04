package org.itstep.advanced;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/root")
    public String admin() {
        return "root";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/guest")
    public String demo() {
        return "guest";
    }
}
