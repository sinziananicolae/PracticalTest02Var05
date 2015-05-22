package ro.pub.cs.systems.pdsd.practicaltest02var05;

import java.security.Timestamp;

public class MyClass {
	String mystring;
	Timestamp time;
	
	public MyClass() {};
	
	public MyClass(String str, Timestamp time){
		this.mystring = str;
		this.time = time;
	}
	
	public void setTemp (String str){
		this.mystring = str;
	}
	
	public String getStr(){
		return this.mystring;
	}
	
	public void setTime (Timestamp time){
		this.time = time;
	}
	
	public Timestamp getHum(){
		return this.time;
	}
}
