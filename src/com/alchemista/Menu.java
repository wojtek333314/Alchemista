package com.alchemista;

import org.andengine.audio.music.Music;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

public class Menu extends Scene
{
	MainActivity act;
	
	int w,
		h;
	
	boolean sounds_permission = true;
	
	TextureRegion t_background,
				  t_coin,
				  t_lvl;
	
	TextureRegion[] t_tab,
	 				t_speaker;
	
	Sprite s_speaker,
		   s_background,
		   s_coin,
		   s_lvl;
	
	Sprite[] s_tab;
	
	Text text_money,
		 text_lvl;
	
	Music example_music;
	
	Menu()
	{
		act = MainActivity.getSharedInstance();
		w = act.w;
		h = act.h;
		act.ID = 1;
		
		t_tab = new TextureRegion[3];
		t_speaker = new TextureRegion[2];
		s_tab = new Sprite[3];
		
		t_speaker[0] = new stb("Menu/speaker0", 512, 512).T;
		t_speaker[1] = new stb("Menu/speaker1", 512, 512).T;
		t_background = new stb("Menu/background", 1024, 1024).T;
		t_coin = new stb("Menu/coin", 256, 256).T;
		t_lvl = new stb("Menu/lvl", 256, 256).T;
		
		t_tab[0] = new stb("Menu/tab0", 512, 1024).T;
		t_tab[1] = new stb("Menu/tab1", 512, 1024).T;
		t_tab[2] = new stb("Menu/tab2", 512, 1024).T;
		
		example_music = act.SFX.background.music;
		
		sound();
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
		
		attachChild(s_background);
		
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
							act.setCurrentScene(new Alchemy());
							System.out.println("Przechodze w ALCHEMIA");
							break;
							
						case 2:
							act.setCurrentScene(new Encyklopedia());
							
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
		
		s_coin = new Sprite(w, h, t_coin, act.getVertexBufferObjectManager());
		s_coin.setWidth(0.1f * h);
		s_coin.setHeight(0.1f * h);
		s_coin.setPosition(0, 0);
		
		text_money = new Text(s_coin.getWidth() + 0.01f * w, 0, act.mFont, act.sav.getData("money"), act.getVertexBufferObjectManager());
		text_money.setColor(0, 0, 0);
		text_money.setHeight(0.1f * h);
		
		attachChild(s_coin);
		attachChild(text_money);
		
		s_lvl = new Sprite(w, h, t_lvl, act.getVertexBufferObjectManager());
		s_lvl.setWidth(0.1f * h);
		s_lvl.setHeight(0.1f * h);
		s_lvl.setPosition(text_money.getX() + text_money.getWidth() + 0.01f * w, 0);
		
		text_lvl = new Text(s_lvl.getX() + s_lvl.getWidth() + 0.01f * w, 0, act.mFont, act.sav.getData("lvl"), act.getVertexBufferObjectManager());
		text_lvl.setColor(0, 0, 0);
		text_lvl.setHeight(0.1f * h);
		
		attachChild(s_lvl);
		attachChild(text_lvl);
		registerTouchArea(s_speaker);
		attachChild(s_speaker);
		//TODO DODANIE PRZYCISKU LIKE I SHARE (facebook)
	}
	
	void sound()
	{
		char obrazek;
		if(sounds_permission)
			obrazek = 1;
		else
			obrazek = 0;
		
		s_speaker = new Sprite(w, h, t_speaker[obrazek],act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					if(sounds_permission)
						sounds_permission = false;
					else
						sounds_permission = true;
					
					if(sounds_permission)
					{
						s_speaker.setSprite(t_speaker[1]);
						example_music.play();
					}
					else
					{
						s_speaker.setSprite(t_speaker[0]);
						example_music.pause();
					}

					
					return true;
				}
				return false;
			}
		};
		s_speaker.setWidth(0.1f * h);
		s_speaker.setHeight(0.1f * h);
		s_speaker.setPosition(w - s_speaker.getWidth(), 0);	
		
		if(sounds_permission)
		{
			example_music.play();
		}
		else
		{
			example_music.pause();
		}
		
		
	}
}
