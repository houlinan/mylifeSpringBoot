package cn.hgxsp.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * DESC：角色 类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/30
 * Time : 23:38
 */
@Data
@Entity
public class Role {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id ;

    @Column(name = "rolename")
    private String roleName ;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private AccountNum  fromAccountNum ;

    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date createTime;


}
