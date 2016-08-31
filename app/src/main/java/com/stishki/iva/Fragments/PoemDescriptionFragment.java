package com.stishki.iva.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stishki.iva.Fragments.dummy.DummyContent;
import com.stishki.iva.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PoemDescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PoemDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoemDescriptionFragment extends Fragment implements PoemChooseFragment.OnFragmentInteractionListener {
    private static final String ARG_PARAM1 = "Position";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private String mParam2;
    public TextView poem_name;
    public TextView poem_description;

    private OnFragmentInteractionListener mListener;

    public static PoemDescriptionFragment newInstance(String param1, String param2) {
        PoemDescriptionFragment fragment = new PoemDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PoemDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poem_description, container, false);

        poem_name = (TextView) v.findViewById(R.id.Poem_Description_Label);
        poem_description = (TextView) v.findViewById(R.id.poem_description);
        poem_name.setText(DummyContent.poems.get(mParam1));
        poem_description.setText(DummyContent.poemDescriptions.get(mParam1));

        final Button okay = (Button) v.findViewById(R.id.description_okay_button);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putString("Genre", DummyContent.poems.get(mParam1));

                PoemWritingFragment poemWritingFragment = new PoemWritingFragment();

                poemWritingFragment.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().addToBackStack(null).commit();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemWritingFragment).commit();
            }
        });


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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
