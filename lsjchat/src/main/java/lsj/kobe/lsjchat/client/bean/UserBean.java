package lsj.kobe.lsjchat.client.bean;

import java.io.Serializable;

/**
 * Created by lsj on 2016/5/25.
 */
public class UserBean implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private String img;
    private int uid;
    private String operation; //登录or注册
    private String account;
    private String password;
    private int msgCount;

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
