package com.stishki.iva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stishki.iva.Database.DatabaseManager;
import com.stishki.iva.Entity.Poem;
import com.stishki.iva.Helper.LoginHelper;
import com.stishki.iva.Helper.PoemHelper;
import com.stishki.iva.Service.FetchPoemService;
import com.stishki.iva.View.DesignDemoRecyclerAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    private DrawerLayout mDrawerLayout;
    private DesignDemoPagerAdapter adapter;
    DatabaseManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        manager = new DatabaseManager(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_myaccount: {
                        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.navigation_item_user_poems: {

                    }
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.coordinator), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();
                Intent intent = new Intent(getApplicationContext(), PoemActivity.class);
                startActivity(intent);
            }
        });

        adapter = new DesignDemoPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // TODO СДЕЛАТЬ СЕРВИС, КОТОРЫЙ ЗАБИРАЕТ ДАННЫЕ ИЗ БД И ВСТАВЛЯЕТ В ОКОШЕЧКИ
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    //TODO РЕАЛИЗОВАТЬ ОКОШКО , В КОТОРОМ ПОКАЗЫВАЕТСЯ СТИХ, ЛАЙКИ, ПОЛЬЗОВАТЕЛЬ И ТЭГ
                    //TODO ТУТ ДОЛЖЕН КАЖДЫЙ РАЗ БЫТЬ СЕРВИС
                    case 1:
                        Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(getApplicationContext(), "000", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: {
                        new GetUserPoems().execute();
                        Toast.makeText(getApplicationContext(), "2222", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*Intent intent = new Intent(this, FetchPoemService.class);
        intent.putExtra("getnewpoems", "getnewpoems");
        startService(intent);*/

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Logout logout = new Logout();
            logout.execute();
            return true;
        }
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            //case R.id.action_settings:
            //    return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public static class DesignDemoFragment extends Fragment {
        private static final String TAB_POSITION = "tab_position";
        static DesignDemoRecyclerAdapter adapter;

        public DesignDemoFragment() {

        }

        public static DesignDemoFragment newInstance(int tabPosition) {
            DesignDemoFragment fragment = new DesignDemoFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_POSITION, tabPosition);
            fragment.setArguments(args);
            return fragment;
        }

        public void fillAdapterWithPoems(ArrayList<Poem> poems) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);
            for (Poem p: poems) {
                adapter.add(p);
            }
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int tabPosition = args.getInt(TAB_POSITION);

            switch (tabPosition) {
                case 0: { ArrayList<String> items = new ArrayList<String>();

                    View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
                    RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    Poem poem = new Poem(); poem.setAuthor("Ivan"); poem.setContent("я в следующей жизни стану\n" +
                            "большим красивым мужиком\n" +
                            "и буду защищать девчонок\n" +
                            "и буду страшненьких любить");
                    poem.setLikes(20);
                    poem.setDislikes(20);
                    poem.setGenre("Пирожок");
                    ArrayList<Poem> poems = new ArrayList<>();
                    poems.add(poem);
                    poems.add(poem);
                    poems.add(poem);
                    poems.add(poem);
                    poems.add(poem);
                    poems.add(poem);
                    adapter = new DesignDemoRecyclerAdapter(poems);
                    recyclerView.setAdapter(adapter);


                    return v;
                }
                case 1: {

                    View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
                    RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Poem poem = new Poem(); poem.setAuthor("Ivan"); poem.setContent("я в следующей жизни стану\n" +
                            "большим красивым мужиком\n" +
                            "и буду защищать девчонок\n" +
                            "и буду страшненьких любить");
                    poem.setLikes(20);
                    poem.setDislikes(20);
                    poem.setGenre("Пирожок");
                    ArrayList<Poem> poems = new ArrayList<>();
                    adapter = new DesignDemoRecyclerAdapter(poems);
                    recyclerView.setAdapter(adapter);

                    return v;
                }

                case 2: {
                    ArrayList<String> items = new ArrayList<String>();
                    for (int i = 0; i < 30; i++) {
                        items.add("BlahBlah " + tabPosition + " item #" + i);
                    }

                    View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
                    RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    Poem poem = new Poem(); poem.setAuthor("Ivan"); poem.setContent("я в следующей жизни стану\n" +
                            "большим красивым мужиком\n" +
                            "и буду защищать девчонок\n" +
                            "и буду страшненьких любить");
                    poem.setLikes(20);
                    poem.setDislikes(20);
                    poem.setGenre("Пирожок");
                    ArrayList<Poem> poems = new ArrayList<>();
                    poems.add(poem);
                    recyclerView.setAdapter(new DesignDemoRecyclerAdapter(poems));
                    return v;
                }

            }

            return null;
        }


    }

    private class GetUserPoems extends AsyncTask<String, Void, String>  {
        @Override
        protected String doInBackground(String... params) {
            PoemHelper helper = new PoemHelper();
            String result= "";
            try {
                JSONObject jsonObject = new JSONObject();
                sharedPreferences = getSharedPreferences(LoginActivity.userPreferences, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("Token", "");
                jsonObject.put("token", token);
                String poem = helper.getPoemsByUser(jsonObject);


                JSONObject jsonStrings = new JSONObject(poem);
                jsonObject.get("content");

                result = poem;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



    static class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

        public DesignDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DesignDemoFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }



        @Override
        public CharSequence getPageTitle(int position)
        {
            switch(position) {
                case 0: return "Новое";
                case 1: return "Популярное";
                case 2: return "Подписки";
            }
            return "Не удалось";
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if  (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class Logout extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            if (s.equals("logoutOK")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Could not perform logout", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... params) {
            sharedPreferences = getSharedPreferences(LoginActivity.userPreferences, Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("Token", "");
            String result = LoginHelper.logout(token);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("Token", "");
            editor.apply();
            if (!result.equals("")) {
                return result;
            }
            return result;
        }
    }


}
