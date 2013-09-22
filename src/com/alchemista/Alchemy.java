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
		h,
		s_shadow_clicked_index = 0;
	
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
	
	Sprite[] s_shadow;//musi byc spritem bo .setSprite() nie dziala dla ButtonSpritów				
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
		
		s_shadow = new Sprite[4];
		
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
					registerShadowTouchAreas();
					return true;
				}
				return false;
			}
		};
		//s_paper.setWidth(0.6f * w); a co jak szerokosc ekranu bedzie w takim stosunku ze wysokosc spritu bedzie wyzsza niz ekranu?(wlasnie tak zadzialalo na moim telefonie)
		//s_paper.setHeight(0.6f * w);//nie uzalezniaj wysokosci spritu od szerokosci ekranu to nielogiczne
		s_paper.setScaleCenter(0, 0);
		s_paper.setScale(h/s_paper.getHeight());
		   
		grafika();
		grafika1();
		registerTouchArea(s_paper);
		attachChild(s_paper);//musi byc przed wszystkimi spritami
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
					act.setCurrentScene(new Shop(0));
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
		//registerTouchArea(s_list); CO TO KURWA MA BYÆ?! TODO  PO KIEGO TO REJESTROWALES?!
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
			s_shadow[i] = new Sprite(w, h, t_shadow0, act.getVertexBufferObjectManager())
			{
				@Override
		          public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			      {
		              if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_DOWN)        	   
		              { 
							s_shadow[s_shadow_clicked_index].setSprite(t_shadow0);//usuwa zaznaczenie poprzedniego sprite
							s_shadow[nasluch_petli_s_shadow(this)].setSprite(t_shadow1);//zaznacza sprite 
							s_shadow_clicked_index = nasluch_petli_s_shadow(this);//ustawia index ktory sprite jest zaznaczony
							s_paper.setPosition(w/2-s_paper.getWidthScaled()/2,h/2-s_paper.getHeightScaled()/2);//s_paper.setPosition(0.2f * w, h - 0.6f * w / 2);  a nie mozna po prostu wysrodkowac? PS: h - 0.6f * w /2 nie umieszczaj dla Y uzaleznienia od szerokosci.bezsens                                        
							unregisterShadowTouchAreas();//odrejestrowuje wszystkie touche listy bo jak klikniesz w kartke to tez je rejestruje jako klikniecia
		              }
		           return true;
			      }
			};
			s_shadow[i].setWidth(s_list.getWidth() *0.9f ); //s_shadow[i].setWidth(s_list.getWidth() - 0.2f * w);  CO TO JEST? UTRUDNIASZ PROSTE RZECZY co ma szer.ekranu do szer listy?
			s_shadow[i].setHeight(s_list.getHeight()/4 - 0.02f * h);
			
			/*if(i == 0)
				s_shadow[i].setPosition(s_list.getX() + 0.03f * h, s_list.getY() + 0.01f * h);
			
			if(i > 0)
				s_shadow[i].setPosition(s_shadow[i - 1].getX(), s_shadow[i - 1].getY() + 0.01f * h + s_shadow[i - 1].getHeight());
				
			W£¥CZ MYŒLENIE ZANIM NAPISZESZ TAKI BEZSENS robisz na czuja wszystko tylko masa testowania a jak sie spierdoli to potem szukaj w tym gaszczu...
			*/
			float odstep = s_shadow[i].getHeightScaled()*0.2f;// odstep miedzy kazdym zleceniem o jedn¹ pi¹t¹ wysokosci tabelki
			s_shadow[i].setPosition(s_list.getX()+s_list.getWidthScaled()/2 - s_shadow[i].getWidthScaled()/2,(s_list.getY()* 1.05f * i+1)+ s_shadow[i].getHeightScaled()+odstep);
			
			attachChild(s_shadow[i]);
			registerTouchArea(s_shadow[i]);
			
		}	
	}
	
	
	private void unregisterShadowTouchAreas()
	{
		for(int i=0;i<s_shadow.length;i++)
			unregisterTouchArea(s_shadow[i]);
	}
	
	private void registerShadowTouchAreas()
	{
		for(int i=0;i<s_shadow.length;i++)
			registerTouchArea(s_shadow[i]);
	}
	
	
	
	
	
	
	
	
	
	
}
