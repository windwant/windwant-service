package org.windwant.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;

/**
 * org.apache.ftpserver 构造ftp服务器
 *
 * Created by Administrator on 18-10-25.
 */
public class FTPServer {

    public static void main(String[] args) throws FtpException {
        FtpServerFactory ftpServerFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();//连接监听工厂
        factory.setPort(9221);//监听端口
        ftpServerFactory.addListener("default", factory.createListener());

        //访问账户
        BaseUser user = new BaseUser();
        user.setName("bykj");
        user.setPassword("1256");
        user.setHomeDirectory("d:\\data");

        //设置账户权限 写权限
        user.setAuthorities(new ArrayList() {{
            add(new WritePermission());
        }});

        //保存账户
        ftpServerFactory.getUserManager().save(user);

        //服务创建
        FtpServer server = ftpServerFactory.createServer();
        server.start();
    }
}
