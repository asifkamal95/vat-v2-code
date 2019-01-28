package com.enamelbd.vatchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FeedAdapter extends BaseAdapter {
	

    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public DetailsImageLoader imageLoader; 

 	
 	
    
    public FeedAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new DetailsImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }
    
    
    

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;

        	HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        

        
        if(song.get(home.KEY_THUMB_URL).toString().length() > 6){
         	   vi = inflater.inflate(R.layout.feed_item, null);
        }else{
        	vi = inflater.inflate(R.layout.feed_item_singlepost, null);
        }

            TextView name = (TextView)vi.findViewById(R.id.name); // title
            TextView time = (TextView)vi.findViewById(R.id.timestamp); // title
            TextView status = (TextView)vi.findViewById(R.id.txtStatusMsg); // title
            
            ImageView pro_pic =(ImageView)vi.findViewById(R.id.profilePic); // thumb image
            ImageView status_img =(ImageView)vi.findViewById(R.id.feedImage1); // thumb image
            
            
            
            
            
            
            
            
    
            
            
            String max = song.get(home.KEY_TITLE).toString();
            
            if(max.length() > 100){
           // max = max.substring(0, 50);
            
            Pattern pattern = Pattern.compile("([\\S]+\\s*){1,10}");
            Matcher matcher = pattern.matcher(max);
            matcher.find();
           
            max =  matcher.group() + "...";
            
            }
            name.setText(max);
            
            
            String caption = song.get(home.KEY_SDETAILS).toString();
            
            if(caption.length() > 150){
                // max = max.substring(0, 50);
                 
                 Pattern pattern = Pattern.compile("([\\S]+\\s*){1,35}");
                 Matcher matcher = pattern.matcher(caption);
                 matcher.find();
                
                 caption =  matcher.group() + "...";
                 
                 }
            
            
            time.setText(song.get(home.KEY_TIME).toString());
            status.setText(caption);
         	
         	imageLoader.DisplayImage(song.get(home.KEY_THUMB_URL), status_img);
         	imageLoader.DisplayImage(song.get(home.KEY_DETAILS), pro_pic);
    	
            
            
           
         

       
        return vi;
    }
}