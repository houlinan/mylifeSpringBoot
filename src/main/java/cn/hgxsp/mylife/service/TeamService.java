package cn.hgxsp.mylife.service;

import cn.hgxsp.mylife.DAO.TeamDao;
import cn.hgxsp.mylife.Utils.idworker.Sid;
import cn.hgxsp.mylife.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * DESC：团队service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/3
 * Time : 21:53
 */
@Service
public class TeamService {

    @Autowired
    TeamDao teamDao ;

    @Autowired
    Sid sid ;

    /**
    *DESC:创建一个team
    *@author hou.linan
    *@date:  2018/10/3 22:05
    *@param:  [team]
    *@return:  cn.hgxsp.mylife.entity.Team
    */
    public Team add(Team team){
        team.setId(sid.nextShort());
        team.setCreateTime( new Date());
        Team result = teamDao.save(team);


        return result ;
    }

    /**
    *DESC:删除一个Team
    *@author hou.linan
    *@date:  2018/10/3 22:05
    *@param:  [teamId]
    *@return:  void
    */
    public void delete(String teamId){
        teamDao.deleteById(teamId);
    }

    /**
    *DESC: 判断team名字是否存在
    *@author hou.linan
    *@date:  2018/10/3 22:05
    *@param:  [teamName]
    *@return:  boolean
    */
    public boolean existTeam(String teamName){
        Team team = new Team();
        team.setTeamName(teamName);

        Example<Team > findone  = Example.of(team);

        Optional<Team> result = teamDao.findOne(findone);
        if(result.isPresent()) return true ;
        return false ;
    }


    /**
    *DESC:通过ID查询一个Team
    *@author hou.linan
    *@date:  2018/10/3 22:06
    *@param:  [teamId]
    *@return:  cn.hgxsp.mylife.entity.Team
    */
    public Team findTeamById(String teamId){

        Optional<Team> findResult = teamDao.findById(teamId);

        if(!findResult.isPresent())return null ;
        return findResult.get() ;
    }




}

