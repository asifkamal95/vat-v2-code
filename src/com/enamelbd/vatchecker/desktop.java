package com.enamelbd.vatchecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import android.app.AlertDialog;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class desktop extends Activity {
	
	InterstitialAd  mInterstitialAd;
	
	EditText ed;
    static String KEY_SMS = "";
    String respons;
    
    ProgressBar pb;
    TextView fTv;
    
    ImageView whatIsBIN;
	
	private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
	private long mBackPressed;
	
	
	
	@SuppressLint("NewApi") public void hideDesktop_home(){
		
		
		 ActionBar bar = getActionBar();
    	 if(android.os.Build.VERSION.SDK_INT > 13) {
         	 bar.setIcon(getResources().getDrawable(
                     R.drawable.ic_action_back));
         		 }
		
    	RelativeLayout desktop_home=(RelativeLayout) findViewById(R.id.desktop_home);
		 	TranslateAnimation animate = new TranslateAnimation(0,desktop_home.getWidth(),0,0);
    	animate.setDuration(500);
    	animate.setFillAfter(true);
    	desktop_home.startAnimation(animate);
    	desktop_home.setVisibility(View.GONE);
    	
    	
	}
	
	
	
	
	public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}
	


    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
       // actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#1Affffff")));
        
        setContentView(R.layout.desktop);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        fTv = (TextView) findViewById(R.id.fTv);
        whatIsBIN = (ImageView) findViewById(R.id.whatIsBIN);
        
        findViewById(R.id.progressBar1).setVisibility(View.GONE);
		findViewById(R.id.fTv).setVisibility(View.VISIBLE);
		
		whatIsBIN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Business Identification Number (BIN)\nUsually 11 Digit.. Example BIN: 18011054205", Toast.LENGTH_LONG).show();			
			}
		});
        
        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        mInterstitialAd = new InterstitialAd(this);  // vat checker// jubayer0511@gmail.com
        mInterstitialAd.setAdUnitId("ca-app-pub-8248033864499718/2410189980");
        requestNewInterstitial();

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } 
        
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        
        
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
    
    	RelativeLayout desktop_about=(RelativeLayout) findViewById(R.id.desktop_about);
			
    	//desktop_about.setVisibility(View.VISIBLE);
    	
       //	TranslateAnimation animate = new TranslateAnimation(0,desktop_about.getWidth(),0,0);
       //	animate.setDuration(10);
       //	animate.setFillAfter(true);
       //	desktop_about.startAnimation(animate);
        
        
        
        ActionBar bar = getActionBar();
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           bar.setTitle("VAT Checker");
           
           
           
           Button animb1=(Button) findViewById(R.id.Button01);
    		 Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.animb1);
    		 animb1.startAnimation(animation1);
    		
    		 Button animb2=(Button) findViewById(R.id.Button02);
    		 Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.animb2);
    		 animb2.startAnimation(animation2);
    		 
    		 Button animb3=(Button) findViewById(R.id.Button03);
    		 Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.animb3);
    		 animb3.startAnimation(animation3);
    		 
    		 Button animb4=(Button) findViewById(R.id.Button04);
    		 Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.animb4);
    		 animb4.startAnimation(animation4);
    		 
    		 
    		 
           
         
   					   
  
				 Button circle1 =(Button) findViewById(R.id.Button01);
		   	        Spannable button_circle_1 = new SpannableString(".\nComplain");
		   	  button_circle_1.setSpan(new ImageSpan(getApplicationContext(), R.drawable.ic_action_event,      
		   			     ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		   circle1.setText(button_circle_1);  
   					    
		  
   					 Button circle2 =(Button) findViewById(R.id.Button02);
 		   	        Spannable button_circle_2 = new SpannableString(".\nBlog");
 		   	  button_circle_2.setSpan(new ImageSpan(getApplicationContext(), R.drawable.ic_action_chat,      
 		   			     ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 		   circle2.setText(button_circle_2);   
					    
   					    
   					    
   					    
           
           
           
   					 Button circle3 =(Button) findViewById(R.id.Button03);
    		   	        Spannable button_circle_3 = new SpannableString(".\nAbout");
    		   	  button_circle_3.setSpan(new ImageSpan(getApplicationContext(), R.drawable.ic_action_about,      
    		   			     ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    		   circle3.setText(button_circle_3);    
   					    
   					    
   					    
   					 Button circle4 =(Button) findViewById(R.id.Button04);
   		   	        Spannable button_circle_4 = new SpannableString(".\nFAQ");
   		   	  button_circle_4.setSpan(new ImageSpan(getApplicationContext(), R.drawable.ic_action_important,      
   		   			     ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
   		   circle4.setText(button_circle_4);
           
           
           
           
           
           Button circle_button1 =(Button) findViewById(R.id.Button01);
           circle_button1.setOnClickListener(onClickListener);
           
           Button circle_button2 =(Button) findViewById(R.id.Button02);
           circle_button2.setOnClickListener(onClickListener);
           
           Button circle_button3 =(Button) findViewById(R.id.Button03);
           circle_button3.setOnClickListener(onClickListener);
           
           
           Button circle_button4 =(Button) findViewById(R.id.Button04);
           circle_button4.setOnClickListener(onClickListener);
           
           Button clearcurves =(Button) findViewById(R.id.clearcurves);
           clearcurves.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "DSE Alarm", Toast.LENGTH_SHORT).show();
				dseApp();
			}
		});
           
           Button clearreset =(Button) findViewById(R.id.clearreset);
           
           clearreset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            //case R.id.clearreset:
            	
            	//Toast.makeText(getApplicationContext(), "Rate Us", Toast.LENGTH_SHORT).show();
            	RateUs();
            	
           		//break;	
			}
		});
           
           
           Button  search =(Button) findViewById(R.id.search);
           search.setOnClickListener(onClickListener);
           
         ed =(EditText) findViewById(R.id.editText);
         
           
           
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private OnClickListener onClickListener = new OnClickListener() {
        @SuppressLint("SetJavaScriptEnabled")
   	@Override
        public void onClick(View v) {
        	
        	
        	
        	 if ((findViewById(R.id.desktop_home).getVisibility() == View.GONE)) {
        		return; 
        	 }
       	 
   			
            switch(v.getId()){
            	
	        	// voda
            case R.id.search:
            	
          KEY_SMS = ed.getText().toString();
          
          if (KEY_SMS.length() >= 8 ){
        	  
        	  if(isNetworkAvailable(desktop.this)){
                  new MyAsyncTask().execute(KEY_SMS);
                  
          		 findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
          			findViewById(R.id.fTv).setVisibility(View.GONE);  
        	  } else{
        		  Toast.makeText(getApplication(),"Please check your internet connection and try again!", Toast.LENGTH_LONG).show();  
        	  }
        	  
        	 
          } else{
        	  Toast.makeText(getApplication(),"Please enter a valid BIN number and try again!", Toast.LENGTH_LONG).show();
          }
           
			
			
            	break;
	        	
            case R.id.Button01:
            	
          		Intent i = new Intent(desktop.this, Complain.class);
	    		i.putExtra("bin", "");
	    		startActivity(i);

	        	break;
	        	 
	        	
	        	
            case R.id.Button02:
            	Intent intent_blog = new Intent(desktop.this, home.class);
            	intent_blog.putExtra("url", "news");
				//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
	        		startActivity(intent_blog);

	        	break;
	        	 
	        	
            case R.id.Button03:
            	
            	
                   
            	
            //	Toast.makeText(getApplication(), "egdfgdf", 3000).show();
            	hideDesktop_home();
            
            	RelativeLayout desktop_about=(RelativeLayout) findViewById(R.id.desktop_about);
      			Animation out = AnimationUtils.makeInAnimation(desktop.this, true);
            	out.setDuration(500);
            	desktop_about.startAnimation(out);
            	desktop_about.setVisibility(View.VISIBLE);
            	
         
       		break;
       		
       		
       		
            case R.id.Button04:
                //	Toast.makeText(getApplication(), "egdfgdf", 3000).show();
                	
            	Intent faq = new Intent(desktop.this, FAQ.class);
				//Toast.makeText(getApplication(), catsongsList.get(+position).get(KEY_LINK), 3000).show();
	        		startActivity(faq);

             
           		break;


            
            
               	 
            }

      }
   };
    
    
    
   
   
   
   
   @SuppressLint("NewApi") public void onBackPressed()
   
   
   {
	   
	   ActionBar bar = getActionBar();
  	 if(android.os.Build.VERSION.SDK_INT > 13) {
       	 bar.setIcon(getResources().getDrawable(
                   R.drawable.ic_action_web_site));
       		 }
	   
	   if ((findViewById(R.id.desktop_home).getVisibility() == View.VISIBLE)) {
		   if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
	       { 

	   		 if (mInterstitialAd.isLoaded()) {
                 mInterstitialAd.show();
             }
	           super.onBackPressed(); 
	           return;
	       }
	       else { Toast.makeText(getBaseContext(), "Press again to Exit", Toast.LENGTH_SHORT).show(); 
	       }
	   		mBackPressed = System.currentTimeMillis();  	
	   }else{
		   
		   
		   if ((findViewById(R.id.desktop_about).getVisibility() == View.VISIBLE)) {
		   RelativeLayout desktop_about=(RelativeLayout) findViewById(R.id.desktop_about);
 			Animation out = AnimationUtils.makeOutAnimation(desktop.this, false);
       	out.setDuration(500);
       	desktop_about.startAnimation(out);
       	desktop_about.setVisibility(View.GONE);
		   }
       	
       	
		   if ((findViewById(R.id.desktop_setting).getVisibility() == View.VISIBLE)) {
        RelativeLayout desktop_setting=(RelativeLayout) findViewById(R.id.desktop_setting);
			Animation out_setting = AnimationUtils.makeOutAnimation(desktop.this, false);
			out_setting.setDuration(500);
   	desktop_setting.startAnimation(out_setting);
   	desktop_setting.setVisibility(View.GONE);
		   }
       	
       	
       	
       	RelativeLayout desktop_home=(RelativeLayout) findViewById(R.id.desktop_home);
			Animation in = AnimationUtils.makeInAnimation(desktop.this, false);
   	in.setDuration(500);
   	desktop_home.startAnimation(in);
   	desktop_home.setVisibility(View.VISIBLE);

   	
	   }
 
   
   
   	
   	}
   

   
   
   
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.desktop, menu);
       
       
       MenuItem item2 = menu.findItem(R.id.action_setting);
           item2.setVisible(true);
       
   
       
       return true;
   }
   //////////  Google Add  //////////////////////////
   private void requestNewInterstitial() {
       AdRequest adRequest = new AdRequest.Builder()
                 .addTestDevice("YOUR_DEVICE_HASH")
                 .build();
       
       
       

       mInterstitialAd.loadAd(adRequest);
   }
   
   
   @SuppressLint("NewApi") @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       // TODO Auto-generated method stub
       switch (item.getItemId()) {
       
       case android.R.id.home:
    	   
    	   
    	   ActionBar bar = getActionBar();
      	 if(android.os.Build.VERSION.SDK_INT > 13) {
           	 bar.setIcon(getResources().getDrawable(
                       R.drawable.ic_action_web_site));
           		 }
    	   
       	
    	   if ((findViewById(R.id.desktop_about).getVisibility() == View.VISIBLE)) {
    		   RelativeLayout desktop_about=(RelativeLayout) findViewById(R.id.desktop_about);
     			Animation out = AnimationUtils.makeOutAnimation(desktop.this, false);
           	out.setDuration(500);
           	desktop_about.startAnimation(out);
           	desktop_about.setVisibility(View.GONE);
    		   }
           	
           	
    		   if ((findViewById(R.id.desktop_setting).getVisibility() == View.VISIBLE)) {
            RelativeLayout desktop_setting=(RelativeLayout) findViewById(R.id.desktop_setting);
    			Animation out_setting = AnimationUtils.makeOutAnimation(desktop.this, false);
    			out_setting.setDuration(500);
       	desktop_setting.startAnimation(out_setting);
       	desktop_setting.setVisibility(View.GONE);
    		   }
           	
           	
    		   if ((findViewById(R.id.desktop_home).getVisibility() == View.GONE)) {
    	           
           	RelativeLayout desktop_home=(RelativeLayout) findViewById(R.id.desktop_home);
    			Animation in = AnimationUtils.makeInAnimation(desktop.this, false);
       	in.setDuration(500);
       	desktop_home.startAnimation(in);
       	desktop_home.setVisibility(View.VISIBLE);
    		   }
           break;
           
           
           
           
       case R.id.action_setting:
    	   
    	   if ((findViewById(R.id.desktop_home).getVisibility() == View.VISIBLE)) {
       		
    		hideDesktop_home();
            
        	RelativeLayout desktop_setting =(RelativeLayout) findViewById(R.id.desktop_setting);
  			Animation out_setting = AnimationUtils.makeInAnimation(desktop.this, true);
  			out_setting.setDuration(500);
        	desktop_setting.startAnimation(out_setting);
        	desktop_setting.setVisibility(View.VISIBLE);
    	   }
    	   
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
    
    ///// sms
    
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		 
		@Override
		protected Double doInBackground(String... params) {
			// TODO Auto-generated method stub
			postData(params[0]);
			return null;
		}
 
		protected void onPostExecute(Double result){
			//pb.setVisibility(View.GONE);
			//Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
			
			 findViewById(R.id.progressBar1).setVisibility(View.GONE);
				findViewById(R.id.fTv).setVisibility(View.VISIBLE);
			
			String ops1 = android.text.Html.fromHtml(respons).toString();
			
			String[] separated = ops1.split("Enter BIN");
			
			ops1 = separated[1]; // this will contain " they taste good"
			
			ops1 = ops1.replaceAll("\\s+", " ");
			
			if (ops1.contains("No Result Found")){
				
				AlertDialog alertDialog = new AlertDialog.Builder(desktop.this).create();
				alertDialog.setTitle(KEY_SMS+" NOT found!");
				alertDialog.setMessage("এই প্রতিষ্ঠানটির ক্ষেত্রে ভ্যাটের সরকারী অনুমোদন পাওয়া যায়নি।");
				alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));	
				
				alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					    new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					            dialog.dismiss();
					        }
					    });
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "অভিযোগ",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				            dialog.dismiss();
				            
				    		Intent i = new Intent(desktop.this, Complain.class);
				    		i.putExtra("bin", KEY_SMS);
				    		startActivity(i);
				        }
				    });
				alertDialog.show();		
			} 
			// Yes this company has a lisense
			else{
				
				String[] separated2 = ops1.split("Address:");
				
				String name = separated2[0]; 
				String add = separated2[1]; 
				
				
				AlertDialog alertDialog = new AlertDialog.Builder(desktop.this).create();
				alertDialog.setTitle(KEY_SMS+" found!");
				alertDialog.setMessage(name+"\n\nAddress:"+add);
				alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, " - OK -",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) {
				            dialog.dismiss();
				        }
				    });
				alertDialog.show();	
			}
			
		
			
			
		}
		protected void onProgressUpdate(Integer... progress){
			//pb.setProgress(progress[0]);
				
		}
 
		public void postData(String valueIWantToSend) {
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://www.nbr.gov.bd/getbinfield.php");
 
			try {	
					
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("txtSearch", KEY_SMS));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				
				respons = EntityUtils.toString(response.getEntity());
				
 
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
 
	}
    
  ///////////////////////
	
	public static boolean isNetworkAvailable(Context context) 
	{
	    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
	////////////////////
	
	public void RateUs() {
		 Intent intent = new Intent(Intent.ACTION_VIEW);
		    //Try Google play
	    SharedPreferences mypreferences = getApplicationContext().getSharedPreferences("antirox", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = mypreferences.edit();
		  	editor.putString("rate", "true");
		  	editor.commit();
		  	
		  	
		    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
		    if (MyStartActivity(intent) == false) {
		        //Market (Google play) app seems not installed, let's try to open a webbrowser
		 	  	
		        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
		        if (MyStartActivity(intent) == false) {
		            //Well if this also fails, we have run out of options, inform the user.
		            Toast.makeText(this, "Could not open Google Play Store, Please install the Market app.", Toast.LENGTH_SHORT).show();
		        }
		    }
		    
	   }
	////////////////
	
	public void dseApp() {
		 Intent intent = new Intent(Intent.ACTION_VIEW);
		    //Try Google play
	    SharedPreferences mypreferences = getApplicationContext().getSharedPreferences("antirox", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = mypreferences.edit();
		  	editor.putString("rate", "true");
		  	editor.commit();
		  	
		  	
		    intent.setData(Uri.parse("market://details?id=" + "dseapp.dse.alarm"));
		    if (MyStartActivity(intent) == false) {
		        //Market (Google play) app seems not installed, let's try to open a webbrowser
		 	  	
		        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "dseapp.dse.alarm"));
		        if (MyStartActivity(intent) == false) {
		            //Well if this also fails, we have run out of options, inform the user.
		            Toast.makeText(this, "Could not open Google Play Store, Please install the Market app.", Toast.LENGTH_SHORT).show();
		        }
		    }
		    
	   }
	////////////////
	private boolean MyStartActivity(Intent aIntent) {
	    try
	    {
	        startActivity(aIntent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}
	/////////////////////
	
	
	
    //////////////////////////////////////
}
