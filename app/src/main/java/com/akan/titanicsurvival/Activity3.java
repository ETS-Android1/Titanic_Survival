package com.akan.titanicsurvival;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity3 extends AppCompatActivity {
    EditText Parch, SibSp, Age, Fare;
    RadioButton male,female, checkBox,checkBox2,Q,S;
    Button predict;
    private String url = "https://survival-titanic.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Parch = findViewById(R.id.Parch);
        SibSp = findViewById(R.id.SibSp);
        Age = findViewById(R.id.Age);
        Fare = findViewById(R.id.Fare);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        Q = findViewById(R.id.Q);
        S = findViewById(R.id.S);
        male = findViewById(R.id.Male);
        female = findViewById(R.id.Female);
        predict = findViewById(R.id.button2);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("Survived");
                                    Intent intent = new Intent(Activity3.this, Result.class);
                                    String prediction;
                                    if(data.equals("1")){
                                        prediction = "Survived";
                                    }
                                    else{
                                        prediction = "Failed to Survive!";
                                    }
                                    intent.putExtra("Prediction",prediction);
                                    startActivity(intent);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Activity3.this, "Connection Error!", Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        String Pclass = PClass();
                        String Port_Q = PortQ();
                        String Port_S = PortS();
                        String Male = isMale();
                        Map<String,String> params = new HashMap<>();

                        params.put("Pclass",Pclass);
                        params.put("Age",Age.getText().toString());
                        params.put("SibSp",SibSp.getText().toString());
                        params.put("Parch",Parch.getText().toString());
                        params.put("Fare",Fare.getText().toString());
                        params.put("male",Male);
                        params.put("Q",Port_Q);
                        params.put("S",Port_S);
                        Log.i("male",Male);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(Activity3.this);
                queue.add(stringRequest);
            }
        });
    }
    public String PClass(){
        if(checkBox.isChecked()){
            return "1";
        }
        else if(checkBox2.isChecked()){
            return "2";
        }
        else{
            return "3";
        }
    }
    public String PortQ(){
        if(Q.isChecked()){
            return "1";
        }
        return "0";
    }
    public String PortS(){
        if(S.isChecked()){
            return "1";
        }
        return "0";
    }
    public String isMale(){
        if(male.isChecked()){
            return "1";
        }
        return "0";
    }

}