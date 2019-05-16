package cn.hgxsp.mylife.service;

import cn.hgxsp.mylife.DAO.BillDao;
import cn.hgxsp.mylife.DAO.UserDao;
import cn.hgxsp.mylife.SendEmailUtils;
import cn.hgxsp.mylife.entity.Bill;
import cn.hgxsp.mylife.entity.Team;
import cn.hgxsp.mylife.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * DESC：定时任务service类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/10/15
 * Time : 9:37
 */
@Component
@Slf4j
public class ScheduledService {

    @Autowired
    UserDao userDao;

    @Autowired
    BillDao billDao;

    @Autowired
    SendEmailUtils sendEmailUtils;

    @Scheduled(cron = "0 00 10 * * *")
    public void scheduled() {
        List<User> allUser = userDao.findAllUserByRole();
        allUser.forEach(e -> sendEmailByBillForUser(e));
    }

    private void sendEmailByBillForUser(User user) {
        Team team = user.getFromTeam();
        List<Bill> allByTeam = billDao.findAllByTeam(team.getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(new Date());
        StringBuilder sb = new StringBuilder("您全部的历史账单如下：\n");
        for (Bill currbill : allByTeam) {

            sb.append("用户：【").append(currbill.getOperUserName()).
                    append("】添加了收入：【").append(currbill.getNumber()).append("】，创建时间为：【")
                    .append(currbill.getCreateTime()).append("】-----来源账号：【" +
                    currbill.getFromAccountNum().getNumber()+"】\n");

        }
        if (sendEmailUtils.checkEmailAddress(user.getEmailAddress())) {
            sb.append( "\n注：如果需要查询范围内的总和，请在微信小程序中查询，此邮件仅备份数据，以防小程序数据库数据丢失给您造成不必要的麻烦");
            sendEmailUtils.testSendSimple(user.getEmailAddress(), sb.toString());
            log.info("发送账单到邮箱成功，时间为【" +
                    format + "】，用户为：【" +
                    user.getUserName() + "】,");
        }
    }
}
