package com.alchemista;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;

import android.view.MotionEvent;

public class Encyklopedia extends Scene implements IScrollDetectorListener, IOnSceneTouchListener, IClickDetectorListener
{
   MainActivity		act;
   int				w,h;
   Save				save;

			
   Sprite			back,
   					foto;				
   ButtonSprite[]	func;
   TiledTextureRegion 	tex_icons,
   						tex_mixtures,
   						tex_skladniki,
   						tex_techniki;
   
   item[]			mikstury,skladniki,techniki;//listy przchowujace dane wszelkie o przedmiocie
   list				LISTA;//scrollowana
   Text				title,
   					description;
   
   int						current_list = 0;
   SurfaceScrollDetector 	mScrollDetector ;
   ClickDetector			mClickDetector;
   
   
   Encyklopedia()
   {
	   act = MainActivity.getSharedInstance();
	   w   = act.w;
	   h   = act.h;
	   act.ID = 2;
	   
	   //tlo
	   SpriteBackground tlo = new SpriteBackground(0,w,h,new Sprite(0,0,w,h,new stb("Encyklopedia/tlo",1024,512,1222).T,act.getVertexBufferObjectManager()));
	   this.setBackground(tlo);
	   //koniec t³a
		
	   tex_icons = new stb("Encyklopedia/icons",1024,256,4,1).tiledT; 
	   tex_skladniki = new stb("Encyklopedia/rosliny",1024,256,8,2).tiledT; 
	   tex_techniki = new stb("Encyklopedia/przygotowanie",1024,256,8,2).tiledT; 
	   backbtn();
	   LISTA = new list(tex_skladniki);

	   funkcyjne();
	   foto_description();
	   mScrollDetector = new SurfaceScrollDetector(this);
	   mClickDetector = new ClickDetector(this);
	   this.setOnSceneTouchListener(this);
	   this.setOnAreaTouchTraversalBackToFront();

   }
	
   
   int getfuncI(ButtonSprite x)
   {
	   for(int i=0;i<func.length;i++)
		   if(func[i].equals(x))return i;
	   return -1;
   }
   
   
   
   void funkcyjne()
   {
	   func = new ButtonSprite[4];
	   float przerwa = 1.5f;// >1
	   for(int i = 0 ;i<4;i++)
	   {
		   
		   func[i] = new ButtonSprite(0,0,tex_icons.getTextureRegion(i),act.getVertexBufferObjectManager())
		   {
			   	@Override
		          public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			      {
		              if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_DOWN)        	   
		              { 
		                current_list = getfuncI(this);
		                switch (current_list)
		                {
		                case 0:
		                	LISTA.changelista(tex_skladniki);
		                	System.out.println("SS");
		                	break;
		                case 3:
		                	LISTA.changelista(tex_techniki);
		                	break;
		                }
		              }
		           return false;
			      }
		   };
		   
		   func[i].setScaleCenter(0, 0);
		   func[i].setScale(back.getScaleY());
		   func[i].setPosition((w/2-func[i].getWidthScaled()*3)+ i*(func[i].getWidthScaled()*przerwa), 0);
		   attachChild(func[i]);
		   registerTouchArea(func[i]);
	   }
   }
   void backbtn()
   {
	   back = new Sprite(0,0,new stb("Encyklopedia/back_button",256,256).T,act.getVertexBufferObjectManager());//TODO manager scen
	   back.setScaleCenter(0, 0);
	   back.setScale(w/10/back.getWidth());
	   back.setPosition(w-back.getWidthScaled(), 0);
	   //attachChild(back);
   }
   
   void foto_description()
   {
	   foto = new Sprite(0,0,new stb("Encyklopedia/blank_foto",128,128).T,act.getVertexBufferObjectManager());
	   foto.setScaleCenter(0, 0);
	   foto.setScale(w/8/foto.getWidth());
	   foto.setPosition(0,LISTA.dolna_krecha);
	   attachChild(foto);
	   
	   title = new Text(0,0,act.mFont,"tytul",1024,act.getVertexBufferObjectManager());
	   description = new Text(0,0,act.mFont,"opis",1024,
			   act.getVertexBufferObjectManager());
	   description = new Text(50, 40, act.mFont, "", 1000, 
			   new TextOptions(AutoWrap.LETTERS, w*0.98f, HorizontalAlign.LEFT, Text.LEADING_DEFAULT), 
			   act.getVertexBufferObjectManager());
		
	   
	   title.setScaleCenter(0, 0);
	   title.setScale(foto.getHeightScaled()/2/title.getHeight());
	   title.setPosition(foto.getX()+foto.getWidthScaled(), foto.getY());
	   attachChild(title);
	   
	   description.setScaleCenter(0, 0);
	  // description.setScale(foto.getHeightScaled()/3/description.getHeight());
	   description.setPosition(foto.getX(), foto.getY()+foto.getHeightScaled());
	   attachChild(description);
   }
   
   class list
   {
	   int					rozmiar;//rozmiar listy czyli ilosc elementow
	   float				dolna_krecha;//pozycja Y dolnej kreski itemów - od tej linni wyswietlane jest foto dla itemu
	   TiledTextureRegion 	tex;//obecny plik przechowujacy tekstury itemow
	   item[]				items;//przechowuje wszystkie itemy danego pliku tekstur
	   FileRead				dane;
	   
	   list(TiledTextureRegion k)
	   {
		   rozmiar = k.getTileCount();
		   items = new item[tex_skladniki.getTileCount()];
		   
		   if(k.equals(tex_skladniki)) dane = new FileRead("Encyklopedia/skladniki.txt",32);
		   if(k.equals(tex_techniki))  dane = new FileRead("Encyklopedia/przygotowanie.txt",32);
		   
		   for(int i=0;i<items.length;i++)
		   {
			   items[i] = new item(k,i,dane);
			   items[i].btn.setPosition(i*items[i].btn.getWidthScaled(),back.getY()+back.getHeightScaled());
			   attachChild(items[i].btn);
			   registerTouchArea(items[i].btn);
		   }
		   dolna_krecha = items[0].btn.getY()+items[0].btn.getHeightScaled();
	   }
	   
	   void przesun(float U)
	   {
		   for(int i=0;i<items.length;i++)
			   items[i].btn.setPosition(items[i].btn.getX() + U, items[i].btn.getY());
	   }
	   
	   
	   void changelista(TiledTextureRegion k)
	   {
		 
		   if(k.equals(tex_skladniki)) dane = new FileRead("Encyklopedia/skladniki.txt",32);
		   if(k.equals(tex_techniki))  dane = new FileRead("Encyklopedia/przygotowanie.txt",32);
		   
		   for(int i=0;i<items.length;i++)
		   {
		
			   detachChild(items[i].btn);
			   unregisterTouchArea(items[i].btn);
		   }
		  
		   rozmiar = k.getTileCount();
		   items = new item[tex_skladniki.getTileCount()]; 
		   for(int i=0;i<items.length;i++)
		   {
			   items[i] = new item(k,i,dane);
			   items[i].btn.setPosition(i*items[i].btn.getWidthScaled(),back.getY()+back.getHeightScaled());
			   attachChild(items[i].btn);
			   registerTouchArea(items[i].btn);
		   }
		   
	   }
   }
   
   
   
   class item
   {
	   ButtonSprite			btn;//zarowno ikonka do listy jak i zdjecie pogladowe
	   TiledTextureRegion 	tex_fill;
	   String				tytul;
	   String				opis;
	   boolean				visible;
	   int 					indeks;
	   
	   item(TiledTextureRegion teksturka,int index,FileRead dane)
	   {
		   indeks = index;
		   tex_fill = teksturka;
		   visible  = false;
		   
		   tytul = dane.F[indeks*2];
		   opis  = dane.F[indeks*2+1];
		   
		   btn = new ButtonSprite(0,0,teksturka.getTextureRegion(index),act.getVertexBufferObjectManager())
		   {
				@Override
		          public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			      {
		              if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_DOWN)        	   
		              { 
		            	 wczytajdane();
		              }
		             return false;
			      }
		   };
		   btn.setScaleCenter(0, 0);
		   btn.setScale(w/14/btn.getWidth());   
	   }
	   
	   void wczytajdane()
	   {
		   title.setText(tytul);
		   description.setText(opis);
		   title.setScaleCenter(0, 0);
		   title.setScale(foto.getHeightScaled()/2/title.getHeight());
		   title.setPosition(foto.getX()+foto.getWidthScaled(), foto.getY());
		  
		   description.setScaleCenter(0, 0);
		 //  description.setScale(foto.getHeightScaled()/3/description.getHeight());
		   description.setPosition(foto.getX(), foto.getY()+foto.getHeightScaled());

		   detachChild(foto);
		   foto = new Sprite(0,0,btn.getTextureRegion(),act.getVertexBufferObjectManager());
		   foto.setScaleCenter(0, 0);
		   foto.setScale(w/8/foto.getWidth());
		   foto.setPosition(0,LISTA.dolna_krecha);
		   attachChild(foto);
	   }
   }



@Override
public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
		float pDistanceX, float pDistanceY) {
	
}


@Override
public void onScroll(ScrollDetector dete, int pPointerID,
		float px, float py) {
	 
	 	LISTA.przesun(px);
	
	
}


@Override
public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
		float pDistanceX, float pDistanceY) {
	
}


@Override
public boolean onSceneTouchEvent(Scene pScene, TouchEvent ev) {
	
	if(ev.getY()<this.LISTA.dolna_krecha && ev.getY()>back.getHeightScaled())
	{
		this.mScrollDetector.onTouchEvent(ev);

	}

	return false;
}


@Override
public void onClick(ClickDetector pClickDetector, int pPointerID,
		float pSceneX, float pSceneY) {

	
}
  
}