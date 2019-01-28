package com.enamelbd.vatchecker;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Complain extends Activity{
	
	 static String bin_ = "";
	 
	 static String locInt_="";
	 static String location = "";
	 static String email = "";
	 
	 TextView cBin;
	 ImageView binImage, cam;
	 
	 Button binSubmit;
	 String msg,s1,s2;
	 LinearLayout ly;
	 
	 EditText binCompName,binCompAdd, binMsg;
	 static Bitmap photo;
	 
	 private static final int CAMERA_REQUEST = 1888; 

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ffffff")));
       // actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#1Affffff")));
		setContentView(R.layout.activity_main);
		
        ActionBar bar = getActionBar();
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           //bar.setTitle("VAT Checker");
        
        
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		
		Intent intent = getIntent();
		bin_ = intent.getStringExtra("bin");
		cBin = (TextView) findViewById(R.id.cBin);
		
		ly = (LinearLayout) findViewById(R.id.layyy1);
		
		Animation in = AnimationUtils.makeInAnimation(Complain.this, false);
		in.setDuration(450);
       	ly.startAnimation(in);
		
		
		
		binImage = (ImageView) findViewById(R.id.binImage);
		cam = (ImageView) findViewById(R.id.ImageView01);
		binSubmit = (Button) findViewById(R.id.binSubmit);
		
		Button binBack = (Button) findViewById(R.id.binBack);
		
		// EditText binCompName,binCompAdd, binMsg;
		binCompName = (EditText) findViewById(R.id.binCompName);
		binCompAdd = (EditText) findViewById(R.id.binCompAdd);
		binMsg = (EditText) findViewById(R.id.binMsg);
		
		 Button circle3 =(Button) findViewById(R.id.loccc);
	        Spannable button_circle_3 = new SpannableString(".\nChange\nLocation");
	  button_circle_3.setSpan(new ImageSpan(getApplicationContext(), R.drawable.ic_action_about,      
			     ImageSpan.ALIGN_BASELINE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
circle3.setText(button_circle_3);   

circle3.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		locateZone2();
	}
});
		
		cam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try{
					File file = new File("/sdcard/bin.png");
					file.delete();
					
					//Uri uriSavedImage=Uri.fromFile(new File("/sdcard/VAT.png"));
				     Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				     
						startActivityForResult(cameraIntent, CAMERA_REQUEST);
				}
				catch ( Exception e ){
					e.printStackTrace();
				}
		
			}
		});
		
		binBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		binSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				// EditText binCompName,binCompAdd, binMsg;
				if (!binCompName.getText().toString().isEmpty()  && !binCompAdd.getText().toString().isEmpty()){
					
					///////////////////////////////////
					   try {
						  
						   
						   
						 s1 =  cBin.getText().toString() +  "\n\n প্রতিষ্ঠানের নামঃ "+
								 binCompName.getText().toString() +"\n\n প্রতিষ্ঠানের ঠিকানাঃ " +
								 binCompAdd.getText().toString()+"\n\n";
						 
						 if ( ! binMsg.getText().toString().isEmpty()){
							 s2="";
							 s2 = binMsg.getText().toString();
							 
							 msg = s1+s2 + "\n\nPlease check attachment if available!";
						 } else{
							 msg = s1 + "\n\nPlease check attachment if available!";
						 }
						   
						   
						   File F = new File("/sdcard/bin.png");
						   Uri U = Uri.fromFile(F);
						   Intent i = new Intent(android.content.Intent.ACTION_SEND);
						   i.setType("message/rfc822"); 
						   i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email}); 
						   i.putExtra(Intent.EXTRA_SUBJECT, "VAT Complain");
						   i.putExtra(Intent.EXTRA_TEXT   , msg);
						   
						   i.putExtra(Intent.EXTRA_STREAM, U);
						   
						   startActivity(Intent.createChooser(i,"Email:"));
						   

					   }catch(Exception e ){
						   e.printStackTrace();
					   }
					
				} 
				
				else{
					
					if (binCompName.getText().toString().isEmpty()){
						Toast.makeText(getApplicationContext(), "Please Enter Company Name!", Toast.LENGTH_SHORT).show();
					} else{
						Toast.makeText(getApplicationContext(), "Please Enter Company Address!", Toast.LENGTH_SHORT).show();
					}
				}
				
				
			
			
			
			
			}

			 private File savebitmap(Bitmap bmp) {
				  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
				  OutputStream outStream = null;
				  String temp ="myImage";
				  File file = new File(extStorageDirectory, temp + ".png");
				  if (file.exists()) {
				   file.delete();
				   file = new File(extStorageDirectory, temp + ".png");
				  }

				  try {
				   outStream = new FileOutputStream(file);
				   bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				   outStream.flush();
				   outStream.close();
				  } catch (Exception e) {
				   e.printStackTrace();
				   return null;
				  }
				  return file;
				 }
		});
		
		
		
		
		locInt_ = bin_.substring(0, Math.min(bin_.length(), 2));
		//locateZone();
		//Toast.makeText(getApplicationContext(), locInt_, Toast.LENGTH_SHORT).show();
		
		if (locInt_.contains("17")) { 
			location = "ঢাকা পশ্চিম";
			email ="dhakawestcomm@yahoo.com";
		} 
		else if (locInt_.contains("18")) { 
			location = "ঢাকা উত্তর ";
			email ="commdkn@yahoo.com";
		}
		else if (locInt_.contains("19")) { 
			location = "ঢাকা দক্ষিণ  ";
			email ="cevdhksouth@yahoo.com";
		}
		else if (locInt_.contains("21")) { 
			location = "ঢাকা পূর্ব   ";
			email ="vatdhakaeast@yahoo.com";
		}
		else if (locInt_.contains("11")) { 
			location = "রংপুর   ";
			email ="rangpurvat@nbr.gov.bd";
		}
		//
		else if (locInt_.contains("12")) { 
			location = "রাজশাহী ";
			email ="cevraj93@yahoo.com";
		}		else if (locInt_.contains("14")) { 
			location = "যশোর  ";
			email ="jessorecustoms@gmail.com";
		}		else if (locInt_.contains("15")) { 
			location = " খুলনা  ";
			email ="khulnavathq@gmail.com";
		}		else if (locInt_.contains("22")) { 
			location = "সিলেট ";
			email ="sylhetcustoms@yahoo.com";
		}		else if (locInt_.contains("23")) { 
			location = "কুমিল্লা";
			email ="cevccomilla@nbr.gov.bd";
		}
		else if (locInt_.contains("24")) { 
			location = "চট্টগ্রাম";
			email ="ccevatctg@gmail.com";
		} 
		
		else{
			locateZone();
		}

		
////////////////////////////////////////////////////////////
		if(bin_!=null){
			cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
		} else{
			cBin.setText("BIN : null"+"\nLocaton : "+location);
		}

		
		
		
		
		
	}
	/////////////////////////////////////////////
/////////////////////////////////////////////
	
private void locateZone(){
// Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

Handler handler = new Handler(); 
handler.postDelayed(new Runnable() { 
@Override
public void run() { 

try{

final Dialog dialog = new Dialog(Complain.this);
dialog.setContentView(R.layout.dos);
dialog.setTitle("Select Your Location");

dialog.show();
dialog.setCancelable(true);

final Spinner sp = (Spinner) dialog.findViewById(R.id.bb_spinner);
final int[] positions = new int[2];

sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

@Override
public void onItemSelected(AdapterView<?> arg0, View arg1,
int pos, long arg3) {
// TODO Auto-generated method stub

switch(pos){
case 0 : 
// Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
break;

case 1 : 
location = "ঢাকা পশ্চিম";
email ="dhakawestcomm@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 2 : 
location = "ঢাকা উত্তর ";
email ="commdkn@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 3 : 
location = "ঢাকা দক্ষিণ  ";
email ="cevdhksouth@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 4 : 
location = "ঢাকা পূর্ব   ";
email ="vatdhakaeast@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 5 : 
location = "রংপুর   ";
email ="rangpurvat@nbr.gov.bd";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 6 : 
location = "রাজশাহী ";
email ="cevraj93@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 7 : 
location = "যশোর  ";
email ="jessorecustoms@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 8 : 
location = " খুলনা  ";
email ="khulnavathq@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 9 : 
location = "সিলেট ";
email ="sylhetcustoms@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 10 : 
location = "কুমিল্লা";
email ="cevccomilla@nbr.gov.bd";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 11 : 
location = "চট্টগ্রাম";
email ="ccevatctg@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;








}

}

@Override
public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

}
});


} catch(Exception e){
e.printStackTrace();
}


} 
}, 2000);


// ===================================================================


}
//
/////////////////////////////////////////////

private void locateZone2(){
// Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

Handler handler = new Handler(); 
handler.postDelayed(new Runnable() { 
@Override
public void run() { 

try{

final Dialog dialog = new Dialog(Complain.this);
dialog.setContentView(R.layout.dos);
dialog.setTitle("Select Your Location");

dialog.show();
dialog.setCancelable(true);

final Spinner sp = (Spinner) dialog.findViewById(R.id.bb_spinner);
final int[] positions = new int[2];

sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

@Override
public void onItemSelected(AdapterView<?> arg0, View arg1,
int pos, long arg3) {
// TODO Auto-generated method stub

switch(pos){
case 0 : 
// Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
break;

case 1 : 
location = "ঢাকা পশ্চিম";
email ="dhakawestcomm@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 2 : 
location = "ঢাকা উত্তর ";
email ="commdkn@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 3 : 
location = "ঢাকা দক্ষিণ  ";
email ="cevdhksouth@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 4 : 
location = "ঢাকা পূর্ব   ";
email ="vatdhakaeast@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 5 : 
location = "রংপুর   ";
email ="rangpurvat@nbr.gov.bd";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 6 : 
location = "রাজশাহী ";
email ="cevraj93@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 7 : 
location = "যশোর  ";
email ="jessorecustoms@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 8 : 
location = " খুলনা  ";
email ="khulnavathq@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 9 : 
location = "সিলেট ";
email ="sylhetcustoms@yahoo.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;

case 10 : 
location = "কুমিল্লা";
email ="cevccomilla@nbr.gov.bd";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;


case 11 : 
location = "চট্টগ্রাম";
email ="ccevatctg@gmail.com";
dialog.dismiss();
if(bin_!=null){
cBin.setText("BIN : "+bin_+"\nLocaton : "+location);
} else{
cBin.setText("BIN : null"+"\nLocaton : "+location);
}
break;








}

}

@Override
public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

}
});


} catch(Exception e){
e.printStackTrace();
}


} 
}, 20);


// ===================================================================


}
//
	
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            photo = (Bitmap) data.getExtras().get("data"); 
	            binImage.setImageBitmap(photo);
	            
FileOutputStream out = null;
try {
    out = new FileOutputStream("/sdcard/bin.png");
    photo.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
    // PNG is a lossless format, the compression factor (100) is ignored
} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (out != null) {
            out.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


	            
	            
	            
	        }  
	    } 


}
