package com.enamelbd.vatchecker;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
	
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
  //  public ImageLoader imageLoader; 

 	
 	
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  imageLoader=new ImageLoader(activity, activity.getApplicationContext());
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
        
        
         	   vi = inflater.inflate(R.layout.list_item, null);
            
            TextView name = (TextView)vi.findViewById(R.id.item_name); // title
            TextView ra_deg = (TextView)vi.findViewById(R.id.item_ra_deg); // artist name
            TextView dec_deg = (TextView)vi.findViewById(R.id.item_dec_deg); // duration
           
            TextView alter_name = (TextView)vi.findViewById(R.id.alter_name); // duration
            TextView type = (TextView)vi.findViewById(R.id.type); // duration
            TextView today = (TextView)vi.findViewById(R.id.item_today); // duration
            TextView yesterday = (TextView)vi.findViewById(R.id.yesterday); // duration
            TextView tenday = (TextView)vi.findViewById(R.id.tenday); // duration
            TextView mean = (TextView)vi.findViewById(R.id.mean); // duration
            TextView peak = (TextView)vi.findViewById(R.id.peak); // duration
            TextView days = (TextView)vi.findViewById(R.id.days); // duration
            TextView last_day = (TextView)vi.findViewById(R.id.last_day); // duration
            
            
            // ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
            
            final LinearLayout max = (LinearLayout)vi.findViewById(R.id.item_details); // title
            
            
            // Setting all values in listview
           name.setText(song.get(home.KEY_NAME));
           ra_deg.setText(song.get(home.KEY_ra_deg));
           dec_deg.setText(song.get(home.KEY_dec_deg));
           
           alter_name.setText(song.get(home.KEY_alter_name));
           type.setText(song.get(home.KEY_type));
           today.setText(song.get(home.KEY_today));
           yesterday.setText(song.get(home.KEY_yesterday));
           tenday.setText(song.get(home.KEY_tenday));
           mean.setText(song.get(home.KEY_mean));
           peak.setText(song.get(home.KEY_peak));
           days.setText(song.get(home.KEY_days));
           last_day.setText(song.get(home.KEY_last_day));
           
           
           
           
           
           SharedPreferences mypreferences = activity.getSharedPreferences("nasa", Context.MODE_PRIVATE);
           String login = mypreferences.getString("showAll", "");
           if(login.contentEquals("true")){
        	   vi.findViewById(R.id.list_image_expand).setVisibility(View.GONE);
        	   vi.findViewById(R.id.list_image_collapse).setVisibility(View.VISIBLE);
        	   max.setVisibility(View.VISIBLE);
           }else{
        	   vi.findViewById(R.id.list_image_expand).setVisibility(View.VISIBLE);
        	   vi.findViewById(R.id.list_image_collapse).setVisibility(View.GONE);
        	   max.setVisibility(View.GONE);
           
           }
           
           
           
           
           
           final ImageView list_image_expand = (ImageView)vi.findViewById(R.id.list_image_expand);
           final ImageView list_image_collapse = (ImageView)vi.findViewById(R.id.list_image_collapse);
           
           
           LinearLayout btnUpdate = (LinearLayout)vi.findViewById(R.id.expend);
           btnUpdate.setTag(song.get(home.KEY_NAME));
            
           btnUpdate.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
               	
               	if(max.getVisibility() == View.GONE){
               		max.setVisibility(View.VISIBLE);
               		list_image_expand.setVisibility(View.GONE);
               		list_image_collapse.setVisibility(View.VISIBLE);
               	}else{
               		max.setVisibility(View.GONE);
               		list_image_expand.setVisibility(View.VISIBLE);
               		list_image_collapse.setVisibility(View.GONE);
               	}
            	   
            
             		//Toast.makeText(context, v.getTag().toString(), 1000).show();
               } // end onClick
           }); // end setOnClickListener
         

       
        return vi;
    }
}