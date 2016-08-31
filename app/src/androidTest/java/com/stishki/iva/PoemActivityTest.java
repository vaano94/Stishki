package com.stishki.iva;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stishki.iva.Entity.Poem;
import com.stishki.iva.Fragments.PoemChooseFragment;
import com.stishki.iva.Fragments.PoemDescriptionFragment;
import com.stishki.iva.Fragments.PoemWritingFragment;
import com.stishki.iva.Helper.PoemHelper;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;



/**
 * Created by Ivan on 12/19/2015.
 */
public class PoemActivityTest extends ActivityInstrumentationTestCase2<PoemActivity> {

    private PoemActivity mActivity;
    private PoemChooseFragment mFragment;

    public PoemActivityTest() {
        super(PoemActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mFragment = mActivity.poemChooseFragment;
    }

    @Before
    public void testPreConditions() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
    }

    public void testFirstFragment() {
        // access any public members of myFragment to test
        //check ArrayList is created
        assertEquals(mFragment.getmItems().get(0), "Пирожок");
        //check Adapter is instantiated
        assertNotNull(mFragment.getAdapter());
        // check adapter is filled with data
        assertEquals(mFragment.getAdapter().getCount(), mFragment.getmItems().size());

        // проверка, что все элементы отображаются
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.fragment_what_to_write));
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.poem_choose_item));



        // test bundle works here
        Bundle bundle = new Bundle();
        bundle.putInt("Position", 1);
        assertNotNull(bundle);

        PoemDescriptionFragment poemDescriptionFragment = new PoemDescriptionFragment();
        poemDescriptionFragment.setArguments(bundle);
        //mFragment.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction().addToBackStack(null).commitAllowingStateLoss();
        getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemDescriptionFragment).commitAllowingStateLoss();

        Bundle bundle1 = poemDescriptionFragment.getArguments();
        // проверка одинаковости передаваемых данных
        assertEquals(bundle, bundle1);

        assertEquals(bundle.getInt("Position"), bundle1.getInt("Position"));

    }

    public void testSecondFragment() {
        PoemDescriptionFragment poemDescriptionFragment = new PoemDescriptionFragment();
        //проверка, что фрагмент инициализировался
        assertNotNull(poemDescriptionFragment);

        // проверка, что текст надписи отображается корректно
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), mActivity.findViewById(R.id.poem_choose_item));
        mActivity.getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemDescriptionFragment).commitAllowingStateLoss();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Видим ли мы кнопку на экране
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.description_okay_button));
        // Видим ли мы текст о том как писать стишок на экране
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.poem_description));
        // Видим ли мы название стишка в фрагменте
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.Poem_Description_Label));

        // проверка, что можно кликнуть
        TouchUtils.clickView(this, getActivity().findViewById(R.id.description_okay_button));

        // и перейти в другую активность
        PoemWritingFragment poemWritingFragment = new PoemWritingFragment();
        assertNotNull(poemWritingFragment);
        mActivity.getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemWritingFragment).commitAllowingStateLoss();

    }

    public void testThirdFragment() {

        PoemWritingFragment poemWritingFragment = new PoemWritingFragment();
        assertNotNull(poemWritingFragment);
        mActivity.getFragmentManager().beginTransaction().replace(R.id.fragment_container, poemWritingFragment).commitAllowingStateLoss();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Видим ли мы элементы ввода текста на экране
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.PoemEditorFragment));
        // slk
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.PoemSendButton));
        // dsf
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.textView2));
        // asd
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.textView3));
        // asdsad
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.add_tag_button));
        // asd
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.first_edit_Text));


        TouchUtils.clickView(this, getActivity().findViewById(R.id.first_edit_Text));
        getActivity().findViewById(R.id.first_edit_Text);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // проверим, что при нажатии тега окно никуда не исчезает
        ViewAsserts.assertOnScreen(getActivity().getWindow().getDecorView(), getActivity().findViewById(R.id.PoemEditorFragment));
    }

    public void testAsyncTask() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);
        final AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            public boolean correctly = false;
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
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String s) {

                if (s.equals("OK")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Poem has been sent!", Toast.LENGTH_SHORT).show();
                    correctly = true;
                    assertEquals("OK",s);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not send poem", Toast.LENGTH_SHORT).show();
                    correctly = false;
                    assertFalse(correctly);
                }
                super.onPostExecute(s);

                signal.countDown();

            }
        };

        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                asyncTask.execute("UXE=1450608546",
                        "Слепцы стоят на дороге. Их веки - \n" +
                                " Как нависшие пологи. Вдалеке \n" +
                                " Воскресный звонс отстроверхой колокольни \n" +
                                " Мягко качается над полями",
                        "",
                        "Pirozhok");
            }

        });

        signal.await(30, TimeUnit.SECONDS);
        Thread.sleep(5000);

        final AsyncTask<String, Void, String> asyncTask2 = new AsyncTask<String, Void, String>() {
            public boolean correctly = false;
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
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String s) {

                if (s.equals("OK")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Poem has been sent!", Toast.LENGTH_SHORT).show();
                    correctly = true;
                    assertEquals("OK",s);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not send poem", Toast.LENGTH_SHORT).show();
                    correctly = false;
                    assertFalse(correctly);
                }
                super.onPostExecute(s);

                signal.countDown();

            }
        };

        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                asyncTask2.execute("",
                        "",
                        "",
                        "Pirozhok");
            }

        });

        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!

    }


}