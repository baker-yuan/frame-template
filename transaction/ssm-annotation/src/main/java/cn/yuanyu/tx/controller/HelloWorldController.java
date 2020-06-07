package cn.yuanyu.tx.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanyu
 */
@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "hello,world.";
    }
}
