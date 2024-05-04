package com.example.codefestmay32024;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

public class LoginRegisterActivity extends AppCompatActivity
{

    TextView tv_use_pin_code, tv_register_here, tv_login_here;
    MaterialButton btn_register, btn_login;


    AlertDialog.Builder adb_login, adb_register, adb_pin;


    AlertDialog ad_login, ad_register, ad_pin;

    TextInputLayout lo_name, lo_address, lo_bdate, lo_contact, lo_username, lo_password,
            lo_username_login, lo_password_login;

    TextInputEditText et_name, et_address, et_bdate, et_contact, et_username, et_password,
            et_username_login, et_password_login;

    EditText et1, et2, et3, et4;


    DBHelper dbHelper;

    NotificationHelper notificationHelper;

    CheckBox cb_user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        showLoginDialog();

        dbHelper.close();
    }


    private void initValues()
    {
        notificationHelper = new NotificationHelper();
        dbHelper = new DBHelper(this);
    }

    private void showRegisterDialog()
    {
        View registerView = LayoutInflater.from(this).inflate(R.layout.register_dialog_layout, null);
        initRegister(registerView);

        adb_register = new AlertDialog.Builder(this);
        adb_register.setView(registerView);

        ad_register = adb_register.create();
        ad_register.setCancelable(false);
        ad_register.show();

        tv_login_here.setOnClickListener(login ->
        {
            ad_register.dismiss();
            showLoginDialog();
        });

        cb_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    btn_register.setEnabled(true);
                } else
                {
                    btn_register.setEnabled(false);
                }
            }
        });

        lo_bdate.setEndIconOnClickListener(click -> {
            displayDatePicker();
        });

        btn_register.setOnClickListener(register ->
        {

            if(validateRegister())
            {
                registerProcess();
            }
        });

    }


    private void initRegister(View registerView)
    {
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

        btn_register = registerView.findViewById(R.id.btnRegister);

        cb_user = registerView.findViewById(R.id.cbUserAgreement);
    }


    private void showLoginDialog()
    {
        View loginView = LayoutInflater.from(this).inflate(R.layout.login_dialog_layout, null);
        initLogin(loginView);


        adb_login = new AlertDialog.Builder(this);
        adb_login.setView(loginView);

        ad_login = adb_login.create();
        ad_login.setCancelable(false);
        ad_login.show();


        tv_register_here.setOnClickListener(register ->
        {
            ad_login.dismiss();
            showRegisterDialog();
        });

        tv_use_pin_code.setOnClickListener(usePin ->
        {
            ad_login.dismiss();
            showPinDialog();
        });

        btn_login.setOnClickListener(login ->
        {
            if (loginIsNotEmpty())
            {
                String username = et_username_login.getText().toString().trim();
                String password = et_password_login.getText().toString().trim();
                loginProcess(username, password);
            }
        });
    }

    private void initLogin(View loginView)
    {
        btn_login = loginView.findViewById(R.id.btnLogin);

        tv_register_here = loginView.findViewById(R.id.tvJumpToRegister);
        tv_use_pin_code = loginView.findViewById(R.id.tvUsePinCode);

        lo_username_login = loginView.findViewById(R.id.loUsernameLogin);
        lo_password_login = loginView.findViewById(R.id.loPasswordLogin);

        et_username_login = loginView.findViewById(R.id.etUsernameLogin);
        et_password_login = loginView.findViewById(R.id.etPasswordLogin);
    }


    private void showPinDialog()
    {
        View pinView = LayoutInflater.from(this).inflate(R.layout.pin_dialog_layout, null);
        initPin(pinView);

        adb_pin = new AlertDialog.Builder(this);
        adb_pin.setView(pinView)
                .setTitle("Enter your pin code")
                .setPositiveButton("submit", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String str = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
                        pinProcess(str);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ad_pin.dismiss();
                        showLoginDialog();
                    }
                })
                .setCancelable(false);

        ad_pin = adb_pin.create();
        ad_pin.show();


    }

    private void initPin(View pinView)
    {
        et1 = pinView.findViewById(R.id.et1);
        et2 = pinView.findViewById(R.id.et2);
        et3 = pinView.findViewById(R.id.et3);
        et4 = pinView.findViewById(R.id.et4);

        et1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        et2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        et3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() == 1)
                {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }


    private void pinProcess(String inputPin)
    {
        Cursor cursor = dbHelper.findAccountByPin(inputPin);
        if (cursor.moveToFirst())
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
        {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
        }
    }


    private void registerProcess()
    {

        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String bdate = et_bdate.getText().toString();
        String phone = et_contact.getText().toString();
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();


        Random random = new Random();
        String pinCode = String.format("%04d", random.nextInt(1001));
        if (dbHelper.addData(name, address, bdate, phone, username, password, pinCode))
        {
            notificationHelper.showNotification(this, "Codefest", pinCode);
            Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
        }


    }

    private void loginProcess(String username, String password)
    {
        Cursor cursor = dbHelper.findAccount(username, password);
        if (cursor.moveToFirst())
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else
        {

        }
    }


    private boolean loginIsNotEmpty()
    {
        boolean username = et_username_login.getText().toString().isEmpty();
        boolean password = et_password_login.getText().toString().isEmpty();

        if (username || password)
        {
            if (username)
            {
                lo_username_login.setErrorEnabled(true);
                lo_username_login.setError("Username can't be empty");

                et_username_login.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_username_login.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }

            if (password)
            {
                lo_password_login.setErrorEnabled(true);
                lo_password_login.setError("Password can't be empty");

                et_password_login.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_password_login.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }

            return false;
        }

        return true;
    }

    private boolean validateRegister()
    {
        boolean name = et_name.getText().toString().isEmpty();
        boolean address = et_address.getText().toString().isEmpty();
        boolean bdate = et_bdate.getText().toString().isEmpty();
        boolean contact = et_contact.getText().toString().isEmpty();
        boolean user = et_username.getText().toString().isEmpty();
        boolean pass = et_password.getText().toString().isEmpty();


        if(name || address || bdate || contact || user || pass || validateName() || validateAddress() || validateContact() || validateUsername() || validatePassword())
        {
            if(name)
            {
                lo_name.setErrorEnabled(true);
                lo_name.setError("Name can't be empty");

                et_name.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_name.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateName();
            }


            if(address)
            {
                lo_address.setErrorEnabled(true);
                lo_address.setError("Address can't be empty");

                et_address.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_address.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateAddress();
            }


            if(bdate)
            {
                lo_bdate.setErrorEnabled(true);
                lo_bdate.setError("Birthdate can't be empty");

                et_bdate.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_bdate.setErrorEnabled(false);

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }


            if(contact)
            {
                lo_contact.setErrorEnabled(true);
                lo_contact.setError("Contact can't be empty");

                et_contact.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_contact.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });

            }
            else
            {
                validateContact();
            }


            if(user)
            {
                lo_username.setErrorEnabled(true);
                lo_username.setError("Username can't be empty");

                et_username.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_username.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validateUsername();
            }


            if(pass)
            {
                lo_password.setErrorEnabled(true);
                lo_password.setError("Password can't be empty");

                et_password.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_password.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
            }
            else
            {
                validatePassword();
            }


            return false;
        }

        return true;
    }

    private boolean validateName()
    {
        Set<Character> sChars = new HashSet<>();
        Collections.addAll(sChars, '@', '!', ',', '/', '#', '%', '<', '>', '-', '_', ';', '.',
                                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');


        String name = et_name.getText().toString().trim();

        for(int i=0; i<name.length(); i++)
        {
            if(sChars.contains(name.charAt(i)))
            {
                lo_name.setErrorEnabled(true);
                lo_name.setError("Must not contain special character or number");

                et_name.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                        lo_name.setErrorEnabled(false);
                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {

                    }
                });
                return false;
            }
        }

        return true;
    }


    private boolean validateAddress()
    {
        String address = et_address.getText().toString().trim();

        if(address.length() <= 8)
        {
            lo_address.setErrorEnabled(true);
            lo_address.setError("Address must be more than 8 characters long");

            et_address.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_address.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return false;
        }

        return true;
    }

    private boolean validateContact()
    {
        String contact = et_contact.getText().toString().toString();

        if(contact.length() < 11)
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Contact must be 11 digits long");

            et_contact.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_contact.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });

            return false;
        }
        else if(!contact.substring(0,2).equals("09"))
        {
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Contact must start with 09");


            et_contact.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_contact.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }



        return true;
    }


    private boolean validateUsername()
    {
        String username = et_username.getText().toString().trim();

        Pattern upper = Pattern.compile("[A-Z]");
        if(username.length() != 5)
        {
            lo_username.setError("Username must be 5 characters long");

            et_username.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_username.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!upper.matcher(username).find())
        {
            lo_username.setError("Username must contain at least 1 uppercase");

            et_username.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_username.setErrorEnabled(false);

                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }


        return true;
    }


    private boolean validatePassword()
    {
        String password = et_password.getText().toString().trim();

        Pattern upper = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern sChar = Pattern.compile("[@!,?]");

        if(password.length() < 5)
        {
            lo_password.setError("Password must be at least 5 or more characters");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!upper.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 uppercase");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }
        else if(!digit.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 digit");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;

        }
        else if(!sChar.matcher(password).find())
        {
            lo_password.setError("Password must contain at least 1 special character");

            et_password.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    lo_password.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
            return false;
        }


        return true;
    }


    private void displayDatePicker()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                et_bdate.setText(String.format("%04d", year) + "/" + String.format("%02d", (month+1)) + "/" + String.format("%02d", dayOfMonth));
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }








}
