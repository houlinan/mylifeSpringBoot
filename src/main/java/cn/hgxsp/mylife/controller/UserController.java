package cn.hgxsp.mylife.controller;

import cn.hgxsp.mylife.ObjectVO.UserVO;
import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.Utils.MD5Utils;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import cn.hgxsp.mylife.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * DESC：用户controller层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/1
 * Time : 17:16
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/regist")
    public LLJSONResult regist(String userName, String password) {
        if (StringUtils.isEmpty(userName)) return LLJSONResult.errorMsg("传入的账号为空");
        if (StringUtils.isEmpty(password)) return LLJSONResult.errorMsg("传入的密码为空");

        if(userService.existUser(userName)) return LLJSONResult.errorMsg("该用户名称已经存在");

        User user = new User();
        user.setUserName(userName);
        if("admin".equals(userName)) user.setRole(0);
        user.setPassword(password);


        User result = userService.add(user);
        if(ObjectUtils.isEmpty(result)) return LLJSONResult.errorMsg("创建用户失败") ;
        log.info("用户【{}】注册成功，并跳转至信息完善页面" , userName);
        return LLJSONResult.ok(result);
    }

    @GetMapping("/login")
    public LLJSONResult login(String userName , String password ){
        if (StringUtils.isEmpty(userName)) return LLJSONResult.errorMsg("传入的账号为空");
        if (StringUtils.isEmpty(password)) return LLJSONResult.errorMsg("传入的密码为空");
        User user = userService.findUserByName(userName);


        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("账号或者密码错误");
        String userPassword =  user.getPassword() ;
        if(!MD5Utils.getMD5Str(password).equals(userPassword)) return LLJSONResult.errorMsg("账号或者密码错误");

        UserVO userVO =  new UserVO();
        BeanUtils.copyProperties(user ,userVO);
        //更新用户的最后上线时间
        user.setLastLoginTime(new Date());
        userService.update(user) ;
        log.info("用户【{}】登录成功！", userName);

        return LLJSONResult.ok(userVO);
    }
    @PostMapping("/update")
    public LLJSONResult updateUser(@RequestBody User user){
        System.out.println("1111111111111111111111");

        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("您输入的用户信息为空");

        String userId = user.getId() ;
        if(StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("您输入的用户ID为空") ;
        User findById = userService.findUserById(userId);
        if(ObjectUtils.isEmpty(findById)) return LLJSONResult.errorMsg("请登录后设置用户信息");

        //更新用户的信息
        findById.setMoblie(user.getMoblie());
        findById.setAnswer1(user.getAnswer1());
        findById.setAnswer2(user.getAnswer2());
        log.info("用户输入的邮箱地址为：{}",user.getEmailAddress());
        findById.setEmailAddress(user.getEmailAddress());

        User result = userService.update(findById);
        log.info("用户【{}】修改个人信息成功。修改的手机号为：{}，修改的密码问题1为：{}，修改的问题2为：{}"
        ,user.getUserName(),user.getMoblie() ,user.getAnswer1() , user.getAnswer2());
        UserVO userVO =  new UserVO();
        BeanUtils.copyProperties(result ,userVO);
        return LLJSONResult.ok(userVO) ;
    }

    /**
    *DESC:判断用户是否拥有团队
    *@author hou.linan
    *@date:  2018/10/5 16:58
    *@param:  [userId]
    *@return:  cn.hgxsp.mylife.Utils.LLJSONResult
    */
    @GetMapping("/userHasTeam")
    public LLJSONResult userHasTeam(String userId){
        return userService.findUserTeamId(userId) ;
    }

    /**
    *DESC:将一个用户添加进登录用户的小组
    *@author hou.linan
    *@date:  2018/10/8 18:45
    *@param:  [accountNum, userPassword, loginUserId, teamPassword]
    *@return:  cn.hgxsp.mylife.Utils.LLJSONResult
    */
    @PostMapping("/addUserToTeam")
    public LLJSONResult addUserToTeam(String accountNum , String userPassword,
                                      String loginUserId ,String teamPassword){
        if(StringUtils.isEmpty(accountNum)) return LLJSONResult.errorMsg("您传入的需要添加的用户账号为空") ;
        if(StringUtils.isEmpty(userPassword)) return LLJSONResult.errorMsg("您传入的需要添加的用户密码为空") ;
        if(StringUtils.isEmpty(loginUserId)) return LLJSONResult.errorMsg("系统判断您没有登录，请登录后重试") ;
        if(StringUtils.isEmpty(teamPassword)) return LLJSONResult.errorMsg("您传入的Team密码为空") ;

        //判断登录者相关信息
        User loginUser = userService.findUserById(loginUserId);
        if(ObjectUtils.isEmpty(loginUser)) return LLJSONResult.errorMsg("系统并没有找到您的用户信息,请注册后操作");
        Team loginUserTeam = loginUser.getFromTeam();
        if(ObjectUtils.isEmpty(loginUserTeam)) return LLJSONResult.errorMsg("系统判断到您没有绑定任何Team。请先创建Team后操作") ;
        String loginUserTeamPassword = loginUserTeam.getTeamPassword() ;
        if(!loginUserTeamPassword.equals(teamPassword))LLJSONResult.errorMsg("对不起，您输入的团队密码错误");

        //判断添加的用户信息
        User needAddUser = userService.findUserByName(accountNum);
        if(ObjectUtils.isEmpty(needAddUser)) return  LLJSONResult.errorMsg("对方账号或者密码错误") ;
        if(!needAddUser.getPassword().equals(MD5Utils.getMD5Str(userPassword))) return  LLJSONResult.errorMsg("对方账号或者密码错误") ;
        needAddUser.setFromTeam(loginUserTeam);
        userService.update(needAddUser);

        return LLJSONResult.ok("设置用户【"+accountNum+"】进入Team成功！") ;
    }

    @GetMapping("/test")
    public String tets(){
        List<String> list = Arrays.asList("美国" ,"中国" , "英国" , "法国") ;
        Map<String ,Object> result = new HashMap<>( );

        List<Map<String ,String>> aaa = new ArrayList<>( );
        for(String l : list ){
            Map<String ,String> t = new HashMap<>( );
            t.put("name" ,l) ;
            t.put("value" , l);
            aaa.add(t) ;
        }

        return aaa.toString() ;
    }
}
