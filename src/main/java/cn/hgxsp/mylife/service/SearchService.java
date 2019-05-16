package cn.hgxsp.mylife.service;

import cn.hgxsp.mylife.DAO.AccountNumDao;
import cn.hgxsp.mylife.DAO.BillDao;
import cn.hgxsp.mylife.DAO.UserDao;
import cn.hgxsp.mylife.entity.Bill;
import cn.hgxsp.mylife.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * DESC：搜索相关service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/10
 * Time : 16:18
 */
@Service
public class SearchService {

    @Autowired
    BillDao billDao;

    @Autowired
    UserDao userDao ;

    @Autowired
    AccountNumDao accountNumDao ;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    /**
    *DESC: 获取本小组的所有用户名称
    *@author hou.linan
    *@date:  2018/10/10 16:20
    *@param:  [userId]
    *@return:  java.util.List<java.lang.String>
    */
    public List<String>  getCurrAllTeamUserNameforUser(Team team){

        return  userDao.findAllUserByFromTeamId(team.getId());
    }

    /**
    *DESC: 获取本小组内所有的账号
    *@author hou.linan
    *@date:  2018/10/11 14:50
    *@param:  [team]
    *@return:  java.util.List<java.lang.String>
    */
    public List<String> getCurrAllTeamUserNamsForAccountNum(Team team){
        return accountNumDao.findAllUserNamebyFromTeam(team.getId());
    }

    /**
    *DESC: 通过条件查询符合用户的数据
    *@author hou.linan
    *@date:  2018/10/11 14:52
    *@param:  [accountNums, userS, startTime, endTime]
    *@return:  java.util.List<cn.hgxsp.mylife.entity.Bill>
    */
    public List<Bill> searchInCurrTeamBill(String teamId ,String accountNums ,String users , String startTime , String endTime){

        StringBuilder sb = new StringBuilder("select * from bill where form_team_id = '" + teamId + "'");

        if(!StringUtils.isEmpty(accountNums)) sb.append(" and accountname in ( ").append(accountNums)
                .append(" )") ;
        if(!StringUtils.isEmpty(users)) sb.append(" and operusername in ( ").append(users).append(" )") ;
        if(!"点击设置".equals(startTime) && startTime.contains("-") && startTime.contains("20")){
            sb.append("and UNIX_TIMESTAMP(create_time) >= UNIX_TIMESTAMP('" +startTime+ "')");
        }
        if(!"点击设置".equals(endTime) && endTime.contains("-") && endTime.contains("20")){
            String oldTime = endTime ;
            endTime = DateAdd1Day(endTime) ;
            if(StringUtils.isEmpty(endTime)) endTime = oldTime ;
            sb.append(" and UNIX_TIMESTAMP(create_time) <= UNIX_TIMESTAMP('" +endTime+ "')");
        }
        sb.append(" order by create_time desc") ;
        Query nativeQuery = entityManager.createNativeQuery(sb.toString());
        List<Bill> resultList = nativeQuery.getResultList();



        return resultList ;
    }

    /**
    *DESC: 将当前日期加一天
    *@author hou.linan
    *@date:  2018/10/11 16:31
    *@param:  [date]
    *@return:  java.lang.String
    */
    private String DateAdd1Day(String date){

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = new Date(f.parse(date).getTime() + 24 * 3600 * 1000);
         return f.format(d);

        }catch (Exception e) {
        }
        return null ;
    }




}
