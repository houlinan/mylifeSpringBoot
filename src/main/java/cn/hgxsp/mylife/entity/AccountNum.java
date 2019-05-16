package cn.hgxsp.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：账号储存类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/30
 * Time : 23:35
 */
@Entity
@Data
public class AccountNum {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id ;

    private String number ;

    private String password ;

    @Column(name = "fromusername")
    private String formUserName ;


    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Team fromTeam ;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date createTime;

}
