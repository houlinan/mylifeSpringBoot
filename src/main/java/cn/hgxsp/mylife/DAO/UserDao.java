package cn.hgxsp.mylife.DAO;

import cn.hgxsp.mylife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DESC：用户DAO层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/1
 * Time : 16:33
 */
@Repository
public interface UserDao  extends JpaRepository<User, String> {


    User findUserByUserName(String userName);

    @Query(value = "select DISTINCT t.username from user t where t.from_team_id = ?" , nativeQuery = true)
    List<String> findAllUserByFromTeamId(String teamId) ;

    @Query(value = "select t.* from user t where role = 1 " , nativeQuery = true)
    List<User> findAllUserByRole();

}
