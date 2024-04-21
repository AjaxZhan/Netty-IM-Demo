package cn.cagurzhan.utils;

import cn.cagurzhan.attribute.Attributes;
import cn.cagurzhan.session.Session;
import com.sun.org.apache.bcel.internal.classfile.Attribute;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话工具类，检测用户是否已登录
 * @author AjaxZhan
 */
public class SessionUtils {

    /**
     * 存放userId - channel
     * 表示已经登录的连接
     */
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 存放groupId - channel集合
     * 表示已经建立的群组
     */
    private static final Map<String,ChannelGroup> groupIdChannelGroup = new ConcurrentHashMap<>();

    /**
     * 保存已登录的客户端连接和Session
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 如果已登录，将连接的channel去除
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录!");
        }
    }


    /**
     * 判断是否登录
     */
    public static boolean hasLogin(Channel channel){
        return getSession(channel)!= null;
    }

    /**
     * 从Channel的属性中拿到session
     */
    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据UserId拿到Channel
     */
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup){
        groupIdChannelGroup.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelGroup.get(groupId);
    }
}
