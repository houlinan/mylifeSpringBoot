package cn.hgxsp.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：用户表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/30
 * Time : 21:57
 */

@Entity
@Data
public class User {

    @Id
    private String id ;

    @Column(name = "username")
    private String userName ;

    private String password ;

    private String problem1 = "你的证件的后六位是？";

    private String problem2 = "你最喜欢的电影是？";

    private String answer1 ;

    private String answer2 ;

    private String moblie ;

    @Column(name = "emailaddress")
    private String emailAddress;

    //.0为超级管路  1 为小组管理员  2 为普通用户
    private int role = 2 ;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Team fromTeam ;


    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date createTime;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**最后登录时间*/
    private Date lastLoginTime;



}
