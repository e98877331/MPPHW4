package michel.com;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.R;

public class MPPHW4Activity extends Activity {
    /** Called when the activity is first created. */
	
    public static final String APP_ID = "294859480537391";
    public static final String[] mPermissions = new String[] {"read_friendlists", "publish_stream"};
    
    public static MPPHW4Activity mActivity;
    private Button mLoginButton;
    public static ListView mFriendL;
    
    
    private Facebook mFacebook;
    public static AsyncFacebookRunner mAsyncRunner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mActivity = this;
        
        
        mLoginButton = (Button)findViewById(R.id.login);
        mFriendL = (ListView)findViewById(R.id.listView1);
       	mFacebook = new Facebook(APP_ID);
       	mAsyncRunner = new AsyncFacebookRunner(mFacebook);
       	
       	
       	mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");

       	mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
	            if (mFacebook.isSessionValid()) {
	            	mLoginButton.setText("Login");
	            	mAsyncRunner.logout(mActivity, new Listeners.LogoutRequestListener());
	            } else {
	            	mLoginButton.setText("Logout");
	            	mFacebook.authorize(mActivity, mPermissions,
	                              new Listeners.LoginDialogListener());
	            }
				
			}
		});
       	
       	
    }
    


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.v("hehe","on Activity Result");
        //mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");
        mFacebook.authorizeCallback(requestCode, resultCode, data);
        
    }
    
    
	

	
	
}