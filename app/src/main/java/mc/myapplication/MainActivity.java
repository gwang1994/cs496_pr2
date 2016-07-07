package mc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton facebookLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("user_friends");

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result){
                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(login_result.getAccessToken(), "/me/invitable_friends", null, HttpMethod.GET, new GraphRequest.Callback()
                {
                    public void onCompleted(GraphResponse response) {
                        try {
                            Log.d("JGqwer", response.getJSONObject().getJSONArray("data").toString());
                            JSONObject rawName = response.getJSONObject();
                            String jsondata = rawName.toString();
                            JSONObject friendslist;
//                            ArrayList<String> friends_name = new ArrayList<String>();
//                            ArrayList<String> friends_profile = new ArrayList<String>();

                            try {
                                friendslist = new JSONObject(jsondata);
//                                for (int l=0; l < friendslist.length(); l++) {
//                                    friends_name.add(friendslist.getJSONObject(l).getString("name"));
//                                }
//                                for (int l=0; l < friendslist.length(); l++) {
//                                    friends_profile.add(friendslist.getJSONObject(l).getString("picture"));
//                                }
//                            Log.d("Jp", friends_name.toString());
//                            Log.d("JP", friends_profile.toString());
                                Log.d("JG", "why?33");
                                newActivity newactivity = new newActivity();
                                newactivity.showUp(rawName);
                                Log.d("JG", "why?");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel()
            {
                // App code
                Toast.makeText(MainActivity.this, "로그인을 취소 하였습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception)
            {
                // App code
                Toast.makeText(MainActivity.this, "에러가 발생하였습니다", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class NetClientPost {

        public void sendServer(JSONArray friend) {
            HttpURLConnection conn = null;
            InputStream is = null;
            ByteArrayOutputStream baOs = null;
            Log.d("JG", "first");

            try {
                URL url = new URL("http://143.248.48.235:8005");
                conn = (HttpURLConnection) url.openConnection();   //conn 에 웹페이지 URL 을 연결시킨 것이다.
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");  //요청 방식은 POST
                conn.setRequestProperty("Content-Type", "application/json");
                //Request body 를 application/json 로 서버에 전달
                Log.d("JG", "second");

                Log.d("JG","third2");
                OutputStream os = conn.getOutputStream(); //Request body 를 Data 에 담기 위해 OutputStream 객체를 생성
                Log.d("JG","third24");
                os.write(friend.toString().getBytes()); //Request body 에 Data 를 셋팅
                Log.d("JG","third2");
                os.flush(); //Request body 에 Data 입력
                Log.d("JG","third");
                conn.disconnect(); //이거 나중에 지워봐야 할 수도 있음
            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("myLog"  ,"requestCode  "  + requestCode);
        Log.d("myLog"  ,"resultCode"  + resultCode);
        Log.d("myLog"  ,"data  "  + data.toString());
    }

    public void onButtonClicked(View v){
        Intent helloIntent;
        helloIntent = new Intent(getApplicationContext(), newActivity.class);
        startActivity(helloIntent);
    }


}
