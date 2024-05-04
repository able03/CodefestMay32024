package com.example.codefestmay32024;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codefestmay32024.models.AccountModel;
import com.example.codefestmay32024.models.AccountStaticModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rv_search;
    SearchView searchView;
    AccountAdapter adapter;
    List<AccountModel> mainlist;
    List<AccountModel> sublist;
    DBHelper dbHelper;
    MaterialButton btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initValues();
        setListener();
        dbHelper.close();

    }

    private void searchList(String s)
    {

        for(AccountModel account : mainlist)
        {
            if(account.getName().toLowerCase().contains(s.toLowerCase()))
            {
                sublist.add(account);
            }
        }

        if(!sublist.isEmpty())
        {
            populateRV();
        }


    }

    private void initValues()
    {
        rv_search = findViewById(R.id.rvSearch);
        dbHelper = new DBHelper(this);
        mainlist = dbHelper.getAllList();
        searchView = findViewById(R.id.searchViewS);
        sublist = new ArrayList<>();
        btn_search = findViewById(R.id.btnSearchS);
    }

    private void setListener()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchList(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty())
                {
                    clearSubList();
                }
                return true;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void clearSubList()
    {
        sublist.clear();
        populateRV();
    }

    private void populateRV()
    {
        adapter = new AccountAdapter(this);
        adapter.setAccountList(sublist);
        rv_search.setAdapter(adapter);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
    }
}