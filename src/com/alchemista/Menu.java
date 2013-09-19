package com.alchemista;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

public class Menu extends Scene
{
	MainActivity act;
	//dupa
	int w,
		h;
	
	TextureRegion t_speaker,
				  t_background;
	
	TextureRegion[] t_tab;
	
	Sprite s_speaker,
		   s_background;
	
	Sprite[] s_tab;
	
	
	Menu()
	{
		act = MainActivity.getSharedInstance();
		w = act.w;
		h = act.h;
		
		t_tab = new TextureRegion[3];
		s_tab = new Sprite[3];
		
		t_speaker = new stb("Menu/speaker", 512, 512).T;
		t_background = new stb("Menu/background", 1024, 1024).T;
		t_tab[0] = new stb("Menu/tab0", 512, 1024).T;
		t_tab[1] = new stb("Menu/tab1", 512, 1024).T;
		t_tab[2] = new stb("Menu/tab2", 512, 1024).T;
		
		grafika();
	}
	
	int nasluch_petli_s_tab(Sprite t)
	{
		for(int i = 0; i < s_tab.length; i++ )
		{
			if(t == s_tab[i])
				return i;
		}
		return -1;
	}
	
	void grafika()
	{
		s_background = new Sprite(0, 0, t_background,act.getVertexBufferObjectManager());
		s_background.setWidth(w);
		s_background.setHeight(h);
		
		s_speaker = new Sprite(w, h, t_speaker,act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO W£¥CZANIE/WY£¥CZANIE dŸwiêku
					return true;
				}
				return false;
			}
		};
		s_speaker.setWidth(0.1f * h);
		s_speaker.setHeight(0.15f * h);
		s_speaker.setPosition(0.99f * w - s_speaker.getWidth(), 0.01f * w);	
		
		registerTouchArea(s_background);
		registerTouchArea(s_speaker);
		
		attachChild(s_background);
		attachChild(s_speaker);
		
		for(int i = 0; i < 3; i++)
		{
			s_tab[i] = new Sprite(w, h, t_tab[i],act.getVertexBufferObjectManager())
			{
				@Override
				public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
				{
					if(pEvent.isActionDown())
					{
						switch(nasluch_petli_s_tab(this))
						{
						case 0:
							//TODO przejscie w scene AMULETY
							System.out.println("Przechodze w AMULETY");
							break;
							
						case 1:
							//TODO przejscie w scene ALCHEMIA
							System.out.println("Przechodze w ALCHEMIA");
							break;
							
						case 2:
							act.setCurrentScene(new Encyclopedy());
							System.out.println("Przechodze w ENCYKLOPEDIA");
							break;
						}
						return true;
					}
					return false;
				}
			};
			s_tab[i].setWidth(0.24f * w);
			s_tab[i].setHeight(0.7f * h);
			
			if(i == 0)
				s_tab[i].setPosition(0.1f * w, (h - s_tab[i].getHeight()) /2);
			
			if(i > 0)
				s_tab[i].setPosition(s_tab[i -1].getX() + s_tab[i -1].getWidth() + 0.04f * w, s_tab[i -1].getY());
			
			registerTouchArea(s_tab[i]);
			attachChild(s_tab[i]);
		}
	}
}
