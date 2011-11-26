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
    
    private final MPPHW4Activity mActivity = this;
    private Button mLoginButton;
    
    
    private Facebook mFacebook;
    private AsyncFacebookRunner mAsyncRunner;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mLoginButton = (Button)findViewById(R.id.login);
        
       	mFacebook = new Facebook(APP_ID);
       	mAsyncRunner = new AsyncFacebookRunner(mFacebook);
       	
       	Log.v("onCreate","yaaaaaaaaaaaaaa");
       	mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");

       	mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");
	            if (mFacebook.isSessionValid()) {
	            
	            	//Log.v("onclick","logout");
	            	mAsyncRunner.logout(mActivity, new LogoutRequestListener());
	            } else {
	         
	            	 // Log.v("onclick","login");
	            	mFacebook.authorize(mActivity, mPermissions,
	                              new LoginDialogListener());
	            }
				
			}
		});
       	
       	
    }
    


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Log.v("hehe","on Activity Result");
        mLoginButton.setText(mFacebook.isSessionValid() ? "Logout" : "Login");
        mFacebook.authorizeCallback(requestCode, resultCode, data);
        
    }
    
    public final class LogoutRequestListener implements RequestListener {

        public void onFacebookError(FacebookError e, final Object state) {
            Log.e("Facebook", e.getMessage());
            e.printStackTrace();
        }

        public void onFileNotFoundException(FileNotFoundException e,
                                            final Object state) {
            Log.e("Facebook", e.getMessage());
            e.printStackTrace();
        }

        public void onIOException(IOException e, final Object state) {
            Log.e("Facebook", e.getMessage());
            e.printStackTrace();
        }

        public void onMalformedURLException(MalformedURLException e,
                                            final Object state) {
            Log.e("Facebook", e.getMessage());
            e.printStackTrace();
        }

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			
			Log.v("hehe","logout complete");
			//Toast.makeText(mActivity, "LOOOOGOUT", Toast.LENGTH_SHORT);
			
		}       
        
    }
    private final class LoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
           // SessionEvents.onLoginSuccess();
        	
        	
        }

        public void onFacebookError(FacebookError error) {
         //   SessionEvents.onLoginError(error.getMessage());
        }
        
        public void onError(DialogError error) {
           // SessionEvents.onLoginError(error.getMessage());
        }

        public void onCancel() {
           // SessionEvents.onLoginError("Action Canceled");
        }
    }
}