package cn.cagurzhan.utils;

import java.util.UUID;

/**
 * @author AjaxZhan
 */
public class IDUtils {
    public static String getRandomUserId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
