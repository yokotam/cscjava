package model;

import java.io.Serializable;

public class User implements Serializable {
	private String name;	//ユーザー名
	private String pass;	//パスワード
	public User(String name, String pass){
		this.name = name;
		this.pass = pass;
	}
	/**
	 * nameを取得します。
	 * @return name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * passを取得します。
	 * @return pass
	 */
	public String getPass() {
	    return pass;
	}
}
