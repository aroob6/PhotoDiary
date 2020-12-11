package com.example.photodiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    Button listfrag, addfrag, userfrag;
    BottomNavigationView navView;

    ConstraintLayout maincontainer;
    FragmentManager fragmentManager;
    ListFragment listFragment;
    SettingFragment settingFragment;

    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        maincontainer = findViewById(R.id.maincontainer);

        fragmentManager = getSupportFragmentManager();
        listFragment = new ListFragment();
        settingFragment = new SettingFragment();

        navView = findViewById(R.id.nav_view);

        intent = getIntent();
        int restart = intent.getIntExtra("restart",1);
        if(restart != 10) {

            intent = new Intent(this, LoadingPageActivity.class);
            startActivity(intent);
        }





        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, listFragment).commitAllowingStateLoss();

        //bottom
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.gridfrag:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, listFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.settingfrag:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, settingFragment).commitAllowingStateLoss();
                        return true;
                    default:
                        return false;
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:
                intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }
}