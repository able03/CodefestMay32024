package com.example.codefestmay32024;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

        et_name.setText(AccountStaticClickedModel.getName());
        et_address.setText(AccountStaticClickedModel.getAddress());
        et_bdate.setText(AccountStaticClickedModel.getBdate());
        et_contact.setText(AccountStaticClickedModel.getContact());
        et_username.setText(AccountStaticClickedModel.getUsername());
        et_password.setText(AccountStaticClickedModel.getPassword());

        Log.d("Debugging", "Static ID: " + AccountStaticClickedModel.getId());

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

            @Override
            public void onClick(View view) {
                int id = AccountStaticClickedModel.getId();
                String name = et_name.getText().toString();
                String address = et_address.getText().toString();
                String bdate = et_bdate.getText().toString();
                String contact = et_contact.getText().toString();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                String pin = AccountStaticClickedModel.getPin();

                if(dbHelper.updateData(id, name, address, bdate, contact, username, password ))
                {
                    if(AccountStaticModel.getId() == id)
                    {
                        new AccountStaticModel(id, name, address,bdate, contact, username, password, AccountStaticModel.getPin());
                        startActivity(new Intent(EditAccountDataActivity.this, MainActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(EditAccountDataActivity.this, MainActivity.class));
                    }
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

}