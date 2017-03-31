package edu.fameg.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login2Activity extends AppCompatActivity {

    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

    }

    public void logar(View v){
        Intent intent = new Intent(this,ViagemActivity.class);
        startActivity(intent);
    }

}
