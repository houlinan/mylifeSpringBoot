package cn.hgxsp.mylife.Utils;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * There is no royal road to learning.
 * Description:提交实体对象中的null赋值
 * Created by 贤领·周 on 2018年04月10日 15:26
 */


public class UpdataObjectUtil {


    /**
     * 将目标源中不为空的字段过滤，将数据库中查出的数据源复制到提交的目标源中
     *
     * @param source 用id从数据库中查出来的数据源
     * @param target 提交的实体，目标源
     * @return Object 更新之后的对象，直接保存即可
     */
    public static Object copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNoNullProperties(target));

        return target ;
    }

    /**
     * @param target 目标源数据
     * @return 将目标源中不为空的字段取出
     */
    private static String[] getNoNullProperties(Object target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) noEmptyName.add(p.getName());
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }


    /**
    *DESC:示例！！！！！！！
    *@author hou.linan
    *@date:  2018/8/6 12:58
    *@param:
    *@return:
    */
    /*
    public String save(@RequestBody User u) {
        if(u.getId != 0){
            User source= userDao.findOne(u.getId);
            UpdateTool.copyNullProperties(source, u);
        }
        userDao.save(u)
        return "更新成功";
    }
    */
}

