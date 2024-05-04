package com.example.codefestmay32024;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codefestmay32024.models.AccountStaticClickedModel;
import com.example.codefestmay32024.models.AccountStaticModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class EditAccountDataActivity extends AppCompatActivity {

    TextInputEditText et_name, et_address, et_bdate, et_contact, et_username, et_password;
    TextInputLayout lo_bdate;
    MaterialButton btn_update, btn_cancel;
    TextView tv_id;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();

//        setData();


        et_name.setText(AccountStaticClickedModel.getName());
        et_address.setText(AccountStaticClickedModel.getAddress());
        et_bdate.setText(AccountStaticClickedModel.getBdate());
        et_contact.setText(AccountStaticClickedModel.getContact());
        et_username.setText(AccountStaticClickedModel.getUsername());
        et_password.setText(AccountStaticClickedModel.getPassword());


        setListeners();

        dbHelper.close();
    }

    private void initValues()
    {
        et_name = findViewById(R.id.etNameU);
        et_address = findViewById(R.id.etAddressU);
        et_bdate = findViewById(R.id.etBirthdateU);
        et_contact = findViewById(R.id.etContactU);
        et_username = findViewById(R.id.etUsernameU);
        et_password = findViewById(R.id.etPasswordU);


        lo_bdate = findViewById(R.id.loBirthdateU);

        btn_update = findViewById(R.id.btnUpdate);
        btn_cancel = findViewById(R.id.btnCancel);

        dbHelper = new DBHelper(this);


        tv_id = findViewById(R.id.tvUpdateId);

    }

    private void setListeners()
    {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            int id = AccountStaticClickedModel.getId();
            String name = et_name.getText().toString();
            String address = et_address.getText().toString();
            String bdate = et_bdate.getText().toString();
            String contact = et_contact.getText().toString();
            String username = et_username.getText().toString();
            String password = et_password.getText().toString();

            String pin = AccountStaticClickedModel.getPin();

            @Override
            public void onClick(View view) {
                if(dbHelper.updateData(AccountStaticClickedModel.getId(), name, address, bdate, contact, username, password ))
                {
                   /* SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT name FROM Accounts WHERE id = '"+id+"'", null);
                    Toast.makeText(EditAccountDataActivity.this, cursor.getString(1), Toast.LENGTH_SHORT).show();

                    db.close();*/

                    new AccountStaticModel(id, name, address, bdate, contact, username, password, pin);
                    new AccountStaticClickedModel(id, name, address, bdate, contact, username, password, pin);
                    startActivity(new Intent(EditAccountDataActivity.this, MainActivity.class));
                    Toast.makeText(EditAccountDataActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EditAccountDataActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        lo_bdate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String year = String.format("%04d", calendar.get(Calendar.YEAR));
                String month = String.format("%02d", calendar.get(Calendar.MONTH));
                String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAccountDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_address.setText(String.format("%04d", i) + "/" + String.format("%02d", i1+1) + "/" + String.format("%02d", i2));
                    }
                },Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }


    private void setData()

    {
        int id = Integer.parseInt(getIntent().getStringExtra("id"));

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WHERE id = '"+id+"'", null);

        int id1 = cursor.getInt(0);
        String name = cursor.getString(1);
        String address = cursor.getString(2);
        String bdate = cursor.getString(3);
        String contact = cursor.getString(4);
        String user = cursor.getString(5);
        String pass = cursor.getString(6);
        String pin = cursor.getString(7);


        tv_id.setText(String.valueOf(id1));
        et_name.setText(name);
        et_address.setText(address);
        et_bdate.setText(bdate);
        et_contact.setText(contact);
        et_username.setText(user);
        et_password.setText(pass);
    }

}