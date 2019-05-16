package cn.hgxsp.mylife.controller;

import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import cn.hgxsp.mylife.service.TeamService;
import cn.hgxsp.mylife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * DESC：teamController层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/3
 * Time : 22:08
 */
@RestController
@RequestMapping("/team")
public class TeamController {


    @Autowired
    TeamService teamService ;

    @Autowired
    UserService userService ;
    /**
    *DESC:创建一个Team controller层
    *@author hou.linan
    *@date:  2018/10/3 22:10
    *@param:  [team]
    *@return:  cn.hgxsp.mylife.Utils.LLJSONResult
    */
    @PostMapping("/create")
    public LLJSONResult createTeam(@RequestBody Team team , String userId){

        if(ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("您创建的小组为空") ;
        if(StringUtils.isEmpty(team.getTeamName())) return LLJSONResult.errorMsg("您出入的小组名称为空");
        if(StringUtils.isEmpty(team.getTeamPassword())) return  LLJSONResult.errorMsg("您输入的小组密码为空");
        Team addResult = teamService.add(team);
        if(ObjectUtils.isEmpty(addResult)) return LLJSONResult.errorMsg("创建小组失败");

        //绑定用户和team的关系
        User userById = userService.findUserById(userId);
        if(ObjectUtils.isEmpty(userById)) return LLJSONResult.errorMsg("没有找到你的用户信息,创建失败") ;
        if(!ObjectUtils.isEmpty(userById.getFromTeam())) return LLJSONResult.errorMsg("您已经拥有一个小组，如果需要请重新注册其他账号") ;
        userById.setFromTeam(team);
        //处理权限问题
        if(userById.getRole() == 2 && !"admin".equals(userById.getUserName()))userById.setRole(1);
        userService.update(userById);


        return LLJSONResult.ok(addResult) ;
    }


    @GetMapping("/isExistTeam")
    public LLJSONResult isExistTeam(String teamName){

        return teamService.existTeam(teamName) ?
                LLJSONResult.errorMsg("该小组名称已经存在") :LLJSONResult.ok("Team名称可用");
    }


    @GetMapping("/getTeam")
    public LLJSONResult findTeamById(String id){
        if(StringUtils.isEmpty(id)) return LLJSONResult.errorMsg("传入的小组名称为空");
        Team result = teamService.findTeamById(id);

        if(ObjectUtils.isEmpty(result)) return LLJSONResult.errorMsg("没有找到您的小组") ;

        return LLJSONResult.ok(result) ;
    }



}
