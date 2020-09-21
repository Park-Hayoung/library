package com.example.libraryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment searchFragment;
    Fragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();
        myPageFragment = new MyPageFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new MyNavigationItemSelectedListener());

        // 데이터베이스 오픈
        AppHelper.openDatabase(getApplicationContext(), "library.db");
        createTables();

        userSetting();

        // 초기화면은 도서 목록 프래그먼트
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment).commit();
    }

    class MyNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_search:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, searchFragment).commit();

                    return true;
                case R.id.tab_user:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, myPageFragment).commit();

                    return true;
                default:
                    Toast.makeText(MainActivity.this, "그런 화면은 없습니다.", Toast.LENGTH_SHORT).show();
                    return false;
            }
        }
    }

    private void createTables() {
        AppHelper.createBookTable();
        AppHelper.createMemberTable();
        AppHelper.createRentTable();
        AppHelper.createRentEndTable();
    }

    private void userSetting() {
        SharedPreferences pref
                = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("m_id", "cannondale");
        editor.apply();
    }
}