package cn.hgxsp.mylife.controller;

import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.entity.Bill;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import cn.hgxsp.mylife.service.SearchService;
import cn.hgxsp.mylife.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC：搜索相关controller
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/10
 * Time : 16:37
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;


    @GetMapping("/findAllUserNamesByTeam")
    public LLJSONResult findAllUserNamesByTeam(String userId) {

        if (StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("您传入的用户Id为空");

        User user = userService.findUserById(userId);

        if (ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("对不起没有找到您的用户信息");

        Team team = user.getFromTeam();
        if (ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("对不起，您没有绑定任何Team，请绑定后重试");


        Map<String , Object > resultMap = new HashMap<>();
        List<String> currAllTeamUserName = searchService.getCurrAllTeamUserNameforUser(team);
        resultMap.put("forUser" , ListNames2MapList(currAllTeamUserName)) ;
        List<String> currAllTeamUserNameForAccountNum = searchService.getCurrAllTeamUserNamsForAccountNum(team);
        resultMap.put("forAccountNum" , ListNames2MapList(currAllTeamUserNameForAccountNum)) ;

        return LLJSONResult.ok(resultMap) ;
    }

    @PostMapping("/searchInCurrTeamBill")
    public LLJSONResult searchInCurrTeamBill(String userId ,
                                             @RequestParam(required = false) String accountNums ,
                                             @RequestParam(required = false)  String userNames ,
                                             @RequestParam(required = false) String startTime ,
                                             @RequestParam(required = false) String endTime,
                                             String teamPwd){

        if(StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("传入的用户ID为空") ;
        User user = userService.findUserById(userId);
        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("对比起系统,没有找到您的用户信息,请注册后重试") ;
        Team team = user.getFromTeam() ;
        if(ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("您没有绑定任何Team，请绑定后重试") ;
        if(!team.getTeamPassword().equals(teamPwd)) return LLJSONResult.errorMsg("您输入的Team密码错误，请重试");
        if(user.getRole() == 2 ) return LLJSONResult.errorMsg("对不起，此功能只有团队管理员才能查询，您不具有查询权限") ;
        List<Bill> bills = searchService.searchInCurrTeamBill(team.getId(), accountNums, userNames, startTime, endTime);

        return LLJSONResult.ok(bills);
    }

    /**
    *DESC: 以下为本类工具类
    *@author hou.linan
    *@date:  2018/10/11 11:29
    */

    private List< Map<String , Object >> ListNames2MapList( List<String> currList){
        Map<String , Object > resultMap  ;
        List< Map<String , Object >> resultList = new ArrayList<>( );
        for(String currUserName : currList){
            resultMap = new HashMap<>();
            resultMap.put("name" , currUserName) ;
            resultMap.put("value" , "'"+currUserName+ "'") ;
            resultMap.put("checked" , true) ;
            resultList.add(resultMap) ;
        }




        return resultList ;
    }


}
