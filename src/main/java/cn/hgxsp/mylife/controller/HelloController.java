package cn.hgxsp.mylife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * DESC：测试controller类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/5/15
 * Time : 22:51
 */
@Controller
@ResponseBody
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello";
    }

}
