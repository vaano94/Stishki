package com.stishki.iva.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.stishki.iva.Fragments.dummy.DummyContent;
import com.stishki.iva.PoemActivity;
import com.stishki.iva.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PoemChooseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PoemChooseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoemChooseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] items;

    public ListViewDemoAdapter getAdapter() {
        return adapter;
    }

    private ListViewDemoAdapter adapter;

    private ArrayList<String> mItems;

    public ArrayList<String> getmItems() {
        return this.mItems;
    }

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public static PoemChooseFragment newInstance(String param1, String param2) {
        PoemChooseFragment fragment = new PoemChooseFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public PoemChooseFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poem_choose,container, false);
        mItems = new ArrayList<>();
        mItems.add("Пирожок");
        mItems.add("Порошок");
        mItems.add("Своё");

        final ListView listView = (ListView) v.findViewById(R.id.poem_choose_item);

        adapter = new ListViewDemoAdapter(getActivity(), mItems);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String item = mItems.get(position);
                Toast.makeText(container.getContext(), item, Toast.LENGTH_SHORT).show();

                PoemDescriptionFragment poemDescriptionFragment = new PoemDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("Position", position);
                poemDescriptionFragment.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction().addToBackStack(null).commit();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemDescriptionFragment).commit();


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private static class ViewHolder {
        TextView test;
    }

    public class ListViewDemoAdapter extends ArrayAdapter<String> {
        public ListViewDemoAdapter(Context context, ArrayList<String> items) {
            super(context, R.layout.for_poem_adapter, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                // inflate the GridView item layout
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.for_poem_adapter, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.test = (TextView) convertView.findViewById(R.id.testfield);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String s = getItem(position);
            viewHolder.test.setText(s);
            return convertView;
        }
    }
}
