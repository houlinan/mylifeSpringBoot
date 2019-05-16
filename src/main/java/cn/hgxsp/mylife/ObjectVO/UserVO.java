package cn.hgxsp.mylife.ObjectVO;

import cn.hgxsp.mylife.entity.Team;
import lombok.Data;

import java.util.Date;

/**
 * DESC： 用户视图对象实体类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/3
 * Time : 15:00
 */
@Data
public class UserVO {

    private String id ;

    private String userName ;

    private String moblie ;

    //.0为超级管路  1 为小组管理员  2 为普通用户
    private int role = 2 ;

    private Team fromTeam ;

    /**创建时间*/
    private Date createTime;

    /**最后登录时间*/
    private Date lastLoginTime;
}
