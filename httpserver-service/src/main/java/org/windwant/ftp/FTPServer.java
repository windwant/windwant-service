package org.windwant.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;

/**
 * Created by Administrator on 18-10-25.
 */
public class FTPServer {

    public static void main(String[] args) throws FtpException {
        FtpServerFactory ftpServerFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(9221);
        ftpServerFactory.addListener("default", factory.createListener());

        BaseUser user = new BaseUser();
        user.setName("bykj");
        user.setPassword("1256");
        user.setHomeDirectory("d:\\data");

        user.setAuthorities(new ArrayList() {{
            add(new WritePermission());
        }});

        ftpServerFactory.getUserManager().save(user);

        FtpServer server = ftpServerFactory.createServer();
        server.start();
    }
}
