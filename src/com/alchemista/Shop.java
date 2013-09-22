package com.alchemista;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.view.MotionEvent;

public class Shop extends Scene
{
  MainActivity	act;
  int			w,
  				h,
  				choosed = 0;
  
  item[]		item;
  TiledTextureRegion	tex_func;
  Sprite[]		func;
  Sprite		desk;
  Sprite		coin_icon;
  Text			cash;
  /**
   * @param amulets_or_gradients
   * 0-gradients, 1 - amulets
   */
  Shop(int amulets_or_gradients)
  {
	  act = MainActivity.getSharedInstance();
	  w   = act.w;
	  h   = act.h;
	  
	  func = new Sprite[3];//zawsze 3 w zaleznosci od c-struktora skladniki/amulety
	  tex_func = new stb("Shop/shop_icons",512,128,4,1).tiledT;
	 

	  func_buttons(amulets_or_gradients);
	  coin_cash();
	  desk();
	 
	  
  }
  
  void desk()
  {
	  desk	   = new Sprite(0,0,new stb("Alchemy/list",512,512).T,act.getVertexBufferObjectManager());
	  desk.setWidth(w*0.8f);
	  desk.setHeight((h-func[0].getHeightScaled()-coin_icon.getHeightScaled()));
	  desk.setPosition(w/2 - desk.getWidthScaled()/2, func[0].getHeightScaled());
	  attachChild(desk);
  }
  int getIndexFunc(Sprite x)
  {
	  for (int i=0;i<func.length;i++)
		  if(x==func[i])return i;
	  
	  return -1;
  }
  void func_buttons(int in)
  {
	 ITextureRegion tex;
	
	 for (int i=0;i<func.length;i++)
	 {
		 
		 tex = tex_func.getTextureRegion(i+1);
		 if(in==0 && i ==0) tex = tex_func.getTextureRegion(0);
		 func[i]=new Sprite(0,0,tex,act.getVertexBufferObjectManager())
		 {
			 @Override
	          public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		      {
	              if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_DOWN)        	   
	              { 
	            	 choosed = getIndexFunc(this);
	            	 wczytajdane();
	              }
	             return false;
		      }
		 };
		 
		func[i].setScaleCenter(0, 0);
		func[i].setScale(w/10/func[i].getWidth());
		func[i].setPosition(((w/2 - func[i].getWidthScaled()*1.5f) + (i+1)*func[i].getWidthScaled()*1.5f)-2*func[i].getWidthScaled(), 0);
		attachChild(func[i]);
		registerTouchArea(func[i]);
	 }
	
	 
	
  }
  
  
  void wczytajdane()
  {
	  System.out.println(choosed);
  }
  
  void coin_cash()
  {
	  coin_icon = new Sprite(0,0,tex_func.getTextureRegion(3),act.getVertexBufferObjectManager());
	  coin_icon.setScaleCenter(0, 0);
	  coin_icon.setScale(w/20/coin_icon.getWidthScaled());
	  coin_icon.setPosition(0, h-coin_icon.getHeightScaled());
	  attachChild(coin_icon);
	  
	  cash = new Text(0,0,act.mFont,act.sav.getData("money"),1024,act.getVertexBufferObjectManager());
	  cash.setScaleCenter(0, 0);
	  cash.setScale(coin_icon.getHeightScaled()/cash.getHeight());
	  cash.setPosition(coin_icon.getX()+coin_icon.getWidthScaled(),coin_icon.getY());
	  attachChild(cash);
  }
  
  void updateCash()
  {
	  cash.setText(act.sav.getData("money"));
	  cash.setScaleCenter(0, 0);
	  cash.setScale(coin_icon.getHeightScaled()/cash.getHeight());
	  cash.setPosition(coin_icon.getX()+coin_icon.getWidthScaled(),coin_icon.getY());
  }
  
  
  
  
  
  
  
  
  class item
  {
	  ButtonSprite	buy,
	  				ency;
	  String		Fnazwa;
	  Text			nazwa;
	  Text			cena;
	  
  }
  
}
