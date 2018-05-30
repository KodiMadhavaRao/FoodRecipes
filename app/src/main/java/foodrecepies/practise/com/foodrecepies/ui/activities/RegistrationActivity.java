package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.model.Registration;

public class RegistrationActivity extends AppCompatActivity {

    private EditText displayName;
    private EditText email;
    private EditText password;
    private String gender = "";
    private EditText dateOfBirth;
    private String country = "";
    private EditText confirmPassword;
    private boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        RegistrationListeneer registrationListeneer = new RegistrationListeneer();
        TextView signUpTitle=(TextView)findViewById(R.id.sign_up_title);
        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/caviardreams.ttf");
        signUpTitle.setTypeface(customFont);
        displayName = (EditText) findViewById(R.id.reg_display_name);
        email = (EditText) findViewById(R.id.reg_email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0) {
                    email.setError(null);
                } else {
                    email.setError("Invalid Email");
                }

            }
        });
        password = (EditText) findViewById(R.id.reg_password);
        confirmPassword = (EditText) findViewById(R.id.reg_confirm_password);
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String sPassword = password.getText().toString();
                if (editable.length() > 0 && sPassword.length() > 0) {
                    if (!confirmPassword.getText().toString().equals(sPassword)) {
                        confirmPassword.setError("Password doesnt match");
                    } else {
                        confirmPassword.setError(null);
                    }

                }
            }
        });
        Spinner genders = (Spinner) findViewById(R.id.reg_gender);
        genders.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Gender", "Male", "Female"}));
        genders.setOnItemSelectedListener(registrationListeneer);
        dateOfBirth = (EditText) findViewById(R.id.reg_date_of_birth);
        dateOfBirth.setOnClickListener(registrationListeneer);
        Spinner countries = (Spinner) findViewById(R.id.reg_countries);
        countries.setOnItemSelectedListener(registrationListeneer);
        String[] countriesArray = getResources().getStringArray(R.array.countries_array);
        countries.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countriesArray));
        Button sign_in = (Button) findViewById(R.id.reg_sign_in);
        sign_in.setOnClickListener(registrationListeneer);
        TextView login = (TextView) findViewById(R.id.reg_login);
        login.setOnClickListener(registrationListeneer);
    }

    private class RegistrationListeneer implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reg_sign_in:
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        if (gender.equals("")) {
                            ((TextView) ((Spinner) findViewById(R.id.reg_gender)).getSelectedView()).setError("Error message");
                        } else if (country.equals("")) {
                            ((TextView) ((Spinner) findViewById(R.id.reg_countries)).getSelectedView()).setError("Error message");
                        } else {
                            Registration registration = new Registration();
                            registration.setDisplayName(displayName.getText().toString().trim());
                            registration.setEmail(email.getText().toString().trim());
                            registration.setPassword(password.getText().toString().trim());
                            registration.setGender(gender);
                            registration.setBirthday(dateOfBirth.getText().toString().trim());
                            registration.setCountry(country);
                            FoodRecipeDbHelper foodRecipeDbHelper = new FoodRecipeDbHelper(RegistrationActivity.this);
                            foodRecipeDbHelper.userRegistration(registration);
                            Toast.makeText(RegistrationActivity.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        confirmPassword.setError("Password dosn't match");
                    }
                    break;
                case R.id.reg_login:
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);

                    break;
                case R.id.reg_date_of_birth:
                    Calendar instance = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dateOfBirth.setText(dayOfMonth + "/" + month + "/" + year);
                        }
                    }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE));
                    dialog.show();
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.reg_gender:
                    ((TextView) ((Spinner) findViewById(R.id.reg_gender)).getSelectedView()).setTextColor(Color.LTGRAY);
                    if (position == 1)
                        gender = "Male";
                    else
                        gender = "Female";
                    break;

                case R.id.reg_countries:
                    ((TextView) ((Spinner) findViewById(R.id.reg_countries)).getSelectedView()).setTextColor(Color.LTGRAY);
                    String[] countriesArray = getResources().getStringArray(R.array.countries_array);
                    country = countriesArray[position];
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
