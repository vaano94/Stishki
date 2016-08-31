package com.stishki.iva.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stishki.iva.Helper.PoemHelper;
import com.stishki.iva.PoemActivity;
import com.stishki.iva.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import android.widget.LinearLayout.LayoutParams;


public class PoemWritingFragment extends Fragment {
    private static final String ARG_PARAM1 = "Genre";
    private static final String ARG_PARAM2 = "param2";

    private EditText poemEditText;
    SharedPreferences sharedpreferences;
    public static final String userPreferences = "userToken" ;
    public TextView testfield;
    private static ArrayList<EditText> hashtags = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PoemWritingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PoemWritingFragment newInstance(String param1, String param2) {
        PoemWritingFragment fragment = new PoemWritingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PoemWritingFragment() {
        // Required empty public constructor
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poem_writing, container, false);


        final EditText poemEditText = (EditText) v.findViewById(R.id.PoemEditorFragment);
        hashtags.add(poemEditText);
        final Button sendPoem = (Button) v.findViewById(R.id.PoemSendButton);

        final LinearLayout ll = (LinearLayout)v.findViewById(R.id.fragment_linear_layout_2);

        final EditText firstEditText = (EditText) v.findViewById(R.id.first_edit_Text);
        final Button addTagButton = (Button) v.findViewById(R.id.add_tag_button);
        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hashtags.size() == 3 ) {
                    Toast.makeText(getActivity().getApplicationContext(), "Нельзя использовать больше трех тегов", Toast.LENGTH_SHORT).show();
                    return; }
                else {
                    EditText editText = new EditText(getActivity().getApplicationContext());
                    editText.setWidth(firstEditText.getWidth());
                    editText.setHeight(firstEditText.getHeight());
                    editText.setTextColor(Color.BLACK);

                    editText.setTextSize(pixelsToSp(getActivity(), firstEditText.getTextSize()));
                    ll.addView(editText);

                    hashtags.add(editText);
                }
            }
        });

        sendPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String poemText = poemEditText.getText().toString();
                sharedpreferences = getActivity().getSharedPreferences(userPreferences, Context.MODE_PRIVATE);
                String token = sharedpreferences.getString("Token", "");

                String tags="";
                for (EditText e : hashtags) {
                    tags+= e.getText().toString() + " ";
                }

                new PoemSender().execute(token, poemText, tags, mParam1 );

                getActivity().finish();

            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class PoemSender extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("OK")) {
                Toast.makeText(getActivity().getApplicationContext(), "Poem has been sent!", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String token = params[0];
                String poem = params[1];
                String tags = params[2];
                String genre = params[3];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                jsonObject.put("poem", poem);
                jsonObject.put("tags", tags);
                jsonObject.put("genre", genre);
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
