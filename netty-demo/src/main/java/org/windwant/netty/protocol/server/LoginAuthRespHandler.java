package org.windwant.netty.protocol.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.windwant.netty.protocol.MessageType;
import org.windwant.netty.protocol.struct.Header;
import org.windwant.netty.protocol.struct.NettyMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoginAuthRespHandler
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whiteList = {"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage resp = null;
            if(nodeCheck.containsKey(nodeIndex)){
                resp = buildResp((byte) -1);
            }else{
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean allow = false;
                for(String wip:whiteList){
                    if(wip.equals(ip)){
                        allow = true;
                        break;
                    }
                }
                if(allow){
                    resp = buildResp((byte) 0);
                    nodeCheck.put(nodeIndex, true);
                }else{
                    resp = buildResp((byte) -1);
                }
            }
            System.out.println("login response: " + resp + " body: " + resp.getBody());
            ctx.writeAndFlush(resp);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResp(byte result){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
