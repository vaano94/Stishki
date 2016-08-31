package com.stishki.iva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stishki.iva.Helper.LoginHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    Logincheck loginCheck;
    private EditText loget;
    private EditText passet;

    SharedPreferences sharedpreferences;
    public static final String userPreferences = "userToken" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //new PreLoginCheck().execute();

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginCheck = new Logincheck();
                loget = (EditText) findViewById(R.id.login_parameter);
                passet = (EditText) findViewById(R.id.password_parameter);
                loginCheck.execute(
                        loget.getText().toString(),
                        passet.getText().toString()
                );
            }
        });


        TextView regredirect = (TextView) findViewById(R.id.login_newacc);
        regredirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        TextView noacc = (TextView) findViewById(R.id.no_account);
        noacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public class PreLoginCheck extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... params) {

            sharedpreferences = getSharedPreferences(userPreferences,Context.MODE_PRIVATE );
            String token = sharedpreferences.getString("Token", "");
            if (!token.equals("")) {

                String newToken = LoginHelper.prelogin(token);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (token.equals("could not establish connection")) {
                    cancel(true);
                    return token;
                }

                editor.putString("Token", newToken);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Token", sharedpreferences.getString("Token", ""));
                startActivity(intent);
            }
            else {
                cancel(true);
            }
            return "something";
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class Logincheck extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result ="";
            try {
                String nick = params[0];
                String password = params[1];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nickName", nick);
                jsonObject.put("password", password);
                result = LoginHelper.login(jsonObject);

            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                if (s.equals("could not establish connection")) {
                    {Toast.makeText(getApplicationContext(), "Нет подключения к серверу", Toast.LENGTH_SHORT).show();
                    cancel(true);
                    }
                }
                else {
                JSONObject jsonObject = new JSONObject(s);
                String result = (String) jsonObject.get("result");
                switch(result) {
                    case "OK": {
                        String token = (String) jsonObject.get("token");
                        int id = jsonObject.getInt("id");
                        Toast.makeText(getApplicationContext(), "Логин успешен", Toast.LENGTH_SHORT).show();

                        //TODO save token in sharedPreferences
                        sharedpreferences = getSharedPreferences(userPreferences, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putInt("id",id);
                        editor.putString("Token", token);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        finish();


                        break;
                    }
                    case "BAD": {Toast.makeText(getApplicationContext(), "Неверные имя пользователя/пароль", Toast.LENGTH_LONG).show();break;}
                }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }

}
