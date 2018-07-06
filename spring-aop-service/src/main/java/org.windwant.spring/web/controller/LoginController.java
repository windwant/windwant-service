package org.windwant.spring.web.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.windwant.spring.web.entity.User;
import org.windwant.spring.web.service.BookService;
import org.windwant.spring.web.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by windwant on 2016/3/4.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @RequestMapping("docheck.do")
    @ResponseBody
    public User doLogin(@RequestParam @Valid String userName, @RequestParam @Valid String password){
        LogManager.getLogger().info(userName + " : " + password);
        return userService.getUserById(1);
    }

    @RequestMapping("doFilter.do")
    @ResponseBody
    public Map<String, Object> doFilter(){
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("user", bookService.getBookById(1));
        return map;
    }

    @RequestMapping("doReturn.do")
    public String doReturn(){
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("user", "this is the user");
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(map);
        return "forward:index.do";
    }

    @RequestMapping("doLogin.do")
    public String doLogin(){
        return "login";
    }

    @RequestMapping("index.do")
    public String index(){
        return "redirect:/";
    }
}
