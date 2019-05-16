package cn.hgxsp.mylife.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * DESC：微博调用接口controller层，返回接口信息用
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/26
 * Time : 16:42
 */
@RestController
@RequestMapping("/weibo")
@Slf4j
public class WeiboController {


    @RequestMapping("/toRedirectAddress")
    public String returnMessage(HttpServletRequest req){
        Map<String, String[]> result = req.getParameterMap();
        Set<String> allKey = result.keySet();
        StringBuilder stringBuilder = new StringBuilder() ;
        for(String key :allKey){
            stringBuilder.append("参数为：").append(key).append("    参数的值为：")
                    .append(req.getParameter(key)+"-----------"+System.getProperty("line.separator")) ;
        }
        log.info( stringBuilder.toString());
        return stringBuilder.toString();
    }



}
