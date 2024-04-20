package cn.cagurzhan.attribute;

import cn.cagurzhan.session.Session;
import io.netty.util.AttributeKey;

/**
 * 连接的属性
 * @author AjaxZhan
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
