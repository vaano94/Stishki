package com.stishki.iva;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stishki.iva.Helper.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class UserInfoActivity extends AppCompatActivity {
    TextView userNametv;
    TextView nicktv;
    TextView emailtv;
    TextView likestv;
    TextView dislikestv;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "userToken" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button = (Button) findViewById(R.id.btn_cng_pswrd);
        userNametv = (TextView) findViewById(R.id.user_name);
        nicktv = (TextView) findViewById(R.id.user_nick);
        emailtv = (TextView) findViewById(R.id.user_email);
        likestv = (TextView) findViewById(R.id.liketv);
        dislikestv = (TextView) findViewById(R.id.disliketv);

        new UserDetails().execute();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private class UserDetails extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            String result = "BAD";
            try {
                sharedpreferences = getSharedPreferences(userPreferences, Context.MODE_PRIVATE);
                String token = sharedpreferences.getString("Token", "");


                result =  new UserHelper().getUserDetails(token);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject userCredentialsJson = new JSONObject(s);
                String userName = userCredentialsJson.getString("name");
                String nickName = userCredentialsJson.getString("nickName");
                String email = userCredentialsJson.getString("email");
                int likes = Integer.parseInt(userCredentialsJson.getString("likes"));
                int dislikes = Integer.parseInt(userCredentialsJson.getString("dislikes"));

                userNametv.setText(userName);
                nicktv.setText(nickName);
                emailtv.setText(email);
                likestv.setText(String.valueOf(likes));
                dislikestv.setText(String.valueOf(dislikes));

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

}
