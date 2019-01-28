package com.enamelbd.vatchecker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.google.analytics.tracking.android.EasyTracker;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class home extends Activity {
	
	
	
	
	ListView list;
	LazyAdapter adapter;
	private DownloadTask mTask = new DownloadTask();
	private DownloadFeed feedTask = new DownloadFeed();
	ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> dataSearch = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> feedList = new ArrayList<HashMap<String, String>>();

	
	
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_NAME = "name";
	static final String KEY_icon = "icon";
	static final String KEY_icon_orbit = "icon_orbit";
	static final String KEY_ra_deg = "ra_deg";
	static final String KEY_dec_deg = "dec_deg";
	static final String KEY_alter_name = "alter_name";
	static final String KEY_type = "type";
	static final String KEY_today = "today";
	static final String KEY_yesterday = "yesterday";
	static final String KEY_tenday = "tenday";
	static final String KEY_mean = "mean";
	static final String KEY_peak = "peak";
	static final String KEY_days = "days";
	static final String KEY_last_day = "last_day";
	static final String KEY_time = "time";
	
	
	

	static final String KEY_TITLE = "title";
	static final String KEY_SDETAILS = "sdetails";
	static final String KEY_DETAILS = "details";
	static final String KEY_LINK = "link";
	static final String KEY_TIME = "time";
	static final String KEY_IMAGE = "image";
	static final String KEY_THUMB_URL = "thumb_url";
	
	
	
	private ProgressDialog prgDialog;
	public static final int progress_bar_type = 0;
	private static String file_url = "http://programmerguru.com/android-tutorial/wp-content/uploads/2014/01/jai_ho.mp3";
	private String abcd;
	public static int randNum = 0;
	private static String only_file_name = "";
	private static String file_name = "";
	private String url = "http://enamelbd.org/apps/nasa/space.php";
	
	private String feedURL = "http://enamelbd.org/vat/main.php"; //juba
	
	
	 DBHelper mydb;
	 
	 
	 
	 
	 
	 
	 public void loadData(){
		 
		 
		 Cursor c;
		 // ============================================
	     mydb = new DBHelper(this);
	     
	     if(url.contentEquals("bookmark")){
	    	 c = mydb.getAllBookmark();
	     }else{
	     c = mydb.getAllCompany(url);
	     }
	     
	     if(c.toString().length() > 10){
	    	 findViewById(R.id.progressBar).setVisibility(View.GONE);
	     }else{
	    	Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show(); 
	     }
	     
	     
	     HashMap<String, String> map;
	     if(c!=null ){
	    	 c.moveToFirst();
	    	 while(  c.isAfterLast() == false )
             {
	    		 map = new HashMap<String, String>(); 
	    		 
	    		 url = c.getString(17).toString();
	    		 
	    			map.put(KEY_NAME, c.getString(1).toString());
					map.put(KEY_ra_deg, c.getString(2).toString());
					map.put(KEY_dec_deg, c.getString(3).toString());
					map.put(KEY_today, c.getString(4).toString());
					map.put(KEY_icon, c.getString(5).toString());
					map.put(KEY_icon_orbit, c.getString(6).toString());
					map.put(KEY_alter_name, c.getString(7).toString());
					map.put(KEY_type, c.getString(8).toString());
					
					map.put(KEY_yesterday, c.getString(9).toString());
					map.put(KEY_tenday, c.getString(10).toString());
					map.put(KEY_mean, c.getString(11).toString());
					map.put(KEY_peak, c.getString(12).toString());
					map.put(KEY_days, c.getString(13).toString());
					map.put(KEY_last_day, c.getString(14).toString());
					map.put(KEY_time, c.getString(15).toString());
					
					
					TextView update_time = (TextView) findViewById(R.id.update_time); // duration
					update_time.setText(c.getString(15).toString());
					dataList.add(map);
					c.moveToNext();
             }
	    }
	     
	     ActionBar bar = getActionBar();
		 bar.show();
	     findViewById(R.id.newsfeed).setVisibility(View.VISIBLE);
			findViewById(R.id.progressBar).setVisibility(View.GONE);
			findViewById(R.id.list).setVisibility(View.VISIBLE);
			list =(ListView) findViewById(R.id.list);
			adapter=new LazyAdapter(home.this, dataList);        
	        list.setAdapter(adapter);
	     
	        
	        listClicker();
		 
	 }
	 
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public void shortData(String type){
     	   dataList.clear();
    	   Cursor c;
  		 // ============================================
  	     mydb = new DBHelper(home.this);
  	     
  	     if(type.contentEquals("ra")){
  	   c = mydb.getAllCompanyByRa(url);
  	     }else if(type.contentEquals("dec")){
  	    	c = mydb.getAllCompanyByDec(url); 
  	     }else{
  	    	c = mydb.getAllCompany(url);
  	     }
  	   HashMap<String, String> map;
  	if(c!=null ){
  		c.moveToFirst();
  		while(  c.isAfterLast() == false )
        {
            map = new HashMap<String, String>(); // <---- moved here
            		
            					map.put(KEY_NAME, c.getString(1).toString());
            					map.put(KEY_ra_deg, c.getString(2).toString());
            					map.put(KEY_dec_deg, c.getString(3).toString());
            					map.put(KEY_today, c.getString(4).toString());
            					map.put(KEY_icon, c.getString(5).toString());
            					map.put(KEY_icon_orbit, c.getString(6).toString());
            					map.put(KEY_alter_name, c.getString(7).toString());
            					map.put(KEY_type, c.getString(8).toString());
            					
            					map.put(KEY_yesterday, c.getString(9).toString());
            					map.put(KEY_tenday, c.getString(10).toString());
            					map.put(KEY_mean, c.getString(11).toString());
            					map.put(KEY_peak, c.getString(12).toString());
            					map.put(KEY_days, c.getString(13).toString());
            					map.put(KEY_last_day, c.getString(14).toString());
            					map.put(KEY_time, c.getString(15).toString());
            					
            					
            					TextView update_time = (TextView) findViewById(R.id.update_time); // duration
            					update_time.setText(c.getString(15).toString());
            				
            					
            					dataList.add(map);
            					 c.moveToNext();

        }   
  		
  		
  		findViewById(R.id.newsfeed).setVisibility(View.VISIBLE);
		findViewById(R.id.progressBar).setVisibility(View.GONE);
		findViewById(R.id.list).setVisibility(View.VISIBLE);
		list =(ListView) findViewById(R.id.list);
		adapter=new LazyAdapter(home.this, dataList);        
        list.setAdapter(adapter);
  		
  		
  	}
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public void listClickerObject(String str_source_name, String str_ra_deg, String str_dec_deg, String str_alter_name, String str_type, String str_today, String str_yesterday, String str_tenday, String str_mean, String str_peak, String str_days, String str_last_day, String str_icon, String str_icon_orbit, String str_time){
	    	
			LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View v = vi.inflate(R.layout.table, null);
			
			
			   
         ViewGroup insertPoint = (ViewGroup) findViewById(R.id.details_main);
			//insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			insertPoint.removeAllViews();
			
			
         TextView name = (TextView)v.findViewById(R.id.details_source_name); // title
       TextView ra_deg = (TextView)v.findViewById(R.id.ra_deg); // artist name
        TextView dec_deg = (TextView)v.findViewById(R.id.dec_deg); // duration
        
         TextView source_name = (TextView)v.findViewById(R.id.source_name); // duration
         TextView alter_name = (TextView)v.findViewById(R.id.alter_name); // duration
         TextView type = (TextView)v.findViewById(R.id.type); // duration
         TextView today = (TextView)v.findViewById(R.id.today); // duration
         TextView yesterday = (TextView)v.findViewById(R.id.yesterday); // duration
         TextView tenday = (TextView)v.findViewById(R.id.tenday); // duration
         TextView mean = (TextView)v.findViewById(R.id.mean); // duration
         TextView peak = (TextView)v.findViewById(R.id.peak); // duration
         TextView days = (TextView)v.findViewById(R.id.days); // duration
         TextView last_day = (TextView)v.findViewById(R.id.last_day); // duration
         TextView graph_text = (TextView)v.findViewById(R.id.graph_text); // duration
         
         
			
			
         name.setText(str_source_name);
         ra_deg.setText(str_ra_deg);
         dec_deg.setText(str_dec_deg);
         source_name.setText(str_source_name);
         alter_name.setText(str_alter_name);
         type.setText(str_type);
         today.setText(str_today);
         yesterday.setText(str_yesterday);
         tenday.setText(str_tenday);
         mean.setText(str_mean);
         peak.setText(str_peak);
         days.setText(str_days);
         last_day.setText(str_last_day);
         graph_text.setText("Dashed lines show average rate (and +/- 1 sigma standard deviation) for this source (derived from this plot)");
			
        
         
         only_file_name = str_source_name;
         
        
         TextView table_update_time = (TextView) v.findViewById(R.id.table_update_time); // duration
         table_update_time.setText(str_time);
		
         
         mydb = new DBHelper(home.this);
         Cursor c = mydb.getData(str_source_name, url);
			c.moveToFirst();
		//	Toast.makeText(getApplicationContext(), c.getString(16).toString(), 1000).show();
			if(c.getString(16).toString().contentEquals("false")){
				
				v.findViewById(R.id.btnBookmark).setVisibility(View.VISIBLE);
				v.findViewById(R.id.btnRemoveBookmark).setVisibility(View.GONE);
	            
			}else{
				v.findViewById(R.id.btnBookmark).setVisibility(View.GONE);
				v.findViewById(R.id.btnRemoveBookmark).setVisibility(View.VISIBLE);
	            
			}
         
         
         Button btnBookmark = (Button)v.findViewById(R.id.btnBookmark);
         btnBookmark.setTag(str_source_name);
         btnBookmark.setOnClickListener(new Button.OnClickListener() {
             View vi = v;
         	@Override
             public void onClick(View vi) {
             	
             	
             	mydb = new DBHelper(home.this);
 				Cursor c = mydb.getData(vi.getTag().toString(), url);
 				c.moveToFirst();
 				int cnt = c.getCount();
 				if(cnt > 0){
 					mydb.addBookmark(c.getString(0).toString(), "true");
 					Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
 					v.findViewById(R.id.btnBookmark).setVisibility(View.GONE);
						v.findViewById(R.id.btnRemoveBookmark).setVisibility(View.VISIBLE);
		 	              
 				}else{
 					Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
		                	
 				}
             	
             	
           		} // end onClick
         }); 
         
         
         
         
         
         
         
         Button btnRemoveBookmark = (Button)v.findViewById(R.id.btnRemoveBookmark);
         btnRemoveBookmark.setTag(str_source_name);
         btnRemoveBookmark.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View vi) {
             	
             	
             	mydb = new DBHelper(home.this);
 				Cursor c = mydb.getData(vi.getTag().toString(), url);
 				c.moveToFirst();
 				int cnt = c.getCount();
 				if(cnt > 0){
 					mydb.addBookmark(c.getString(0).toString(), "false");
 					Toast.makeText(getApplicationContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
 					v.findViewById(R.id.btnBookmark).setVisibility(View.VISIBLE);
 					v.findViewById(R.id.btnRemoveBookmark).setVisibility(View.GONE);
		 	             
 				}else{
 					Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
		                	
 				}
             	
             	
           		} // end onClick
         }); 
         
         
         
         
         
         
         Button btnDayLight = (Button)v.findViewById(R.id.btnDownloadDayLight);
         btnDayLight.setTag(str_icon);
         btnDayLight.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View v) {
             	
             	
             	Random r = new Random();
	             randNum = r.nextInt(14656000 - 100) + 65;
	 	       // Toast.makeText(getApplicationContext(), ""+i1, 1000).show();
	 	        
	        	 
	        		try{
	        			  File folder = new File(Environment.getExternalStorageDirectory() + "/Space Monitor");
	        			  boolean success = true;
	        			  if (!folder.exists()) {
	        			      success = folder.mkdir();
	        			  }
	        				}catch (Exception e) {
	        				   
	        				    Toast.makeText(getApplicationContext(), "Something went wrong.", 1500).show();
	        				}
	        			  file_url = v.getTag().toString().replace(".png", ".lc.txt");
	        			file_name = only_file_name+"(Daily light curves)";
	        	 new DownloadMusicfromInternet().execute(file_url);
          
           		//Toast.makeText(context, v.getTag().toString(), 1000).show();
             } // end onClick
         }); 
         
         
         
         
         
         
         
         Button btnOrbitLight = (Button)v.findViewById(R.id.btnDownloadOrbitLight);
         btnOrbitLight.setTag(str_icon);
         btnOrbitLight.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View v) {
             	
             	
             	Random r = new Random();
	             randNum = r.nextInt(14656000 - 100) + 65;
	 	       // Toast.makeText(getApplicationContext(), ""+i1, 1000).show();
	 	        
	        	 
	        		try{
	        			  File folder = new File(Environment.getExternalStorageDirectory() + "/Space Monitor");
	        			  boolean success = true;
	        			  if (!folder.exists()) {
	        			      success = folder.mkdir();
	        			  }
	        				}catch (Exception e) {
	        				   
	        				    Toast.makeText(getApplicationContext(), "Something went wrong.", 1500).show();
	        				}
	        			  file_url = v.getTag().toString().replace(".png", ".orbit.lc.txt");
	        			file_name = only_file_name+"(Orbit light curves)";
	        			  new DownloadMusicfromInternet().execute(file_url);
          
           		//Toast.makeText(context, v.getTag().toString(), 1000).show();
             } // end onClick
         }); 
         
         
         
         
         
         
         
         
         ImageView graph_1 = (ImageView)v.findViewById(R.id.graph_1);
         graph_1.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View v) {
             	ImageView gr_1 = (ImageView) v.findViewById(R.id.graph_1);
             	BitmapDrawable drawable = (BitmapDrawable) gr_1.getDrawable();
                 Bitmap bitmap = drawable.getBitmap();
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                 File image = new File(sdCardDirectory, "0sdzbfsdv.png");
                 boolean success = false;

                 FileOutputStream outStream;
                 try {

                     outStream = new FileOutputStream(image);
                     bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
                     /* 100 to keep full quality of the image */

                     outStream.flush();
                     outStream.close();
                     success = true;
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             	
                 if (success) {
                 	Intent intent = new Intent(home.this, imageView.class);
    				 intent.putExtra("url", "http://enamelbd.org/apps/nasa/space.php");
    				//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
    	        		startActivity(intent);
                 } else {
                      Toast.makeText(getApplicationContext(),
                             "Something went wrong", Toast.LENGTH_LONG).show();
                 }
            } // end onClick
         }); 
         
         
         
         
         
         ImageView graph_2 = (ImageView)v.findViewById(R.id.graph_2);
         graph_2.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View v) {
             	ImageView gr_2 = (ImageView) v.findViewById(R.id.graph_2);
             	BitmapDrawable drawable = (BitmapDrawable) gr_2.getDrawable();
                 Bitmap bitmap = drawable.getBitmap();
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                 File image = new File(sdCardDirectory, "0sdzbfsdv.png");
                 boolean success = false;

                 FileOutputStream outStream;
                 try {

                     outStream = new FileOutputStream(image);
                     bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream); 
                     /* 100 to keep full quality of the image */

                     outStream.flush();
                     outStream.close();
                     success = true;
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                    e.printStackTrace();
                 }
             	
                 if (success) {
                 	Intent intent = new Intent(home.this, imageView.class);
    				 intent.putExtra("url", "http://enamelbd.org/apps/nasa/space.php");
    				//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
    	        		startActivity(intent);
                 } else {
                      Toast.makeText(getApplicationContext(),
                             "Something went wrong", Toast.LENGTH_LONG).show();
                 }
                
                 
             } // end onClick
         }); 
         
         
         
         
         
         
         insertPoint = (ViewGroup) findViewById(R.id.details_main);
			insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			
			
			// ========= Stop View =================
			
			findViewById(R.id.details).setVisibility(View.VISIBLE);
			findViewById(R.id.newsfeed).setVisibility(View.GONE);
			
			
			
			//==============================================================
	        new DownloadImageTask((ImageView) findViewById(R.id.graph_1))
	        .execute(str_icon);
	        new DownloadImageTask((ImageView) findViewById(R.id.graph_2))
	        .execute(str_icon_orbit);
			//=====================================================================
			
			
}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public void listClicker(){
	    	list.setOnItemClickListener(new OnItemClickListener() {
   	        	
   				@SuppressLint("NewApi") public void onItemClick(AdapterView<?> parent, View view,
   						int position, long id) {
   					
   			listClickerObject(dataList.get(+position).get(KEY_NAME), dataList.get(+position).get(KEY_ra_deg), dataList.get(+position).get(KEY_dec_deg), dataList.get(+position).get(KEY_alter_name), dataList.get(+position).get(KEY_type), dataList.get(+position).get(KEY_today), dataList.get(+position).get(KEY_yesterday), dataList.get(+position).get(KEY_tenday), dataList.get(+position).get(KEY_mean), dataList.get(+position).get(KEY_peak), dataList.get(+position).get(KEY_days), dataList.get(+position).get(KEY_last_day), dataList.get(+position).get(KEY_icon), dataList.get(+position).get(KEY_icon_orbit), dataList.get(+position).get(KEY_TIME));		
   					
   				}
   			});	
	 }
	 
	 
	 
	
	
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	ImageView bmImage;

    	public DownloadImageTask(ImageView bmImage) {
    	    this.bmImage = bmImage;
    	}

    	protected Bitmap doInBackground(String... urls) {
    	    String urldisplay = urls[0];
    	    Bitmap mIcon11 = null;
    	    try {
    	        InputStream in = new java.net.URL(urldisplay).openStream();
    	        mIcon11 = BitmapFactory.decodeStream(in);
    	    } catch (Exception e) {
    	        Log.e("Error", e.getMessage());
    	        e.printStackTrace();
    	    }
    	    return mIcon11;
    	}

    	protected void onPostExecute(Bitmap result) {
   	    bmImage.setImageBitmap(result);
   	}
    }
	
	
	
	
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("Downloading...");
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prgDialog.setCancelable(false);
			prgDialog.show();
			return prgDialog;
		default:
			return null;
		}
	}
	
	
	
	
	
	// Async Task Class
				class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

					// Show Progress bar before downloading Music
					@SuppressLint("NewApi") @Override
					protected void onPreExecute() {
						super.onPreExecute();
						// Shows Progress Bar Dialog and then call doInBackground method
						showDialog(progress_bar_type);
						
						prgDialog.setProgress(0);
						
						// 
						
						
					// ===============================================	
						
						
						
					}

					// Download Music File from Internet
					@SuppressLint("NewApi") @Override
					protected String doInBackground(String... f_url) {
						int count;
						try {
							URL url = new URL(f_url[0]);
							URLConnection conection = url.openConnection();
							conection.connect();
							
							
							// Get Music file length
							int lenghtOfFile = conection.getContentLength();
							// input stream to read file - with 8k buffer
							InputStream input = new BufferedInputStream(url.openStream(),10*1024);
							
							// Getting time===========
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy(HH-mm-ss aaa)");
							String currentDateandTime = sdf.format(new Date());
							// =====================
							
							// Output stream to write file in SD card
							OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Space Monitor/"+file_name +"-"+currentDateandTime+".txt");
							byte data[] = new byte[1024];
							double total = 0.0;
							while ((count = input.read(data)) != -1) {
								total += count;
								// Publish the progress which triggers onProgressUpdate method
								publishProgress("" + (int) ((total * 100) / lenghtOfFile));

							String format = "B";
							double size = total;
							if(size > 1024){
								format = "KB";
								size = size / 1024;
								if(size > 1024){
									format = "MB";
									size = size / 1024;
								}
							}
								
								
								
								
								
								
								
								
								// Write data to file
								output.write(data, 0, count);
							}
							// Flush output
							output.flush();
							// Close streams
							output.close();
							input.close();
						} catch (Exception e) {
							Log.e("Error: ", e.getMessage());
						}
						return null;
					}

					// While Downloading Music File
					protected void onProgressUpdate(String... progress) {
						// Set progress percentage
						prgDialog.setProgress(Integer.parseInt(progress[0]));
					}

					// Once Music File is downloaded
					@Override
					protected void onPostExecute(String file_url) {
						// Dismiss the dialog after the Music file was downloaded
						try{
						dismissDialog(progress_bar_type);
						Toast.makeText(getApplicationContext(), "Download complete, Image saved at: "+Environment.getExternalStorageDirectory().getPath()+"/Space Monitor/", Toast.LENGTH_LONG).show();
						// Play the music
					//	playMusic();
						
						
					   
					}catch (Exception d) {
					    //Log.d("Exception", e.getMessage());
					    
					}
						
						
						
					}
				}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
private  class DownloadFeed extends AsyncTask<String, Void, Document>{
		
		
		@Override
	       protected void onPreExecute() {
			super.onPreExecute();
			
	
			 if(!isNetworkAvailable(home.this)){
	       			Toast.makeText(getApplicationContext(), "Please check your internet connection...", Toast.LENGTH_LONG).show();
	       		}
			 findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		}
	
		Document doc = null;
		
		protected Document doInBackground(String... args) {
			
		
			
			
			try{
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(feedURL); // getting XML from URL
				doc = parser.getDomElement(xml); // getting DOM element
			}catch (Exception e) {
			    //Log.d("Exception", e.getMessage());
				feedTask.cancel(true);
			}
			
			return doc;
			
			
		}
		
		@Override
        protected void onPostExecute(Document doc) {
			try{
			
				ActionBar bar = getActionBar();
				 bar.show();
				 
				feedTask.cancel(true);	
			
			
	   		XMLParser parser = new XMLParser();
	   		NodeList nl = doc.getElementsByTagName(KEY_SONG);
	   		
	   		
	   		for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				
				
				
				if(parser.getValue(e, KEY_TITLE).contentEquals("line")){
					//isLastFeed = "true";
					//Toast.makeText(getApplicationContext(), "No more posts to show.", Toast.LENGTH_LONG).show();
				}else{
		
				// adding each child node to HashMap key => value
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_SDETAILS, parser.getValue(e, KEY_SDETAILS));
					map.put(KEY_DETAILS, parser.getValue(e, KEY_DETAILS));
					map.put(KEY_TIME, parser.getValue(e, KEY_TIME));
					map.put(KEY_LINK, parser.getValue(e, KEY_LINK));
					map.put(KEY_IMAGE, parser.getValue(e, KEY_IMAGE));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					
		
					
				feedList.add(map);
				}
				}
	   		
	   		ListView FeedView =(ListView) findViewById(R.id.feedlist);
			//list.setSmoothScrollbarEnabled(true);
			// Getting adapter by passing xml data ArrayList
		FeedAdapter feedadapter;
		feedadapter = new FeedAdapter(home.this, feedList);    
			
			
		FeedView.setAdapter(feedadapter);
		
		
		findViewById(R.id.progressBar).setVisibility(View.GONE);
		findViewById(R.id.feedlist).setVisibility(View.VISIBLE);
		findViewById(R.id.blog).setVisibility(View.VISIBLE);
		
		
		
		
		
FeedView.setOnItemClickListener(new OnItemClickListener() {
		  	
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							
				// findViewById(R.id.subgrid).setVisibility(View.GONE);
				 
				// findViewById(R.id.subgrid).setVisibility(View.GONE);
				 
				if(feedURL.contains("feeds.php")){
					
					Intent intent = new Intent(home.this, userpostprev.class);
					 intent.putExtra("post_id", feedList.get(+position).get(KEY_ID));
					 intent.putExtra("name", feedList.get(+position).get(KEY_TITLE));
					 intent.putExtra("time", feedList.get(+position).get(KEY_TIME));
					 intent.putExtra("user_id", feedList.get(+position).get(KEY_LINK));
					 intent.putExtra("propic", feedList.get(+position).get(KEY_DETAILS));
		        		intent.putExtra("caption", feedList.get(+position).get(KEY_SDETAILS));
		        		intent.putExtra("piclink", feedList.get(+position).get(KEY_THUMB_URL));
		        		
		        		//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
		        		startActivity(intent);
					
				}else{
				 Intent intent = new Intent(home.this, postprev.class);
				 intent.putExtra("post_id", feedList.get(+position).get(KEY_ID));
				 intent.putExtra("name", feedList.get(+position).get(KEY_TITLE));
				 intent.putExtra("time", feedList.get(+position).get(KEY_TIME));
				 intent.putExtra("details", feedList.get(+position).get(KEY_DETAILS));
				 intent.putExtra("propic", feedList.get(+position).get(KEY_THUMB_URL));
	        	intent.putExtra("link", feedList.get(+position).get(KEY_LINK));
	        		intent.putExtra("piclink", feedList.get(+position).get(KEY_IMAGE));
	        		startActivity(intent);
				}
			}
		});	
		
		
		
		
		
			}catch (Exception d) {
			    //Log.d("Exception", e.getMessage());
				feedTask.cancel(true);
			    ListView FeedView =(ListView) findViewById(R.id.feedlist);
				FeedView.setAdapter(null);
			}
		}	
	
	
	}
	
	
	
	
 
	
	
	
	
	
	
	private  class DownloadTask extends AsyncTask<String, Void, Document>{


	   	@Override
	       protected void onPreExecute() {
	           super.onPreExecute();
			   
	        	if(!isNetworkAvailable(home.this)){
       			Toast.makeText(getApplicationContext(), "Please check your internet connection...", Toast.LENGTH_LONG).show();
				}
	        	
	        	
	        	findViewById(R.id.newsfeed).setVisibility(View.GONE);
	        	findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
			}
			
	   	Document doc = null;
			protected Document doInBackground(String... args) {
				
		    	
		    	
		    	try{
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(url); // getting XML from URL
				doc = parser.getDomElement(xml); // getting DOM element
			}catch (Exception e) {
			   
			}
				 return doc;
			}
			
			@Override
	        protected void onPostExecute(Document doc) {
				try{
					
					
			        
					 ActionBar bar = getActionBar();
					 bar.show();
					
					
				dataList.clear();
			
	   		XMLParser parser = new XMLParser();
	   		NodeList nl = doc.getElementsByTagName(KEY_SONG);
			
			for (int i = 0; i < nl.getLength(); i++) {
				
				HashMap<String, String> map = new HashMap<String, String>();
				Element e = (Element) nl.item(i);
				
				
				if(parser.getValue(e, KEY_NAME).contentEquals("line")){
					map.put(KEY_NAME, "line");
				}else{
					
					map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
				}
				
				mydb = new DBHelper(home.this);
				Cursor c = mydb.getData(parser.getValue(e, KEY_NAME), url);
				//c.moveToFirst();
				int cnt = c.getCount();
				c.close();
				if(cnt > 0){
					Cursor d = mydb.getData(parser.getValue(e, KEY_NAME), url);
					d.moveToFirst();
			     	mydb.updateContact(parser.getValue(e, KEY_NAME), Double.parseDouble(parser.getValue(e, KEY_ra_deg)), Double.parseDouble(parser.getValue(e, KEY_dec_deg)), parser.getValue(e, KEY_today), parser.getValue(e, KEY_icon), parser.getValue(e, KEY_icon_orbit), parser.getValue(e, KEY_alter_name), parser.getValue(e, KEY_type), parser.getValue(e, KEY_yesterday), parser.getValue(e, KEY_tenday), parser.getValue(e, KEY_mean), parser.getValue(e, KEY_peak), parser.getValue(e, KEY_days), parser.getValue(e, KEY_last_day), parser.getValue(e, KEY_time), "false", url);
				}else{
				//Toast.makeText(getApplicationContext(), catName, 1500).show();	
				mydb.insertContact(parser.getValue(e, KEY_NAME), Double.parseDouble(parser.getValue(e, KEY_ra_deg)), Double.parseDouble(parser.getValue(e, KEY_dec_deg)), parser.getValue(e, KEY_today), parser.getValue(e, KEY_icon), parser.getValue(e, KEY_icon_orbit), parser.getValue(e, KEY_alter_name), parser.getValue(e, KEY_type), parser.getValue(e, KEY_yesterday), parser.getValue(e, KEY_tenday), parser.getValue(e, KEY_mean), parser.getValue(e, KEY_peak), parser.getValue(e, KEY_days), parser.getValue(e, KEY_last_day), parser.getValue(e, KEY_time), "false", url);
				}
				
					map.put(KEY_icon, parser.getValue(e, KEY_icon));
					map.put(KEY_icon_orbit, parser.getValue(e, KEY_icon_orbit));
					map.put(KEY_ra_deg, parser.getValue(e, KEY_ra_deg));
					map.put(KEY_dec_deg, parser.getValue(e, KEY_dec_deg));
					map.put(KEY_alter_name, parser.getValue(e, KEY_alter_name));
					map.put(KEY_type, parser.getValue(e, KEY_type));
					map.put(KEY_today, parser.getValue(e, KEY_today));
					map.put(KEY_yesterday, parser.getValue(e, KEY_yesterday));
					map.put(KEY_tenday, parser.getValue(e, KEY_tenday));
					map.put(KEY_mean, parser.getValue(e, KEY_mean));
					map.put(KEY_peak, parser.getValue(e, KEY_peak));
					map.put(KEY_days, parser.getValue(e, KEY_days));
					map.put(KEY_last_day, parser.getValue(e, KEY_last_day));
					map.put(KEY_time, parser.getValue(e, KEY_time));
					
					TextView update_time = (TextView) findViewById(R.id.update_time); // duration
					update_time.setText(parser.getValue(e, KEY_time));
				
				dataList.add(map);
				}
				
			findViewById(R.id.newsfeed).setVisibility(View.VISIBLE);
			findViewById(R.id.progressBar).setVisibility(View.GONE);
			findViewById(R.id.list).setVisibility(View.VISIBLE);
			list =(ListView) findViewById(R.id.list);
			adapter=new LazyAdapter(home.this, dataList);        
	        list.setAdapter(adapter);
	        
	        
	        listClicker();
	        
	       
	        }catch (Exception e) {
				    //Log.d("Exception", e.getMessage());
				    mTask.cancel(true);
				    list.setAdapter(null);
				    //Toast.makeText(getApplicationContext(), "There is an error to load news.", 1500).show();
				}

			// =================================
			
	  
			}

			
			
		}
 
	
	

 
	
	
	public static boolean isNetworkAvailable(Context context) 
	{
	    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
	

	
	

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
       // actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#1Affffff")));
        
        
        setContentView(R.layout.home);
        
        
        
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
     
        
      //  appImage = (ImageView)findViewById(android.R.id.home);
        
      //  mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
      //  mSlidingPanel.setPanelSlideListener(panelListener);
      //  mSlidingPanel.setParallaxDistance(200);


      
        
        
 ActionBar bar = getActionBar();
 
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           bar.setTitle("Blog _ VAT Checker");
           bar.hide();
           
           
           
           

           
           
           Intent intent = getIntent();
           String urlText = intent.getStringExtra("url");
           String openType = intent.getStringExtra("openType");
           
           
           
           if(urlText.length()== 9){
        	   url = "userblog";
        	   feedURL = "http://enamelbd.org/apps/nasa/feeds.php";
        	   
        	   
        	   if (isNetworkAvailable(this))
          		{
           	   feedTask = new DownloadFeed();
         		 feedTask.execute();
          		}else{
          			Toast.makeText(getApplicationContext(), "No internet connection, Showing data from last session.", Toast.LENGTH_LONG).show();
       			
       			//loadData();
       		}
       		
       		
           }else if(urlText.length()== 8){
        	   url = urlText;
        	   
        	   
        	   if (isNetworkAvailable(this))
        		{
        		   loadData();
        		}else{
        			Toast.makeText(getApplicationContext(), "No internet connection, Showing data from last session.", Toast.LENGTH_LONG).show();
        			loadData();
        		}
       		
       		
           }else if(urlText.length()== 7){
        	  // url = urlText;
        	   loadData();
        	   String str_source_name = intent.getStringExtra("str_source_name");
               String str_ra_deg = intent.getStringExtra("str_ra_deg");
       		String str_dec_deg = intent.getStringExtra("str_dec_deg");
       		String str_today = intent.getStringExtra("str_today");
       		String str_icon = intent.getStringExtra("str_icon");
       		String str_icon_orbit = intent.getStringExtra("str_icon_orbit");
       		String str_alter_name = intent.getStringExtra("str_alter_name");
       		String str_type = intent.getStringExtra("str_type");
       		String str_yesterday = intent.getStringExtra("str_yesterday");
       		String str_tenday = intent.getStringExtra("str_tenday");
       		String str_mean = intent.getStringExtra("str_mean");
       		String str_peak = intent.getStringExtra("str_peak");
       		String str_days = intent.getStringExtra("str_days");
       		String str_last_day = intent.getStringExtra("str_last_day");
       		String str_time = intent.getStringExtra("str_time");
       		String str_qpeak = intent.getStringExtra("str_peak");
        	   
        	 listClickerObject(str_source_name, str_ra_deg, str_dec_deg, str_alter_name, str_type, str_today, str_yesterday, str_tenday, str_mean, str_qpeak, str_days, str_last_day, str_icon, str_icon_orbit, str_time);

       		
           }else if(urlText.length()>9){
        	   url = urlText;
        	   
        	   
        	   if (isNetworkAvailable(this))
        		{
        	    mTask = new DownloadTask();
       		mTask.execute();
        		}else{
        			Toast.makeText(getApplicationContext(), "No internet connection, Showing data from last session.", Toast.LENGTH_LONG).show();
        			loadData();
        		}
       		
       		
           }else{
        	   
        	   if (isNetworkAvailable(this))
       		{
        	   feedTask = new DownloadFeed();
      		 feedTask.execute();
       		}else{
       			Toast.makeText(getApplicationContext(), "No internet connection, Showing data from last session.", Toast.LENGTH_LONG).show();
    			
    			//loadData();
    		}
         	  
           }
        	   
           
           
          
           
           
           
           
           
           
           
           CheckBox open_all_box = (CheckBox)findViewById(R.id.open_all_box);
           
           SharedPreferences mypreferences = getSharedPreferences("nasa", Context.MODE_PRIVATE);
           String login = mypreferences.getString("showAll", "");
           if(login.contentEquals("true")){
        	   open_all_box.setChecked(true);
           }else{
        	   open_all_box.setChecked(false);
           }
           
           
           
           
           
           LinearLayout item_ra_deg_container = (LinearLayout)findViewById(R.id.item_ra_deg_container);
           item_ra_deg_container.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
            	   
            	   v.setBackgroundColor(Color.parseColor("#6F4E37"));
            	   findViewById(R.id.item_dec_deg_container).setBackgroundColor(Color.parseColor("#493D26"));
            	   findViewById(R.id.item_name_container).setBackgroundColor(Color.parseColor("#493D26"));
            	   shortData("ra");
            	   
               } // end onClick
           });
           
           
           LinearLayout item_dec_deg_container = (LinearLayout)findViewById(R.id.item_dec_deg_container);
           item_dec_deg_container.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
            	   
            	   v.setBackgroundColor(Color.parseColor("#6F4E37"));
            	   findViewById(R.id.item_ra_deg_container).setBackgroundColor(Color.parseColor("#493D26"));
            	   findViewById(R.id.item_name_container).setBackgroundColor(Color.parseColor("#493D26"));
            	   
            	   shortData("dec");
            	   
               } // end onClick
           });
            
           
           LinearLayout item_name_container = (LinearLayout)findViewById(R.id.item_name_container);
           item_name_container.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
            	   
            	   
            	   v.setBackgroundColor(Color.parseColor("#6F4E37"));
            	   findViewById(R.id.item_ra_deg_container).setBackgroundColor(Color.parseColor("#493D26"));
            	   findViewById(R.id.item_dec_deg_container).setBackgroundColor(Color.parseColor("#493D26"));
            	  
            	   shortData("name");
            	   
               } // end onClick
           });
           
           
           
           
           
           
          
       
           
           open_all_box.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
            	   
            	   SharedPreferences mypreferences = getApplicationContext().getSharedPreferences("nasa", Context.MODE_PRIVATE);
      	    		SharedPreferences.Editor editor = mypreferences.edit();
      	        	
            	   
            	   CheckBox open_all_box = (CheckBox)findViewById(R.id.open_all_box);
                   if(open_all_box.isChecked()){
                	   editor.putString("showAll", "true");
                   }else{
                	   editor.putString("showAll", "false");
                   }
               	
   	        	editor.commit();
               

list =(ListView) findViewById(R.id.list);
list.invalidateViews();
             		//Toast.makeText(context, v.getTag().toString(), 1000).show();
               } // end onClick
           });
           
           
           
           
           
           mydb = new DBHelper(home.this);
           int numOfRows = mydb.numberOfRows();
           // Toast.makeText(getApplicationContext(), ""+numOfRows, 1000).show();
           
           if(numOfRows < 5){
            //Toast.makeText(getApplicationContext(), ""+numOfRows, 1000).show();
           }
           
           
           
 
    }
    
    
    
    
    
    
    
    @SuppressLint("NewApi") @Override
    public void onBackPressed() {
    	
    	
    	if ((findViewById(R.id.details).getVisibility() == View.GONE)) {
       		
       	finish();
       		
       	
       	}
    	
   	 
   	if ((findViewById(R.id.details).getVisibility() == View.VISIBLE)) {
   		
   		findViewById(R.id.details).setVisibility(View.GONE);
    	findViewById(R.id.newsfeed).setVisibility(View.VISIBLE);
    	
   		
   	
   	}
   	
   	
   	

   	
    }
    
    
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case android.R.id.home:
        	
        	if ((findViewById(R.id.details).getVisibility() == View.GONE)){
        		
            finish();
        	}else{
        		 findViewById(R.id.details).setVisibility(View.GONE);
        		 findViewById(R.id.newsfeed).setVisibility(View.VISIBLE);
        	}
            break;
            
            
        case R.id.action_addpost:
       	 
       	 
       	 Intent intentxx = new Intent(home.this, addpost.class);
   			startActivity(intentxx);
       	 
       	 
       	 break;
            
        
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    

    
    /////////////////////////////////////////////////////////////////
    
    
    @Override
     public void onStart() {
       super.onStart();
       EasyTracker.getInstance(this).activityStart(this);  
     }

     @Override
     public void onStop() {
       super.onStop();
       EasyTracker.getInstance(this).activityStop(this);  
     }


   //////////////////////////////////////////////////////////////
    
    
}
