package lsj.kobe.lsjchat.client.bean;

import java.io.Serializable;

public class MessageBean implements Serializable {
	private static final long serialVersionUID = 7526472295622776147L;
	int uid;
	int key; //发送或者接收标记
	String img;
	String type;
	String sender;
	String senderNick;
	String receiver;
	String content;
	String sendTime;
	String groupNick;

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderNick() {
		return senderNick;
	}
	public void setSenderNick(String senderNick) {
		this.senderNick = senderNick;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getGroupNick() {
		return groupNick;
	}
	public void setGroupNick(String groupNick) {
		this.groupNick = groupNick;
	}
	
}
