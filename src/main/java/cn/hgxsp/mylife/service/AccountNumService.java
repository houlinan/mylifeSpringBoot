package cn.hgxsp.mylife.service;

import cn.hgxsp.mylife.DAO.AccountNumDao;
import cn.hgxsp.mylife.entity.AccountNum;
import cn.hgxsp.mylife.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DESC：账号service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/5
 * Time : 18:05
 */
@Service
public class AccountNumService {

    @Autowired
    AccountNumDao accountNumDao;

    public boolean add(AccountNum accountNum){
        accountNum.setCreateTime(new Date());
        return accountNumDao.save(accountNum) != null ?true :false ;
    }

    public void delete(Long accountNumId){
        accountNumDao.deleteById(accountNumId);
    }

    public void update (String accountNum ,String password){
        AccountNum result = findByAccountNum(accountNum);
        if(ObjectUtils.isEmpty(result)) return  ;
        result.setPassword(password);
        accountNumDao.save(result) ;
    }

    public AccountNum findByAccountNum(String accountNum){
//        AccountNum accountNum1 = new AccountNum();
////        accountNum1.setNumber(accountNum);
////        Example<AccountNum> ex = Example.of(accountNum1);
////        Optional<AccountNum> one = accountNumDao.findOne(ex);
//        if(one.isPresent()) return one.get() ;
        AccountNum result = accountNumDao.findAccountNumByNumber(accountNum);
        if(!ObjectUtils.isEmpty(result)) return result ;
        return null ;
    }

    public List<AccountNum> findAccountByTeam(Team team){
        return accountNumDao.findAccountNumByFromTeam(team );
    }

    public AccountNum findAccountById(Long accountId){
        Optional<AccountNum> result = accountNumDao.findById(accountId);
        if(result.isPresent()) return result.get() ;
        return  null;
    }
}
