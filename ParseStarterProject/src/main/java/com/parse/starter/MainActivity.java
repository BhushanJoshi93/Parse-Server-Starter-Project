/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean signUpModeActive = true;
    TextView loginTextView;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logInTextView){
            //TOGGLE FOR SIGN IN AND SIGN UP
            Button signUpButton = (Button) findViewById(R.id.signUpButton);

            if (signUpModeActive){
                signUpModeActive = false;
                signUpButton.setText("Sign In");
                loginTextView.setText("Sign Up");

            }
            else {
                signUpModeActive = true;
                signUpButton.setText("Sign Up");
                loginTextView.setText("Sign In");
            }
        }
    }
  public void signUpClicked(View view){
    EditText userNameEditText = (EditText) findViewById(R.id.userName);
    EditText passwordEditText = (EditText) findViewById(R.id.password);

    if (userNameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
      Toast.makeText(this, "Username or Password is required", Toast.LENGTH_SHORT).show();
    }

    else {
        if (signUpModeActive) {
            // SIGN UP SITUATION
            ParseUser user = new ParseUser();
            user.setUsername(userNameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Signed Up", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            //SIGN IN SITUATION
            ParseUser.logInInBackground(userNameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

// GETTING ALL THE OBJECTS FROM THE PARSER
//
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        if (e == null){
//          if(objects.size()>0){
//            for (ParseObject object: objects){
//              Log.i("Username",object.getString("Username"));
//              Log.i("score",object.getString("score"));
//            }
//          }
//        }
//      }
//    });

    //Currently logged in User

//    if (ParseUser.getCurrentUser() != null){
//      Log.i("User is logged in",ParseUser.getCurrentUser().getUsername());
//
//    }
//    else {
//      Log.i("No one Logged in","NONE");
//    }


      loginTextView = (TextView) findViewById(R.id.logInTextView);
      loginTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }



}