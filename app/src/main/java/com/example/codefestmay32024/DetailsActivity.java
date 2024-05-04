package com.example.codefestmay32024;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codefestmay32024.models.AccountStaticClickedModel;

public class DetailsActivity extends AppCompatActivity {

    private TextView tv_name, tv_address, tv_bday, tv_phone, tv_username, tv_password;
    private ImageView iv_edit, iv_delete;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();


        tv_name.setText(AccountStaticClickedModel.getName());
        tv_address.setText(AccountStaticClickedModel.getAddress());
        tv_bday.setText(AccountStaticClickedModel.getBdate());
        tv_phone.setText(AccountStaticClickedModel.getContact());
        tv_username.setText(AccountStaticClickedModel.getUsername());
        tv_password.setText(AccountStaticClickedModel.getPassword());


        setListener();


        dbHelper.close();
    }

    private void initValues()
    {
        tv_name = findViewById(R.id.tvDetailsName);
        tv_address = findViewById(R.id.tvDetailsAddress);
        tv_bday = findViewById(R.id.tvDetailsBdate);
        tv_phone = findViewById(R.id.tvDetailsPhone);
        tv_username = findViewById(R.id.tvDetailsUsername);
        tv_password = findViewById(R.id.tvDetailsPassword);

        iv_edit = findViewById(R.id.ivEdit);
        iv_delete = findViewById(R.id.ivDelete);

        dbHelper = new DBHelper(this);
    }

    private void setListener()
    {
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, EditAccountDataActivity.class));
            }
        });


        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper.deleteData(AccountStaticClickedModel.getId());
                                finish();
                                startActivity(new Intent(DetailsActivity.this, LoginRegisterActivity.class));
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });
    }
}