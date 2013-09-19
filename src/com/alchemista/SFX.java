package com.alchemista;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;





public class SFX 
{
private MainActivity act;
	private String filename ;
	Sound sound;
	Music music;
	
	SFX(String f)//dzwieki
	{
		act = MainActivity.getSharedInstance();
		filename = f ;
		
		SoundFactory.setAssetBasePath("sfx/");
	  
	  
	  try
	  {
	      sound = SoundFactory.createSoundFromAsset(act.getSoundManager(), act,filename+".mp3");
	      System.out.println("SFX done!");
	  }
	  catch (IOException e)
	  {
	      e.printStackTrace();
	      System.out.println("SFX err");
	  }
	}
	
	
	
	SFX(String f,boolean looping)//muzyka
	{
		act = MainActivity.getSharedInstance();
		filename = f ;
		
		MusicFactory.setAssetBasePath("sfx/");
	  
	  
	  try
	  {
	      music = MusicFactory.createMusicFromAsset(act.getMusicManager(), act,filename+".mp3");
	      System.out.println("SFX done!");
	  }
	  catch (IOException e)
	  {
	      e.printStackTrace();
	      System.out.println("SFX err");
	  }
	  
	  music.setLooping(looping);
	  
	}
}