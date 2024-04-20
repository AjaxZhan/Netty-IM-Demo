package cn.cagurzhan.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户会话实体
 * @author AjaxZhan
 */
@Data
@NoArgsConstructor
public class Session {
    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString(){
        return userId + ":" + username;
    }
}
