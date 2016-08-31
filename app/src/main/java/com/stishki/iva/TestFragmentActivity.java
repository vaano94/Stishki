package com.stishki.iva;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.stishki.iva.Fragments.PoemDescriptionFragment;

public class TestFragmentActivity extends FragmentActivity implements PoemDescriptionFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout view = new LinearLayout(this);
        view.setId(R.id.activity_test_fragment_linearlayout);

        setContentView(view);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
