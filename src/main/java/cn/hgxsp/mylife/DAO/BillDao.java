package cn.hgxsp.mylife.DAO;


import cn.hgxsp.mylife.entity.AccountNum;
import cn.hgxsp.mylife.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DESC：账单收入DAO层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/1
 * Time : 16:36
 */
@Repository
public interface BillDao extends JpaRepository<Bill, Long> {


    Page<Bill> findAllByFromAccountNum(AccountNum accountNum, Pageable pageable) ;


    @Query(value = "select DISTINCT  t.operusername from Bill t where t.form_team_id = ?" ,nativeQuery = true)
    List<String> findAllOperUserNameByFormTeam(String teamId) ;

    @Query(value = "select t.* from bill t where t.form_team_id = ? order by t.create_time desc" , nativeQuery = true)
    List<Bill> findAllByTeam(String teamId) ;

}
