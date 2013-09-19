package com.alchemista;

public class Hero
{

	String	nick,
			mail;
	int		cash,
			lvl,
			exp;
	
	Save	sav;
	
	Hero(Save s)
	{
		sav=s;
		load();	
	}
	
	void load()
	{
		nick = sav.getData("nick");
		cash = Integer.parseInt(sav.getData("money"));
		lvl  = Integer.parseInt(sav.getData("lvl"));
		exp	 = Integer.parseInt(sav.getData("exp"));
		mail = sav.getData("mail");
	}
	
}
