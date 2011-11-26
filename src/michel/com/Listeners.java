package michel.com;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Listeners {
	
	static MPPHW4Activity mActivity;
	public static ArrayList<String> FriendLID;

	
	public static class LogoutRequestListener implements RequestListener {

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
			//Log.v("hehe","logout complete");
			MPPHW4Activity.mActivity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(MPPHW4Activity.mActivity, "LOOOOGOUT", Toast.LENGTH_SHORT).show();
					
				}
				
				
			});
			
		}       
        
    }
	
    static class LoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
           // SessionEvents.onLoginSuccess();	
        	
        	MPPHW4Activity.mAsyncRunner.request("me/friends",new RequestListener(){

				@Override
				public void onComplete(final String response, Object state) {
					// TODO Auto-generated method stub
					
					MPPHW4Activity.mActivity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							MPPHW4Activity.mET.setText("ªB¤Í¦W³æ");
							FriendLID = new ArrayList<String>();
							ArrayList<String> al = new ArrayList<String>();
							JSONObject friend;
							JSONArray friendList;
							Bundle b = new Bundle();
							try {
								friend = new JSONObject(response);
								friendList = friend.getJSONArray("data");
								for(int i = 0;i<friendList.length();i++)
								{
									//Log.v("FL",friendList.getJSONObject(i).getString("name"));
									
									al.add(friendList.getJSONObject(i).getString("name"));
									FriendLID.add(friendList.getJSONObject(i).getString("id"));
									
								}
								String[] temp = new String[al.size()];
								al.toArray(temp);
								MPPHW4Activity.mFriendL.setAdapter(new ArrayAdapter<String>(MPPHW4Activity.mActivity,   
								           android.R.layout.simple_list_item_1, temp));
								
							} catch (JSONException e){
								
							}
							
						}
					});
					

					
					
				}

				@Override
				public void onIOException(IOException e, Object state) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFileNotFoundException(FileNotFoundException e,
						Object state) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onMalformedURLException(MalformedURLException e,
						Object state) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFacebookError(FacebookError e, Object state) {
					// TODO Auto-generated method stub
					
				}
        	
        	}); 	
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
