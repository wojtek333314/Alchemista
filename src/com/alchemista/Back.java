package com.alchemista;

import com.alchemista.MainActivity;

public class Back 
{
	MainActivity act;
	int current_id;
	
	Back()
	{
		act = MainActivity.getSharedInstance();
		current_id = act.ID;
	}
	
	void BackF(int id)
	{
		switch(id)
		{
		case 1:
			act.finish();
			break;
		case 2:
			act.setCurrentScene(new Menu());
			break;
		}
	}
}
