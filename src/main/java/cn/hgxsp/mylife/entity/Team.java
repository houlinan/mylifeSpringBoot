package cn.hgxsp.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：小组
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/30
 * Time : 23:25
 */
@Entity
@Data
public class Team {

    @Id
    private String id ;

    @Column(name = "teamname")
    private String teamName ;

    @Column(name = "teampassword")
    private String teamPassword ;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date createTime;


}
