package com.alchemista;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;

public class BrotherhoodIntroScene extends Scene
{
	MainActivity 	act;
	int 			w,h;
	
	BrotherhoodIntroScene()
	{
		act = MainActivity.getSharedInstance();
		w = act.w;
		h = act.h;
		
		   SpriteBackground tlo = new SpriteBackground(0,w,h,new Sprite(0,0,w,h,new stb("Brotherhood/brotherhood",1024,512,1222).T,act.getVertexBufferObjectManager()));
		   this.setBackground(tlo);
		BrotherhoodLoad	asyncLoad = new BrotherhoodLoad();
		asyncLoad.execute("x");
	}
}
