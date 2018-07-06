package org.windwant.jetty.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 18-3-22.
 */
@Validated //此类需要方法级别验证
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello(@NotNull(message = "{name.notnull}") String name){
        return "nice to meet you, " + name + " !";
    }
}
