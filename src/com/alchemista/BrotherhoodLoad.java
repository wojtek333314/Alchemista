package com.alchemista;

import android.os.AsyncTask;

public class BrotherhoodLoad extends AsyncTask<String,Void,String>
{
	MainActivity	act;
	
	BrotherhoodLoad()
	{
		act = MainActivity.getSharedInstance();
	}
	@Override
	 protected void onPostExecute(String result) {
	  act.setCurrentScene(new Menu());
	 }
	 
	 @Override
	 protected void onPreExecute() {
	  // Analogicznie do metody onPostExecute, implenentujesz czynnosci do zrealizowania przed uruchomieniem w¹tku
	 }
	 
	 @Override
	 protected void onProgressUpdate(Void... values) {
	  //System.out.println(values);
	  }

	@Override
	protected String doInBackground(String... params) {
        
		onProgressUpdate();
        act.sav  = new Save();
        onProgressUpdate();
        act.back = new Back();
        onProgressUpdate();
        act.hero = new Hero(act.sav);
        onProgressUpdate();
        
		return null;
	}
}
