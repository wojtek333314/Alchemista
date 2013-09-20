package com.alchemista;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import android.view.MotionEvent;

public class Alchemy extends Scene
{
	MainActivity act;
	
	int w,	
		h;
	
	TextureRegion t_shop,
				  t_free,
				  t_list,
				  t_get,
				  t_shadow0,
				  t_shadow1,
				  t_paper;
	
	ButtonSprite s_shop,
				 s_free,
				 s_list,
				 s_get,
				 s_paper;
	
	ButtonSprite[] s_shadow;				
	//TODO £adowanie misji i opisów
	
	Alchemy()
	{
		act = MainActivity.getSharedInstance();
		act.ID = 2;
		w = act.w;
		h = act.h;
		
		t_shop = new stb("Alchemy/shop", 256, 256).T;
		t_free = new stb("Alchemy/free", 256, 256).T;
		t_list = new stb("Alchemy/list", 512, 512).T;
		t_get = new stb("Alchemy/get", 256, 64).T;
		t_shadow0 = new stb("Alchemy/shadow0", 128, 32).T;
		t_shadow1 = new stb("Alchemy/shadow1", 128, 32).T;
		t_paper = new stb("Alchemy/paper", 512, 512).T;
		
		s_shadow = new ButtonSprite[4];
		
		SpriteBackground s_background = new SpriteBackground(0,w,h,new Sprite(0,0,w,h,new stb("Alchemy/background",1024,1024,1222).T,act.getVertexBufferObjectManager()));
		   this.setBackground(s_background);
		   
		s_paper = new ButtonSprite(w, h, t_paper, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					s_paper.setPosition(w, h);
					return true;
				}
				return false;
			}
		};
		s_paper.setWidth(0.6f * w);
		s_paper.setHeight(0.6f * w);
		registerTouchArea(s_paper);
		attachChild(s_paper);
		   
		grafika();
		grafika1();
		
	}
	
	int nasluch_petli_s_shadow(Sprite t)
	{
		for(int i = 0; i < s_shadow.length; i++ )
		{
			if(t == s_shadow[i])
				return i;
		}
		return -1;
	}
	
	void grafika()
	{
		s_shop = new ButtonSprite(w, h, t_shop, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO act.setCurrentScene(new Shop());
					System.out.println("trolll");
					return true;
				}
				return false;
			}
		};
		s_shop.setWidth(0.15f * h);
		s_shop.setHeight(0.15f * h);
		s_shop.setPosition(0, h/2 - s_shop.getHeight() - 0.06f * h);
		
		s_free = new ButtonSprite(w, h, t_free, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO act.setCurrentScene(new Game(false));
					System.out.println("trolll22222");
					return true;
				}
				return false;
			}
		};
		s_free.setWidth(0.15f * h);
		s_free.setHeight(0.15f * h);
		s_free.setPosition(0, h/2 + 0.06f * h);
		
		s_list = new ButtonSprite(w, h, t_list, act.getVertexBufferObjectManager());
		s_list.setWidth(w - 0.34f * h);
		s_list.setHeight(0.7f * h);
		s_list.setPosition(s_shop.getX() + 0.02f * h + s_shop.getHeight(), 0.15f * h);
		
		s_get = new ButtonSprite(w, h, t_get, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO act.setCurrentScene(new Game(true, misja));
					System.out.println("tr3123123olll");
					return true;
				}
				return false;
			}
		};
		s_get.setWidth(0.4f * h);
		s_get.setHeight(0.1f * h);
		s_get.setPosition(w - 0.01f * h - s_get.getWidth() , h - 0.01f * h - s_get.getHeight());
		
		registerTouchArea(s_shop);
		registerTouchArea(s_free);
		registerTouchArea(s_list);
		registerTouchArea(s_get);
		
		attachChild(s_shop);
		attachChild(s_free);
		attachChild(s_list);
		attachChild(s_get);
	}
	
	void grafika1()
	{
		for(int i = 0; i < 4; i++)
		{
			s_shadow[i] = new ButtonSprite(w, h, t_shadow0, act.getVertexBufferObjectManager())
			{
				@Override
		          public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			      {System.out.println("lol");
		              if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_DOWN)        	   
		              { 
							System.out.println("dada");
							s_shadow[nasluch_petli_s_shadow(this)].setSprite(t_shadow1);
							s_paper.setPosition(0.2f * w, h - 0.6f * w / 2);
		              }
		           return false;
			      }
			};
			s_shadow[i].setWidth(s_list.getWidth() - 0.2f * w);
			s_shadow[i].setHeight(s_list.getHeight()/4 - 0.02f * h);
			
			if(i == 0)
				s_shadow[i].setPosition(s_list.getX() + 0.03f * h, s_list.getY() + 0.01f * h);
			
			if(i > 0)
				s_shadow[i].setPosition(s_shadow[i - 1].getX(), s_shadow[i - 1].getY() + 0.01f * h + s_shadow[i - 1].getHeight());
			
			registerTouchArea(s_shadow[i]);
			attachChild(s_shadow[i]);
		}	
	}
}
