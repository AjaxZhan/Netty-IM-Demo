package cn.cagurzhan.utils;

import org.junit.Test;

/**
 * @author AjaxZhan
 */
public class TestIDUtils {

    @Test
    public void testGetRandomUserId(){
        String randomUserId = IDUtils.getRandomUserId();
        System.out.println("randomUserId=" + randomUserId);
    }
}
