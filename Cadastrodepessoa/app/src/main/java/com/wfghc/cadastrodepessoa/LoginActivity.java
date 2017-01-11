package com.wfghc.cadastrodepessoa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wfghc.cadastrodepessoa.bo.LoginBO;
import com.wfghc.cadastrodepessoa.validation.LoginValidation;


public class LoginActivity extends AppCompatActivity {

    public static boolean HAS_PLAYED_ANIMATION = false;
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogar;
    private LoginBO loginBO;

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBO = new LoginBO(this);

        getSupportActionBar().hide();

        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String login = preferences.getString("login",null);
        String senha = preferences.getString("senha",null);
        if(login != null && senha != null){
            Intent intent =  new Intent(LoginActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
        }

        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
    }



    public void clickLogar(View view){
        String txtLogin = edtLogin.getText().toString();
        String txtSenha = edtSenha.getText().toString();

        LoginValidation validation = new LoginValidation();
        validation.setActivity(LoginActivity.this);
        validation.setEdtLogin(edtLogin);
        validation.setEdtSenha(edtSenha);
        validation.setLogin(txtLogin);
        validation.setSenha(txtSenha);

        boolean isValid =  loginBO.validarCamposLogin(validation);

        if(isValid){
            Intent intent =  new Intent(LoginActivity.this,DashBoardActivity.class);
            startActivity(intent);
            finish();
            this.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
        }
    }


}
