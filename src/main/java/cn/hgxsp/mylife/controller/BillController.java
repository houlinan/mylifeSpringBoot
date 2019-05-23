package cn.hgxsp.mylife.controller;

import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.entity.AccountNum;
import cn.hgxsp.mylife.entity.Bill;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import cn.hgxsp.mylife.service.AccountNumService;
import cn.hgxsp.mylife.service.BillService;
import cn.hgxsp.mylife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigDecimal;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/9
 * Time : 16:33
 */
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService  billService ;


    @Autowired
    UserService  userService ;

    @Autowired
    AccountNumService accountNumService ;

    @PostMapping("/add")
    public LLJSONResult add(String userID  ,Long accountNum , Long addNumber){


        if(StringUtils.isEmpty(userID)) return LLJSONResult.errorMsg("没有获取到你的用户信息，请登录后重试") ;
//        if(StringUtils.isEmpty(accountNum)) return LLJSONResult.errorMsg("系统获取当前账号信息错误。请联系管理员") ;

        //查看用户
        User user = userService.findUserById(userID);
        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("系统没有找到您的账号信息");
        Team team = user.getFromTeam() ;
        if(ObjectUtils.isEmpty(team)) return LLJSONResult.errorMsg("系统没有找到您的Team信息");

        //查询账号
        AccountNum accountNumber = accountNumService.findAccountById(accountNum);
        if(ObjectUtils.isEmpty(accountNumber)) return LLJSONResult.errorMsg("系统没有找到您的当前账号信息");
        if(addNumber <0 &&  user.getRole() == 2   ) return LLJSONResult.errorMsg("您没有权限输入小于0的数字") ;

        Bill bill = new Bill() ;
        bill.setNumber(new BigDecimal(addNumber));
        bill.setAccountName(accountNumber.getNumber());
        bill.setFormTeam(team);
        bill.setFromAccountNum(accountNumber);
        bill.setOperUserName(user.getUserName());

        billService.save(bill);

        return LLJSONResult.ok() ;
    }

    @GetMapping("/findAllByAccountNum")
    public LLJSONResult findAllByAccountNum(Long accountNumId , Integer pageIndex  , @RequestParam(defaultValue = "10") Integer pageSize){


        if(pageIndex < 1 )  pageIndex = 1 ;
        if(pageSize < 0 ) pageSize = 10 ;

        AccountNum accountNum = accountNumService.findAccountById(accountNumId);
        if(ObjectUtils.isEmpty(accountNum))return LLJSONResult.errorMsg("系统获取您的账号信息为空，请联系管理员") ;


        Page<Bill> result = billService.findByAccountNum(accountNum, pageIndex, pageSize);


        return LLJSONResult.ok(result) ;

    }


    public static void main(String[] args) {
        String str = "/storage/WCMData2//webpic\\W0201905\\W020190522\\W020190522611637779618.png";
        System.out.println(str.substring(str.lastIndexOf(File.separator)+1 , str.length()));
    }



}
