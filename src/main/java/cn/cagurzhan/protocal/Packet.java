package cn.cagurzhan.protocal;

import cn.cagurzhan.session.Session;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 数据包抽象类
 * @author AjaxZhan
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(serialize = false,deserialize = false)
    private Byte version = 1;

    /**
     * 命令
     */
    @JSONField(serialize = false)
    public abstract  Byte getCommand();
}
