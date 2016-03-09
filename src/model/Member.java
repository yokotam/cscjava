package model;

import java.util.Date;

public class Member {
	private String name;
	private Sex sex;
	private int age;
	private int weight;
	private int id;
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	int height;
	Date birth;
}


enum Sex{FEMAIL,EMAIL,}