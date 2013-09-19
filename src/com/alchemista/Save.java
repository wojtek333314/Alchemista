package com.alchemista;

import android.content.Context;
import android.content.SharedPreferences;

public class Save {

	MainActivity act;
	private SharedPreferences preferences;//klasa zapisu danych .. mniej wiecej
	private SharedPreferences.Editor preferencesEditor;
	public static final String prename = "save";
	
	Save()
	{
		act = MainActivity.getSharedInstance();
		
		preferences = act.getSharedPreferences(prename, Context.MODE_PRIVATE);
		preferencesEditor = preferences.edit();	
		
		if(getData("first").equals("null"))FirstPlay();
	}
	
	void putData(String data,String key){ //wklada dane pod podany klucz
		preferencesEditor.putString(key, data);
		 preferencesEditor.commit();//commit danych
	}
	
	String getData(String key)
	{
		 String ret = preferences.getString(key, "null");
		 return ret;
	}
	 
	int getInteger(String key)
	{
		String ret = preferences.getString(key, "null");
		return Integer.parseInt(ret);
	}
	
	float getFloat(String key)
	{
		String ret = preferences.getString(key, "null");
		return Float.parseFloat(ret);
	}
	
	
	boolean getBool(String key)
	{
		String ret = preferences.getString(key, "null");
		return Boolean.parseBoolean(ret);
	}
	
	
	/**
	 * @firstplay Jezeli !="null" wtedy gra uruchomiona co najmniej drugi raz 
	 * 
	 **/
	void FirstPlay()
	{
		putData("asad", "first");
		
		putData("1000", "money");
		putData("1", "lvl");
		putData("Nick","nick");
		putData("0","exp");
		putData("email","mail");
	}
	
	
}