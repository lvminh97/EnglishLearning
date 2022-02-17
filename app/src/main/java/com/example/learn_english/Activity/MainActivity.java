package com.example.learn_english.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.learn_english.Database.Model;
import com.example.learn_english.Fragment.ChineseFragment;
import com.example.learn_english.Fragment.EnglishFragment;
import com.example.learn_english.Fragment.TranslateFragment;
import com.example.learn_english.Object.User;
import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final String TAG = "my_MainActivity";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mAuth = FirebaseAuth.getInstance();

        initNavigationDrawer();

        lang = getIntent().getStringExtra("lang");
        if(lang != null && lang.equals("chinese"))
            loadChineseFragment();
        else
            loadEnglishFragment();
    }

    private void initNavigationDrawer(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.rgb(255, 255, 255));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        View hView = navigationView.getHeaderView(0);
        TextView accountTv = hView.findViewById(R.id.tv_account);

        Model.userInfo = new User(mAuth.getCurrentUser().getUid().toString(), mAuth.getCurrentUser().getEmail(), "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("account").document(Model.userInfo.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Model.userInfo.setName(task.getResult().get("fullname").toString());
                    accountTv.setText("Hello " + Model.userInfo.getName());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.it_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);

//                Model model = new Model(MainActivity.this);
//                List<Vocabulary > listVocabulary = model.getListVocabularyByKeyword(query);

//                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
//                intent.putExtra("search_result", (Serializable) listVocabulary);
//                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_english:
                loadEnglishFragment();
                break;
            case R.id.nav_chinese:
                loadChineseFragment();
                break;
            case R.id.nav_translate:
                loadTranslateFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadEnglishFragment(){
        Fragment fragment = new EnglishFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadChineseFragment(){
        Fragment fragment = new ChineseFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadTranslateFragment(){
        Fragment fragment = new TranslateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void logout(){
        mAuth.signOut();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){

    }
}