package com.zmj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HelloController {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("info: say hello!!!");
        logger.debug("debug: say hello!!!");
        logger.warn("warn: say hello!!!");
        logger.error("error: hello!!!");
        return "Hello World!!!";
    }
}
