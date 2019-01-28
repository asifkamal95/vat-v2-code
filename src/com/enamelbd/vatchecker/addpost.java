package com.enamelbd.vatchecker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import com.google.analytics.tracking.android.EasyTracker;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class addpost extends Activity {
	
	
	
	private String img_code = "";
	private ImageView imageview;
	private int serverResponseCode = 0;
	private ProgressDialog dialog = null;

	private String upLoadServerUri = "http://enamelbd.org/apps/nasa/upload_status_pic.php";
	
	private String filepath = null;
	int FLAG = 0;
	
	private EditText post_status, name;
	
	
	
private HttpStatusRequest httpStatus = new HttpStatusRequest();
	
	

	

public static boolean isNetworkAvailable(Context context) 
{
    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
}
	
public static void DeleteRecursive(File fileOrDirectory)
{
    if (fileOrDirectory.isDirectory()) 
    {
        for (File child : fileOrDirectory.listFiles())
        {
            DeleteRecursive(child);
        }
    }

    fileOrDirectory.delete();
}

	
	
	private class HttpStatusRequest extends AsyncTask<Void, Void, CharSequence> {
		
		
		protected CharSequence doInBackground(Void... params) {
	        BufferedReader in = null;
	        String baseUrl = "http://www.enamelbd.org/apps/nasa/status.php";

	        try {
	            HttpClient httpClient = new DefaultHttpClient();
	            HttpPost request = new HttpPost(baseUrl);
	           
	          
	            
	            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	            String device_id =  telephonyManager.getDeviceId();
	             
	            
	           

	            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	            
	           
	            postParameters.add(new BasicNameValuePair("status", post_status.getText().toString()));
	            postParameters.add(new BasicNameValuePair("user_name", name.getText().toString()));
		           
	           
	            
	        //    postParameters.add(new BasicNameValuePair("type", status_type.getSelectedItem().toString()));
	          //  postParameters.add(new BasicNameValuePair("department", status_department_type.getSelectedItem().toString()));
	            //postParameters.add(new BasicNameValuePair("level", status_level_type.getSelectedItem().toString()));
		           
	            
	            postParameters.add(new BasicNameValuePair("device", device_id));
	            postParameters.add(new BasicNameValuePair("code", img_code));
	            
	            
	            postParameters.add(new BasicNameValuePair("data", "somedata"));
	            UrlEncodedFormEntity form = new UrlEncodedFormEntity(postParameters,"UTF-8");
	            request.setEntity(form);

	            Log.v("log", "making POST request to: " + baseUrl);

	            HttpResponse response = httpClient.execute(request);

	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer sb = new StringBuffer("");
	            String line = "";
	            String NL = System.getProperty("line.separator");
	            while ((line = in.readLine()) != null) {
	                sb.append(line + NL);
	            }
	            in.close();

	            return sb.toString();
	        } catch (Exception e) {
	            return "Exception happened: " + e.getMessage();
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    protected void onPostExecute(CharSequence result) {
	        // this refers to a TextView defined as a private field in the parent Activity
	        //textView.setText(result);
	        if(result.toString().contains("posted")){
	        	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		        dialog.dismiss();
		        finish();
	        }else{
	    	
	    	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	        dialog.dismiss();
	        }
	      //  finish();
	    }

	}
	
	
	
	
	
	public void justcheckData(){
		post_status = (EditText)findViewById(R.id.post_status);
		name = (EditText)findViewById(R.id.name);
	    //  status_type = (Spinner)findViewById(R.id.status_type);
	      
	      //status_department_type = (Spinner)findViewById(R.id.status_department_type);
	      //status_level_type = (Spinner)findViewById(R.id.status_level_type);
	       
	       if(post_status.getText().toString().trim().length()< 1){
	    	 //  dialog.dismiss();
	      	Toast.makeText(getApplicationContext(), "Please write something to make a new post", Toast.LENGTH_LONG).show();
	       
	       }else{
	      	 
   				
   				if (FLAG == 0) {
	        		 
	        		 dialog = ProgressDialog.show(addpost.this, "",
		        				"Posting new status...", true);	

		        	   httpStatus = new HttpStatusRequest();
	   				httpStatus.execute();
	   				
	        			} else {
	        				
	        			
	        				
	        				if (filepath != null) {
	        					
	        					img_code = "";
	        					
	        				dialog = ProgressDialog.show(addpost.this, "",
	        				"Posting new status...", true);
	        				new Thread(new Runnable() {
	        				public void run() {
	        				uploadFile(filepath);
	        				}
	        				}).start();
	        				} else {
	        				Toast.makeText(addpost.this, "Please try again !!!",
	        				Toast.LENGTH_LONG).show();
	        				}	
	        				
	        				
	        				
	        				
	        			}
	        	 
   				
   				
   				
   				
   				
	  	           }
	          	
	       }
	      
	
	

    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);
        
        
        
ActionBar bar = getActionBar();
 
        
        if(android.os.Build.VERSION.SDK_INT > 13) {
        	 bar.setIcon(getResources().getDrawable(
                    R.drawable.ic_action_web_site));
        		 }
           bar.setTitle("Post New Status");
        
        
        
        Button  status__finish = (Button) findViewById(R.id.status__finish);
        status__finish.setOnClickListener(onClickListener);
        
        
        
        
        Button  status__addPic = (Button) findViewById(R.id.status__addPic);
        status__addPic.setOnClickListener(onClickListener);
        
        imageview = (ImageView) findViewById(R.id.status_img);
        imageview.setOnClickListener(onClickListener);
    }

    
    
    
    

	 private OnClickListener onClickListener = new OnClickListener() {
	     @SuppressLint("SetJavaScriptEnabled")
		@Override
	     public void onClick(View v) {
	    	 
				
	         switch(v.getId()){
	         
	         
	         
	         
	         	case R.id.status__addPic:
	        	 
	         		 
		        		Intent intentx = new Intent();
		        		// intent.setType("video/*");
		        		// intent.setType("audio/*");
		        		intentx.setType("image/*");
		        		intentx.setAction(Intent.ACTION_GET_CONTENT);
		        		startActivityForResult(
		        		Intent.createChooser(intentx, "Complete action using"), 1);
		        	 
	        	 break;
	 	 
	         
	         
	         
	         case R.id.status__finish:
	        	if(!isNetworkAvailable(addpost.this)){
		       			Toast.makeText(getApplicationContext(), "Please check your internet connection...", Toast.LENGTH_LONG).show();
		       		}else{
	        	
	        	 justcheckData();
	        	 
		       		}
	        	 break;
	 	 
	 	 
	 	 
	 	 
	 	 
	         case R.id.status_img:
	        		Intent intent = new Intent();
	        		// intent.setType("video/*");
	        		// intent.setType("audio/*");
	        		intent.setType("image/*");
	        		intent.setAction(Intent.ACTION_GET_CONTENT);
	        		startActivityForResult(
	        		Intent.createChooser(intent, "Complete action using"), 1);
	        	 
	        break;
	         }
	     }
	 };
    
    
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && resultCode == RESULT_OK) {
			 FLAG = 1;
		Uri selectedImageUri = data.getData();
		filepath = getPath(selectedImageUri);
		Bitmap bitmap = BitmapFactory.decodeFile(filepath);
		
		
		File fff = new File(filepath);
		bitmap= ExifUtils.rotateBitmap(filepath, bitmap);
		
		
		imageview.setImageBitmap(bitmap);


		String filename = "mist-"+fff.getName().toString();
		File sd = Environment.getExternalStorageDirectory();
		File dest = new File(sd, filename);

		try {
		     FileOutputStream out = new FileOutputStream(dest);
		     bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		     out.flush();
		     out.close();
		} catch (Exception e) {
		     e.printStackTrace();
		}
		
		
		findViewById(R.id.status_img).setVisibility(View.VISIBLE);
		findViewById(R.id.status__addPic).setVisibility(View.GONE);
		
		
		}else{
			 FLAG = 0;
			findViewById(R.id.status_img).setVisibility(View.GONE);
			findViewById(R.id.status__addPic).setVisibility(View.VISIBLE);
			
		}
		}

	 @SuppressWarnings("deprecation")
	 public String getPath(Uri uri) {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = managedQuery(uri, projection, null, null, null);
			int column_index = cursor
			.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
			}

	 
	 
	 
	 
	 
	 
	 public int uploadFile(String sourceFileUri) {

			String fileName = sourceFileUri;
			
			
			 
			File fff = new File(fileName);
			sourceFileUri = Environment.getExternalStorageDirectory()+"/mist-"+fff.getName().toString();
		    fileName = Environment.getExternalStorageDirectory()+"/mist-"+fff.getName().toString();
		    
		    

			HttpURLConnection conn = null;
			DataOutputStream dos = null;
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
			File sourceFile = new File(sourceFileUri);

			if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + filepath);

			runOnUiThread(new Runnable() {
			public void run() {
			//messageText.setText("Source File not exist :" + filepath);
			}
			});

			return 0;

			} else {
			try {
			FileInputStream fileInputStream = new FileInputStream(
			sourceFile);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
			"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
			+ fileName + "\"" + lineEnd);

			dos.writeBytes(lineEnd);
			
			
			
			
			

			// create a buffer of maximum size
			bytesAvailable = fileInputStream.available();

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {

			dos.write(buffer, 0, bufferSize);
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			final String serverResponseMessage = conn.getResponseMessage();
			
			
			
			
			
			
			
			try 
			{
				DataInputStream inStream = new DataInputStream ( conn.getInputStream() );
			       String str;
			       while (( str = inStream.readLine()) != null)
			       {
			           final String yourmessage = str;
			           
			           img_code = yourmessage;
			           
			       	Log.i("uploadFile", "HTTP Response is : "
			       			+ yourmessage + "::::::::::::::::::::: " + serverResponseCode);
			       }
			       inStream.close();
			}
			catch (IOException ioex)
			{
			    Log.e("SD card doFile upload error: ",""+ ioex.getMessage());       
			}
			
			 
			

			Log.i("uploadFile", "HTTP Response is : "
			+ serverResponseMessage + ": " + serverResponseCode);

			if (serverResponseCode == 200) {

			runOnUiThread(new Runnable() {
			public void run() {
			String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
			+ " c:/wamp/www/echo/uploads";
			//messageText.setText(msg);
			//Toast.makeText(addpic.this, "File Upload Complete."+ serverResponseMessage, Toast.LENGTH_SHORT).show();
			
			
		    try{
		    	File fff = new File(filepath);
		    File file = new File(Environment.getExternalStorageDirectory()+"/mist-"+fff.getName().toString());
		    DeleteRecursive(file);
		    } catch (Exception e) {
			     e.printStackTrace();
			}
			

     	   httpStatus = new HttpStatusRequest();
			httpStatus.execute();
			
			
			
			}
			});
			}

			// close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

			} catch (MalformedURLException ex) {

			dialog.dismiss();
			ex.printStackTrace();

			runOnUiThread(new Runnable() {
			public void run() {
			
			Toast.makeText(addpost.this,
			"Unexpected error: 01", Toast.LENGTH_SHORT)
			.show();
			}
			});

			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

			dialog.dismiss();
			e.printStackTrace();

			runOnUiThread(new Runnable() {
			public void run() {
			//messageText.setText("Got Exception : see logcat ");
			Toast.makeText(addpost.this,
			"Unexpected error: 02",
			Toast.LENGTH_SHORT).show();
			}
			});
			Log.e("Upload file to server Exception",
			"Exception : " + e.getMessage(), e);
			}
			//dialog.dismiss();
			return serverResponseCode;
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
