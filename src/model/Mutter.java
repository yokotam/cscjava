package model;

import java.io.Serializable;

public class Mutter implements Serializable{
	private String userName;	//ユーザー名
	private String text;		//つぶやき内容
	public Mutter(){}
	public Mutter(String userName, String text){
		this.userName = userName;
		this.text = text;
	}
	/**
	 * userNameを取得します。
	 * @return userName
	 */
	public String getUserName() {
	    return userName;
	}
	/**
	 * textを取得します。
	 * @return text
	 */
	public String getText() {
	    return text;
	}
}
