package com.enamelbd.vatchecker;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.enamelbd.vatchecker.TouchImageView.OnTouchImageViewListener;
import com.google.analytics.tracking.android.EasyTracker;
public class imageView extends Activity {
	
	private TouchImageView image;
	private TextView scrollPositionTextView;
	private TextView zoomedRectTextView;
	private TextView currentZoomTextView;
	private DecimalFormat df;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
		  
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
      
		
		setContentView(R.layout.imageview);
		
ActionBar bar = getActionBar();
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           bar.setTitle("Nasa Monitor");
           
		//
		// DecimalFormat rounds to 2 decimal places.
		//
		
		
		
		String imgFile = Environment.getExternalStorageDirectory() + "/0sdzbfsdv.png";


        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile);


        image = (TouchImageView) findViewById(R.id.img);
        image.setImageBitmap(myBitmap);
		
	
		image = (TouchImageView) findViewById(R.id.img);
		
		//
		// Set the OnTouchImageViewListener which updates edit texts
		// with zoom and scroll diagnostics.
		//
		image.setOnTouchImageViewListener(new OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				PointF point = image.getScrollPosition();
				RectF rect = image.getZoomedRect();
				float currentZoom = image.getCurrentZoom();
				boolean isZoomed = image.isZoomed();
				//scrollPositionTextView.setText("x: " + df.format(point.x) + " y: " + df.format(point.y));
				//zoomedRectTextView.setText("left: " + df.format(rect.left) + " top: " + df.format(rect.top)
					//	+ "\nright: " + df.format(rect.right) + " bottom: " + df.format(rect.bottom));
			//	currentZoomTextView.setText("getCurrentZoom(): " + currentZoom + " isZoomed(): " + isZoomed);
			}
		});
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
