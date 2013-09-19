package com.alchemista;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class stb
{
	MainActivity act;
	
	 public BitmapTextureAtlas A; //atlas
	 public TextureRegion T;//tekstura
	 public TiledTextureRegion tiledT;
	
	 /** konstruktor dla plikow png**/
		stb(String filename ,int w , int h)
		{
			act = MainActivity.getSharedInstance();
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			A = new BitmapTextureAtlas(act.getTextureManager(),w,h, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
	  	  	T = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.A, act, filename + ".png", 0, 0);
	  	  	A.load();//
		}
		
		/** konstruktor dla plików jpg**/
		stb(String filename ,int w , int h,int wpisz_byle_co_dla_rozroznienia_konstruktorow)
		{
			act = MainActivity.getSharedInstance();
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			A = new BitmapTextureAtlas(act.getTextureManager(),w,h, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
	  	  	T = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.A, act, filename + ".jpg", 0, 0);
	  	  	A.load();//
		}
	
	
	stb(String filename ,int w , int h,int row,int column)//row - ile w szerokosci obrazkow column w pionie 
	{
		act = MainActivity.getSharedInstance();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		A = new BitmapTextureAtlas(act.getTextureManager(),w,h, TextureOptions.DEFAULT);
  	  	tiledT = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(A, act, filename + ".png", 0, 0, row, column);
  	  	A.load();//
	}
	
	
	
	
	
	
	
	
}
