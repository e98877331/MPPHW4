package michel.com;

import java.util.HashMap;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.R;

public class MPPHW4Activity extends Activity {
    /** Called when the activity is first created. */
	
    public static final String APP_ID = "294859480537391";
    public static final String[] mPermissions = new String[] {"read_friendlists","publish_stream", "read_stream"};
    
    public static MPPHW4Activity mActivity;
    public static ListView mFriendL;
    public static TextView mET;
    
    
    private Button mLoginButton;
    
    private Facebook mFacebook;
    public static AsyncFacebookRunner mAsyncRunner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mActivity = this;
       
        
        mFriendL = (ListView)findViewById(R.id.listView1);
        
        mLoginButton = (Button)findViewById(R.id.login);
        
        mET = (TextView)findViewById(R.id.TextView1);
        
       	mFacebook = new Facebook(APP_ID);
       	mAsyncRunner = new AsyncFacebookRunner(mFacebook);
       	
       	
       	mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");
       	
       	mFriendL.setOnItemClickListener(new OnItemClickListener() {  
  
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                
				String targetID = Listeners.FriendLID.get(arg2).toString();
				
				
				Intent intent = new Intent(); 
            	intent.setClass(MPPHW4Activity.this, Friend.class); 
            	Bundle bundle = new Bundle(); 
            	
            	bundle.putString("name", mFriendL.getItemAtPosition(arg2).toString());
            	bundle.putString("ID", targetID);
            	intent.putExtras(bundle); 
            	startActivityForResult(intent,0); 
						
				//setTitle("¿ï¨ú¤F" + name);
			}
		});

       	mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
	            if (mFacebook.isSessionValid()) {
	            	mLoginButton.setText("Login");
	            	mAsyncRunner.logout(mActivity, new Listeners.LogoutRequestListener());
	            } else {
	            	
	            	mFacebook.authorize(mActivity, mPermissions,
	                              new Listeners.LoginDialogListener());
	            }
				
			}
		});
       	
       	
    }
    


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginButton.setText("Logout");
        Log.v("hehe","on Activity Result");
        //mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");
        mFacebook.authorizeCallback(requestCode, resultCode, data);
        
    }
    
    
	

	
	
}