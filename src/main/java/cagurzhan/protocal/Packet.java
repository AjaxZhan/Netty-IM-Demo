package cn.cagurzhan.protocal;

import lombok.Data;

/**
 * @author AjaxZhan
 */
@Data
public abstract class Packet {
    private Byte version = 1;
    public abstract  Byte getCommand();
}
