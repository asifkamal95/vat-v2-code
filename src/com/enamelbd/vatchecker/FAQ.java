package com.enamelbd.vatchecker;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class FAQ extends Activity{
	
	LinearLayout ly;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
       // actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#1Affffff")));
		setContentView(R.layout.faq);
		
        ActionBar bar = getActionBar();
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           //bar.setTitle("VAT Checker");
           
           
		
		ly = (LinearLayout) findViewById(R.id.lyat111);
		
			Animation in = AnimationUtils.makeInAnimation(FAQ.this, false);
			in.setDuration(500);
           	ly.startAnimation(in);
		
		
		
		
		
		
	}
	
	

}
