package com.appone.jordan.quiznow;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appone.jordan.quiznow.Account.LoginActivity;
import com.appone.jordan.quiznow.Account.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import android.view.Menu;

import static com.appone.jordan.quiznow.R.*;


public class HomeActivity extends AppCompatActivity
{
    /**
     * This Activity is our Main activity. It contains a drawer layout for easy
     * navigation. This activity also uses an adapter to display all quiz items.
     */
    private List<QuizItem> qItems;
    private RecyclerView rv;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    /* Drawer toggle object and defining the layout */
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    /* firebase vars*/
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);

        /* init firebase vars */
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        /*Check if the user is logged in, if not send them to Login*/
        if (firebaseUser == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        rv = findViewById(id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        initData();
        initAdapter();


        /* for the hamburger icon */
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mDrawerList = findViewById(id.navList);

        addDrawerItems();
        setUpDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.d("D", "Triggered" + item);
            return true;
        }

        return true;
    }

    private void setUpDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                string.open_drawer, string.closed_drawer) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(string.options_activity_name);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawerItems()
    {
        //simple_selectable_list_item
        /*
                Need to hardcode -- Strange compilation errors when calling from strings.xml!
         */
        String[] osArray = { "Profile", "About" , "Settings", "Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener((parent, view, position, id) -> {
            //Toast.makeText(HomeActivity.this, "Time for an upgrade!" + id, Toast.LENGTH_SHORT).show();
            if (position == 0)
            {
                // If we are on pos 0 - > Profile
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }else if (position == 1)
            {
                // if we are on position 1 -> About
                startActivity(new Intent(getApplicationContext(), AboutPageActivity.class));
            }else if (position == 2)
            {
                // if 2 -> Settings
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }else if (position == 3)
            {
                // Logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.setting_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initData()
    {
        /*
                These have to be hard coded -- got a strange error when calling from strings.xml
         */
        qItems = new ArrayList<>();
        qItems.add(new QuizItem("AI Quiz","Artificial Intelligence",drawable.ic_action_quiz_name_ai));
    }

    private void initAdapter()
    {
        QuizItemAdapter a = new QuizItemAdapter(qItems);
        rv.setAdapter(a);
    }



}
