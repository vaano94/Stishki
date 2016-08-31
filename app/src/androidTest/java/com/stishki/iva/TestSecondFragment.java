package com.stishki.iva;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;

import com.stishki.iva.Fragments.PoemDescriptionFragment;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ivan on 12/20/2015.
 */
public class TestSecondFragment extends ActivityInstrumentationTestCase2<TestFragmentActivity> {

    private TestFragmentActivity mActivity;
    private PoemDescriptionFragment fragment;
    public TestSecondFragment() {
        super(TestFragmentActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        fragment = new PoemDescriptionFragment();
        getActivity().getFragmentManager().beginTransaction().add(R.id.activity_test_fragment_linearlayout, fragment, null).commitAllowingStateLoss();
    }

    @Test
    public void testA() {
        assertNotNull(fragment.getView().findViewById(R.id.Poem_Description_Label));
    }

}
