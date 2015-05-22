package ro.pub.cs.systems.pdsd.practicaltest02var05;

import java.security.Timestamp;

public class MyClass {
	String mystring;
	DateTime time;
	
	public MyClass() {};
	
	public MyClass(String str, DateTime time){
		this.mystring = str;
		this.time = time;
	}
	
	public void setStr (String str){
		this.mystring = str;
	}
	
	public String getStr(){
		return this.mystring;
	}
	
	public void setTime (DateTime time){
		this.time = time;
	}
	
	public DateTime getTime(){
		return this.time;
	}
}
