package cn.hgxsp.mylife.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DESC：账单收入实体类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/30
 * Time : 23:48
 */
@Entity
@Data
public class Bill {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id ;

    private BigDecimal number ;

    @Column(name = "operusername")
    private String operUserName ;

    @Column(name = "accountname")
    private String accountName ;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private AccountNum  fromAccountNum ;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Team formTeam ;


    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date createTime;

}
