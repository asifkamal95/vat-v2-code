package com.enamelbd.vatchecker;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.google.analytics.tracking.android.EasyTracker;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class postprev extends Activity{
	
	public DetailsImageLoader imageLoader; 
	private TextView namePrev, like_button;
	private ImageView profilePicPrev, feedImage1Prev;
	private String link;
	
	private DownloadDetails detailsTask = new DownloadDetails();
	
	private  class DownloadDetails extends AsyncTask<String, Void, String>{


	   	@Override
	       protected void onPreExecute() {
	   		
	           super.onPreExecute();
	           findViewById(R.id.progressBarDetails).setVisibility(View.VISIBLE);
				
	           
	   	}
			
			
	   	
	   	

			protected String doInBackground(String... args) {
				
				
				String xml = null;

				try {
					// defaultHttpClient
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://enamelbd.org/apps/nasa/view.php?url="+link);

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					xml = EntityUtils.toString(httpEntity, "UTF-8");
					
		
				}catch (Exception e) {
				    //Log.d("Exception", e.getMessage());
					detailsTask.cancel(true);
					}
			 
				
			    return  xml;
			}
			
			
			
			
			@Override
	        protected void onPostExecute(String xml) {
				
				
				
	   		
				((TextView) findViewById(R.id.txtStatusMsgPrev)).setText(Html.fromHtml(xml));
				findViewById(R.id.progressBarDetails).setVisibility(View.GONE);
				
			
	   		 
	   		 // =========================================
			}

			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	 
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.postprev);
	        
	        ActionBar bar = getActionBar();
	      	 if(android.os.Build.VERSION.SDK_INT > 13) {
	           	 bar.setIcon(getResources().getDrawable(
	                       R.drawable.ic_action_web_site));
	           		 }
	       
			  
				Intent intent = getIntent();

				// 2. get message value from intent
				String name = intent.getStringExtra("name");
				String time = intent.getStringExtra("time");
				String details = intent.getStringExtra("details");
				String propic = intent.getStringExtra("propic");
				String piclink = intent.getStringExtra("piclink");
				link = intent.getStringExtra("link");
				//Toast.makeText(getApplication(), title + piclink, 3000).show();
				
				detailsTask = new DownloadDetails();
				detailsTask.execute();
			
			
				
				
				  namePrev = (TextView) findViewById(R.id.namePrev);
				  TextView timestampPrev = (TextView) findViewById(R.id.timestampPrev);
				  TextView txtStatusMsgPrev = (TextView) findViewById(R.id.txtStatusMsgPrev);
				profilePicPrev = (ImageView) findViewById(R.id.profilePicPrev);
				feedImage1Prev = (ImageView) findViewById(R.id.feedImage1Prev);
				
				namePrev.setText(name);
				
				timestampPrev.setText(time);
				//txtStatusMsgPrev.setText(details);
				txtStatusMsgPrev.setMovementMethod(LinkMovementMethod.getInstance());
				
				imageLoader=new DetailsImageLoader(getApplicationContext());
				 imageLoader.DisplayImage(propic, profilePicPrev);
				 
				 if(piclink.length() > 5){
				 imageLoader.DisplayImage(piclink, feedImage1Prev);
				 }else{
					 findViewById(R.id.feedImage1Prev).setVisibility(View.GONE); 
					 
				 }
				 

				 
	        
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
