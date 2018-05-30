package foodrecepies.practise.com.foodrecepies.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import foodrecepies.practise.com.foodrecepies.R;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeContract;
import foodrecepies.practise.com.foodrecepies.data.db.FoodRecipeDbHelper;
import foodrecepies.practise.com.foodrecepies.data.sp.PersistentData;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        TextView loginTitle=(TextView)findViewById(R.id.login_title);
        Typeface customFont = Typeface.createFromAsset(getAssets(),  "fonts/caviardreams.ttf");
        loginTitle.setTypeface(customFont);
        LoginListener loginListener = new LoginListener();
        email = (EditText) findViewById(R.id.email);
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
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(loginListener);
        TextView signUp = (TextView) findViewById(R.id.sign_up);
        signUp.setOnClickListener(loginListener);
        //todo have to implement permission
    }

    private class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login:
                    Cursor cursor = new FoodRecipeDbHelper(LoginActivity.this).authenticateUser(new String[]{email.getText().toString(), password.getText().toString()});
                    if (cursor.getCount() > 0) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        PersistentData persistentData = new PersistentData();
                        persistentData.setLoginValid(LoginActivity.this, true);
                        if (cursor.moveToFirst()) {
                            persistentData.setID(LoginActivity.this, cursor.getInt(cursor.getColumnIndex(FoodRecipeContract.COLUMN_USER_ID)));
                        }
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Login credentials", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.sign_up:
                    Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
