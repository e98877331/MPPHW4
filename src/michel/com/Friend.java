package michel.com;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.R;


public class Friend extends Activity {
	
	public static Friend mFriend;

	Bundle bundle;
	public String ID,name;
	
	ImageView ImageView;
	TextView FriendName; 
	public TextView Status; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend);
		
		mFriend = this;
		bundle = this.getIntent().getExtras(); 
		ID = bundle.getString("ID");
		name = bundle.getString("name");
		ImageView = (ImageView)findViewById(R.id.imageView1);
		Status = (TextView)findViewById(R.id.statusView);
		Log.v("pricutre","https://graph.facebook.com/" + ID + "/picture");
		Bitmap pic = getImageBitmap("https://graph.facebook.com/" + ID + "/picture?type=normal");
		
		MPPHW4Activity.mAsyncRunner.request(ID+"/feed", new Listeners.SampleRequestListener());
		
		ImageView.setImageBitmap(pic);
		//ImageView.setImageResource(R.drawable.facebook_icon);
		FriendName = (TextView)findViewById(R.id.FriendN);
		FriendName.setText(name);
		
		
	}
    private Bitmap getImageBitmap(String url) { 
        Bitmap bm = null; 
        try { 
            URL aURL = new URL(url); 
            URLConnection conn = aURL.openConnection(); 
            conn.connect(); 
            InputStream is = conn.getInputStream(); 
            BufferedInputStream bis = new BufferedInputStream(is); 
            bm = BitmapFactory.decodeStream(bis); 
            bis.close(); 
            is.close(); 
       } catch (IOException e) { 
          // Log.e(TAG, "Error getting bitmap", e); 
       } 
       return bm; 
    }
	
}
