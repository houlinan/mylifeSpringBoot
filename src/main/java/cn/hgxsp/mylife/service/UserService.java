package cn.hgxsp.mylife.service;

import cn.hgxsp.mylife.DAO.UserDao;
import cn.hgxsp.mylife.Utils.LLJSONResult;
import cn.hgxsp.mylife.Utils.MD5Utils;
import cn.hgxsp.mylife.Utils.UpdataObjectUtil;
import cn.hgxsp.mylife.Utils.idworker.Sid;
import cn.hgxsp.mylife.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

/**
 * DESC：用户服务层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/1
 * Time : 16:32
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    Sid sid;

    /**
     * DESC: 添加一个用户
     *
     * @author hou.linan
     * @date: 2018/10/1 16:40
     * @param: [user]
     * @return: cn.hgxsp.mylife.entity.User
     */
    public User add(User user) {
        user.setId(sid.nextShort());
        user.setCreateTime(new Date());
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        return userDao.save(user);
    }

    /**
    *DESC: 更新用户
    *@author hou.linan
    *@date:  2018/10/1 16:52
    *@param:  [user]
    *@return:  cn.hgxsp.mylife.entity.User
    */
    public User update(User user) {
        User userById = findUserById(user.getId());
        if (ObjectUtils.isEmpty(userById)) {
            return null;
        }
        UpdataObjectUtil.copyNullProperties(userById, user);
        return userDao.save(user);
    }


    /**
     * DESC: 通过用户ID查询用户
     *
     * @author hou.linan
     * @date: 2018/10/1 16:48
     * @param: [userId]
     * @return: cn.hgxsp.mylife.entity.User
     */
    public User findUserById(String userId) {
        Optional<User> byId = userDao.findById(userId);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    /**
    *DESC:删除一个用户
    *@author hou.linan
    *@date:  2018/10/1 16:52
    *@param:  [userId]
    *@return:  void
    */
    public void delete(String userId){
        userDao.deleteById(userId);
    }

    /**
    *DESC:通过用户名称查询用户
    *@author hou.linan
    *@date:  2018/10/3 14:54
    *@param:  [userName]
    *@return:  cn.hgxsp.mylife.entity.User
    */
    public User findUserByName(String userName){

        return userDao.findUserByUserName(userName) ;
    }


    /**
    *DESC: 判断用户名称是否存在
    *@author hou.linan
    *@date:  2018/10/3 14:56
    *@param:  [userName]
    *@return:  boolean
    */
    public boolean existUser(String userName){
        return findUserByName(userName) == null ? false : true ;
    }


    /**
    *DESC:查找用户的Teamid
    *@author hou.linan
    *@date:  2018/10/5 17:37
    *@param:  [userId]
    *@return:  cn.hgxsp.mylife.Utils.LLJSONResult
    */
    public LLJSONResult findUserTeamId(String userId){
        if(StringUtils.isEmpty(userId)) return LLJSONResult.errorMsg("检测您的用户信息为空，请重新登录") ;
        User user = findUserById(userId);
        if(ObjectUtils.isEmpty(user)) return LLJSONResult.errorMsg("没有找到您的用户信息") ;
        if(user.getFromTeam() == null ) return LLJSONResult.errorMsg("您没有创建任何团队，请在首页创建") ;
        return LLJSONResult.ok(user.getFromTeam()) ;
    }


}
