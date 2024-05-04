package com.example.codefestmay32024;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.codefestmay32024.models.AccountStaticModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LoginRegisterActivity extends AppCompatActivity {

    TextView tv_use_pin_code, tv_register_here, tv_login_here;
    MaterialButton btn_register, btn_login;



    AlertDialog.Builder adb_login, adb_register, adb_pin;


    AlertDialog ad_login, ad_register, ad_pin;

    View loginView, registerView, pinView;

    TextInputLayout lo_name, lo_address, lo_bdate, lo_contact, lo_username, lo_password,
            lo_username_login, lo_password_login;

    TextInputEditText et_name, et_address, et_bdate, et_contact,et_username, et_password,
            et_username_login, et_password_login;


    DBHelper dbHelper;

    NotificationHelper notificationHelper;

    CheckBox cb_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initAlertBuilder();
        setView();

        initValues();

         setListener();



         start();
    }

    private void start()
    {
        adb_login.setView(loginView);
        adb_login.setCancelable(false);
        showLogin();
    }

    private void initValues()
    {

        btn_register = registerView.findViewById(R.id.btnRegister);
        btn_login = loginView.findViewById(R.id.btnLogin);

        tv_register_here = loginView.findViewById(R.id.tvJumpToRegister);
        tv_use_pin_code = loginView.findViewById(R.id.tvUsePinCode);

        tv_login_here = registerView.findViewById(R.id.tvJumpToLogin);

        lo_name = registerView.findViewById(R.id.loName);
        lo_address = registerView.findViewById(R.id.loAddress);
        lo_bdate = registerView.findViewById(R.id.loBirthdate);
        lo_contact = registerView.findViewById(R.id.loContact);
        lo_username = registerView.findViewById(R.id.loUsernameRegis);
        lo_password = registerView.findViewById(R.id.loPasswordRegis);

        et_name = registerView.findViewById(R.id.etName);
        et_address = registerView.findViewById(R.id.etAddress);
        et_bdate = registerView.findViewById(R.id.etBirthdate);
        et_contact = registerView.findViewById(R.id.etContact);
        et_username = registerView.findViewById(R.id.etUsernameRegis);
        et_password = registerView.findViewById(R.id.etPasswordRegis);

        lo_username_login = loginView.findViewById(R.id.loUsernameLogin);
        lo_password_login = loginView.findViewById(R.id.loPasswordLogin);

        et_username_login = loginView.findViewById(R.id.etUsernameLogin);
        et_password_login = loginView.findViewById(R.id.etPasswordLogin);


        cb_user = registerView.findViewById(R.id.cbUserAgreement);

        notificationHelper = new NotificationHelper();
    }

    private void initAlertBuilder()
    {
        adb_login = new AlertDialog.Builder(this);
        adb_register = new AlertDialog.Builder(this);
        adb_pin = new AlertDialog.Builder(this);
    }

    private void setView()
    {
        loginView = LayoutInflater.from(this).inflate(R.layout.login_dialog_layout, null);
        registerView = LayoutInflater.from(this).inflate(R.layout.register_dialog_layout, null);
        pinView = LayoutInflater.from(this).inflate(R.layout.pin_dialog_layout, null);
    }


    private void showLogin()
    {
        ad_login = adb_login.show();
    }

    private void dismissRegister()
    {
        ad_register.dismiss();
    }

    private void dismissPin()
    {
        ad_pin.dismiss();
    }


    private void dismissLogin()
    {
        ad_login.dismiss();
    }

    private void showRegister()
    {


        ad_register = adb_register.show();
    }

    private void showPin()
    {
        ad_pin = adb_pin.show();
    }



    private void setListener()
    {
        tv_use_pin_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adb_pin.setView(pinView);
                dismissLogin();
                showPin();
            }
        });


        tv_register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adb_register.setView(registerView);
                dismissLogin();
                showRegister();
            }
        });


        tv_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                adb_login.setView(loginView);
                dismissRegister();
                showLogin();
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!empty() && !validate())
               {
                 registerProcess();
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(LoginRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_bdate.setText(String.format("%04d", i) + "/" + String.format("%02d", i1+1) + "/" + String.format("%02d", i2));
                    }
                },Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });



        lo_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String year = String.format("%04d", calendar.get(Calendar.YEAR));
                String month = String.format("%02d", calendar.get(Calendar.MONTH));
                String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));


                DatePickerDialog datePickerDialog = new DatePickerDialog(LoginRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                        et_bdate.setText(String.valueOf(String.format("%04d", year1)) + "/" + String.format("%02d", (month1+1)) + "/" + String.format("%02d", day1));
                    }
                },Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoginUsername() || checkLoginPassword())
                {
                    dbHelper =  new DBHelper(LoginRegisterActivity.this);
                    String username = et_username_login.getText().toString();
                    String password = et_password_login.getText().toString();
                    Cursor cursor = dbHelper.findAccount(username, password);
                    if(cursor.moveToFirst())
                    {
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String address = cursor.getString(2);
                        String bdate = cursor.getString(3);
                        String contact = cursor.getString(4);
                        String user = cursor.getString(5);
                        String pass = cursor.getString(6);
                        String pin = cursor.getString(7);

                        new AccountStaticModel(id, name, address, bdate, contact, user, pass, pin);
                        startActivity(new Intent(LoginRegisterActivity.this, MainActivity.class));
                    }



                    dbHelper.close();
                }
            }
        });

        cb_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    btn_register.setEnabled(true);
                }
                else
                {
                    btn_register.setEnabled(false);
                }
            }
        });
    }
    private void registerProcess()
    {
        dbHelper = new DBHelper(this);
        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String bdate = et_bdate.getText().toString();
        String phone = et_contact.getText().toString();
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();


        Random random = new Random();
        String pinCode = String.format("%04d", random.nextInt(1001));
        if(dbHelper.addData(name, address, bdate, phone, username, password, pinCode))
        {
            notificationHelper.showNotification(this, "Codefest", pinCode);
            Toast.makeText(this, "Successfully login", Toast.LENGTH_SHORT).show();
        }




        dbHelper.close();
    }

    private boolean checkLoginUsername() {
        boolean uname = et_username_login.getText().toString().isEmpty();
        if (uname) {
            lo_username_login.setErrorEnabled(true);
            lo_username_login.setError("Username can't be empty");

            et_username_login.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_username_login.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            return false;

        }
        return true;

    }


    private boolean checkLoginPassword()
    {

        boolean pass = et_password_login.getText().toString().isEmpty();

            if(pass)
            {
                lo_password_login.setErrorEnabled(true);
                lo_password_login.setError("Password can't be empty");


                et_password_login.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        lo_password_login.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }

                });
                return false;
            }


        return true;

    }





    private boolean checkNameIfEmpty()
    {

        boolean nameEmpty = et_name.getText().toString().isEmpty();

        if(nameEmpty)
        {

            lo_name.setErrorEnabled(true);
            lo_name.setError("Name can't be empty");

            et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_name.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            return false;

        }




        return true;
    }

    private boolean validate()
    {
        if(nameValidate() | checkAddressLength())
        {
            return false;
        }
        return true;
    }

    private boolean empty()
    {
        if(checkNameIfEmpty() | checkAddressIfEmpty() | checkBDateIfEmpty() |
                phoneEmpty() | usernameEmpty() | checkPasswordEmpty())
        {
            return false;
        }
        return true;
    }




    private boolean checkAddressIfEmpty()
    {
        boolean address = et_address.getText().toString().isEmpty();

        if(address)
        {
            lo_address.setErrorEnabled(true);
            lo_address.setError("Address can't be empty");


            et_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_address.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
        return true;
    }









    private boolean checkBDateIfEmpty()
    {
        boolean bdate = et_bdate.getText().toString().isEmpty();

        if(bdate)
        {
            lo_bdate.setErrorEnabled(true);
            lo_bdate.setError("Birthdate can't be empty");

            et_bdate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_bdate.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
        return true;
    }

    private boolean phoneEmpty()
    {
        boolean phone = et_contact.getText().toString().isEmpty();

        if(phone)
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Contact can't be empty");


            et_contact.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_contact.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
        return true;
    }




    private boolean usernameEmpty()
    {
        boolean username = et_username.getText().toString().isEmpty();

        if(username)
        {
            lo_username.setErrorEnabled(true);
            lo_username.setError("username can't be empty");


            et_username.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_username.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return false;
        }
        return true;
    }

    private boolean checkPasswordEmpty()
    {
        boolean pass = et_password.getText().toString().isEmpty();


        if(pass)
        {
            lo_password.setErrorEnabled(true);
            lo_password.setError("Password can't be empty");

            et_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            return false;
        }


        return true;
    }







    private boolean nameValidate()
    {
        Set<Object> spec_char = new HashSet<>();
        Collections.addAll(spec_char, "@", "!", "#", "%", "<", ">", "-", "_", ",", ";",
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        String name = et_name.getText().toString();

        for(int i=0; i<name.length(); i++)
        {
            if(spec_char.contains(name.charAt(i)))
            {
                lo_name.setErrorEnabled(true);
                lo_name.setError("Must not contain special characters");
                et_name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        lo_name.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                return false;
            }
        }

        return  true;
    }



    private boolean checkAddressLength()
    {
        String address =et_address.getText().toString();

        if(address.length() > 8)
        {
            lo_address.setErrorEnabled(true);
            lo_address.setError("must be more than 8 characters long");



            et_address.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    lo_address.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            return true;
        }
        return false;
    }




    private boolean checkPhone()
    {
        if(!et_contact.getText().toString().substring(0,3).equals("09"))
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Phone number must start with 09");
            return false;
        }
        return true;
    }






}




    /*private void start() {
        adb_login.setView(loginView);
        adb_register.setView(registerView);
        adb_pin.setView(pinView);
        adb_login.setCancelable(false);
        showLogin();
    }*/

    /*private void setView() {
        loginView = LayoutInflater.from(this).inflate(R.layout.login_dialog_layout, null);
        registerView = LayoutInflater.from(this).inflate(R.layout.register_dialog_layout, null);
        pinView = LayoutInflater.from(this).inflate(R.layout.pin_dialog_layout, null);
    }*/

    /*private void setListener() {
        tv_use_pin_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissLogin();
                showPin();
            }
        });

        tv_register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissLogin();
                showRegister();
            }
        });

        tv_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissRegister();
                showLogin();
            }
        });
    }*/
