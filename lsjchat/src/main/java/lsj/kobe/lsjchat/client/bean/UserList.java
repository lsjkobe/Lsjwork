package lsj.kobe.lsjchat.client.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 */
public class UserList implements Serializable {
    private List<UserBean> userList;

    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }
}
