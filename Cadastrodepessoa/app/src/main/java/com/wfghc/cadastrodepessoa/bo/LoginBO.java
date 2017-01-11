package com.wfghc.cadastrodepessoa.bo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.wfghc.cadastrodepessoa.repository.LoginRepository;
import com.wfghc.cadastrodepessoa.util.Util;
import com.wfghc.cadastrodepessoa.validation.LoginValidation;

/**
 * Created by wesleygoes on 16/04/16.
 */
public class LoginBO {

    private LoginRepository loginRepository;

    public LoginBO(Activity activity){
        loginRepository = new LoginRepository(activity);
    }

    public boolean validarCamposLogin(LoginValidation validation){
        boolean resultado = true;
        if(validation.getLogin() == null || "".equals(validation.getLogin())){
            validation.getEdtLogin().setError("Campo obrigatório");
            Util.showMsgToast(validation.getActivity(), "Campo login obrigatório!");
            resultado = false;
        }else if(validation.getLogin().length() < 2){
            validation.getEdtLogin().setError("Campo deve ter no mínimo 3 caracteres!");
        }

        if(validation.getSenha() == null || "".equals(validation.getSenha())){
            validation.getEdtSenha().setError("Campo obrigatório");
            Util.showMsgToast(validation.getActivity(), "Campo senha obrigatório!");
            resultado = false;
        }

        if(resultado){
            loginRepository.deleteLogin(validation.getLogin(), validation.getSenha());

            if (!validation.getLogin().equals("admin") || !validation.getSenha().equals("admin")) {
                Util.showMsgToast(validation.getActivity(), "Login/Senha inválidos!");
                resultado = false;
            } else {
                SharedPreferences.Editor editor = validation.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE).edit();
                editor.putString("login", validation.getLogin());
                editor.putString("senha", validation.getSenha());
                editor.commit();
            }
        }

        return resultado;
    }
}
