package com.enamelbd.vatchecker;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class userpostprev extends Activity  implements OnClickListener  {
	
	public DetailsImageLoader imageLoader; 
	private TextView namePrev, like_button;
	private ImageView profilePicPrev, feedImage1Prev;
	private String post_id, user_id, piclink, caption;
	
	
	
	
	
	
	
	 
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.userpostprev);
	        
	        
	        
	     // ===================================================================
	     	  ActionBar bar = getActionBar();
	     	  
	     	 // bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#348017")));
	     	 //bar.setIcon(
	     			   //new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
	     	 if(android.os.Build.VERSION.SDK_INT > 13) {
	     	 bar.setIcon(getResources().getDrawable(
	                 R.drawable.ic_action_web_site));
	     	 }
	        bar.setTitle("News Feed");
	     	  // ===========================================================
	        
	       
			  
				Intent intent = getIntent();

				// 2. get message value from intent
				post_id = intent.getStringExtra("post_id");
				String name = intent.getStringExtra("name");
				String time = intent.getStringExtra("time");
				user_id = intent.getStringExtra("user_id");
				String propic = intent.getStringExtra("propic");
				caption = intent.getStringExtra("caption");
				piclink = intent.getStringExtra("piclink");
				//Toast.makeText(getApplication(), title + piclink, 3000).show();
				
				
				
				
				  namePrev = (TextView) findViewById(R.id.namePrev);
				  TextView timestampPrev = (TextView) findViewById(R.id.timestampPrev);
				  TextView txtStatusMsgPrev = (TextView) findViewById(R.id.txtStatusMsgPrev);
				profilePicPrev = (ImageView) findViewById(R.id.profilePicPrev);
				feedImage1Prev = (ImageView) findViewById(R.id.feedImage1Prev);
				
				namePrev.setText(name);
				
				timestampPrev.setText(time);
				txtStatusMsgPrev.setText(caption);
				
				imageLoader=new DetailsImageLoader(getApplicationContext());
				 imageLoader.DisplayImage(propic, profilePicPrev);
				 
				 if(piclink.length() > 5){
				 imageLoader.DisplayImage(piclink, feedImage1Prev);
				 }else{
					 findViewById(R.id.feedImage1Prev).setVisibility(View.GONE); 
					 
				 }
				 
				
	        
	 }     

	 @Override
		public void onClick(View arg0) {
		 
		 
		 
		 
			
			if (arg0 == feedImage1Prev) {
				
	        		// Intent intent = new Intent(postprev.this, photoprev.class);
		        		//intent.putExtra("title", caption);
		        		//intent.putExtra("piclink", piclink);
		        		
		        		//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
		        		//startActivity(intent);
				
			}
	 }
		
	



}
