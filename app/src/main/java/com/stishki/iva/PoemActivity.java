package com.stishki.iva;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stishki.iva.Database.DatabaseManager;
import com.stishki.iva.Fragments.PoemChooseFragment;
import com.stishki.iva.Fragments.PoemDescriptionFragment;
import com.stishki.iva.Fragments.PoemWritingFragment;
import com.stishki.iva.Fragments.dummy.DummyContent;
import com.stishki.iva.Helper.PoemHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class PoemActivity extends AppCompatActivity implements PoemChooseFragment.OnFragmentInteractionListener, PoemDescriptionFragment.OnFragmentInteractionListener, PoemWritingFragment.OnFragmentInteractionListener {
    private EditText poemEditText;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "userToken" ;
    public TextView  testfield;
    DatabaseManager manager;

    FragmentManager fm;
    FragmentTransaction ft;
    PoemChooseFragment poemChooseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_poem);

        manager = new DatabaseManager(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            poemChooseFragment = new PoemChooseFragment();
            //getFragmentManager().beginTransaction().replace(android.R.id.content, poemChooseFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, poemChooseFragment).commit();

        }

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class PoemSender extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("OK")) {
                Toast.makeText(getApplicationContext(), "Poem has been sent!", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String token = params[0];
                String poem = params[1];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                jsonObject.put("poem", poem);
                result = PoemHelper.sendPoem(jsonObject);
            }
            catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}
