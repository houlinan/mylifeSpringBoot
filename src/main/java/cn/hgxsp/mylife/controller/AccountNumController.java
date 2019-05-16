package cn.hgxsp.mylife.controller;

import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.entity.AccountNum;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import cn.hgxsp.mylife.service.AccountNumService;
import cn.hgxsp.mylife.service.TeamService;
import cn.hgxsp.mylife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * DESC：账号相关Controller；类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/6
 * Time : 14:20
 */
@RequestMapping("/accountNum")
@RestController
public class AccountNumController {

    @Autowired
    AccountNumService accountNumService  ;

    @Autowired
    UserService userService ;

    @Autowired
    TeamService teamService ;


    @PostMapping("/add")
    public LLJSONResult add(String accountNum , String password , String teamPwd , @RequestParam(required = false ) String teamId, String userId) {
        if (StringUtils.isEmpty(accountNum)) return LLJSONResult.errorMsg("传入的账号为空");
//        if (StringUtils.isEmpty(password)) return LLJSONResult.errorMsg("传入的账号密码为空");
        if (StringUtils.isEmpty(teamPwd)) return LLJSONResult.errorMsg("传入的团队密码为空");
//        if (StringUtils.isEmpty(teamId)) return LLJSONResult.errorMsg("传入的团队信息ID为空");
        if (StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("传入的创建的用户信息ID为空");
        AccountNum accountNum1 = new AccountNum();
        accountNum1.setPassword(password);
        accountNum1.setNumber(accountNum);
        //验证用户
        User user = userService.findUserById(userId);
        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("没有找到您的用户信息") ;
        Team team = user.getFromTeam() ;
        if(ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("请先绑定Team再来操作");
        String DBteamPwd = team.getTeamPassword() ;
        if(!DBteamPwd.equals(teamPwd)) return LLJSONResult.errorMsg("您输入的team密码错误，请重新输入") ;
        accountNum1.setFormUserName(user.getUserName());
        accountNum1.setFromTeam(team);

        if(!ObjectUtils.isEmpty(accountNumService.findByAccountNum(accountNum)))
            return LLJSONResult.errorMsg("该账号已经存在，创建失败，请走更新操作") ;

        accountNumService.add(accountNum1) ;

        return LLJSONResult.ok() ;
    }


    @GetMapping("/findAllByUserId")
    public LLJSONResult finByTeamAll(String userId){
        if(StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("传入的用户ID为空");
        User user = userService.findUserById(userId);
        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("没有找到您的用户信息") ;
        Team team =  user.getFromTeam() ;
        if(ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("您并没有绑定任何Team。请绑定后重试");

        List<AccountNum> resultList = accountNumService.findAccountByTeam(team);

        return LLJSONResult.ok(resultList ) ;
    }

    @PostMapping("/delete")
    public LLJSONResult delete(String accountNum ,String teamPwd, String loginUserId){
        if (StringUtils.isEmpty(accountNum)) return LLJSONResult.errorMsg("传入的账号为空");
        if (StringUtils.isEmpty(teamPwd)) return LLJSONResult.errorMsg("传入的团队密码为空");
        if (StringUtils.isEmpty(loginUserId)) return LLJSONResult.errorMsg("系统检测到你没有登录，请登录");

        User loginUser = userService.findUserById(loginUserId);
        if(ObjectUtils.isEmpty(loginUser)) return LLJSONResult.errorMsg("系统没有找到您的用户信息 , 请注册后重试") ;
        Team userTeam = loginUser.getFromTeam();
        if(ObjectUtils.isEmpty(userTeam)) return LLJSONResult.errorMsg("您没有绑定任何Team，请先绑定小组后重试") ;
        if(!teamPwd.equals(userTeam.getTeamPassword())) return LLJSONResult.errorMsg("您出入的Team密码错误，请重试");
        AccountNum accountNum1 = accountNumService.findByAccountNum(accountNum);
        System.out.println(accountNum1);
        if(ObjectUtils.isEmpty(accountNum1)) return LLJSONResult.errorMsg("您输入的账号错误，没有找到相应账号，请核对后重试") ;
        if(!accountNum1.getFromTeam().getId().equals(userTeam.getId())) return LLJSONResult.errorMsg("您绑定的小组和您操作的小组不是同一个小组，请联系系统管理员") ;


        //删除
        accountNumService.delete(accountNum1.getId());

        return LLJSONResult.ok("删除账号【" + accountNum+"】成功，即将跳转至Team首页") ;
    }

    @GetMapping("/getById")
    public LLJSONResult findAccountById(Long accountId) {

        AccountNum result = accountNumService.findAccountById(accountId);

        if(ObjectUtils.isEmpty(result))  return LLJSONResult.errorMsg("没有找到该账号的信息") ;

        return LLJSONResult.ok(result) ;
    }
}
