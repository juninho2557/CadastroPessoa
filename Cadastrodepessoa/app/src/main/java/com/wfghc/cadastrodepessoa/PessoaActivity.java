package com.wfghc.cadastrodepessoa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.wfghc.cadastrodepessoa.entidade.enums.ProfissaoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.SexoEnum;
import com.wfghc.cadastrodepessoa.entidade.enums.TipoPessoaEnum;
import com.wfghc.cadastrodepessoa.entidade.model.Pessoa;
import com.wfghc.cadastrodepessoa.fragment.DatePickerFragment;
import com.wfghc.cadastrodepessoa.repository.PessoaRepository;
import com.wfghc.cadastrodepessoa.util.Mask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PessoaActivity extends AppCompatActivity {

    private Spinner         spnProfissao;
    private EditText        edtNome;
    private EditText        edtEndereco;
    private EditText        edtData;
    private EditText        edtCpfCnpj;
    private RadioGroup      rbgCpfCnpj;
    private RadioGroup      rbgSexo;
    private RadioButton     rbtCPF;
    private TextWatcher     cpfmaks;
    private TextWatcher     cnpjMask;
    private int             cpfCnpjSelecionado;
    private PessoaRepository pessoaRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        pessoaRepository    = new PessoaRepository(this);
        getSupportActionBar().setTitle("Adicinar Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binds();
        initProfissoes();
    }

    private void binds() {

        spnProfissao        = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj          = (EditText) findViewById(R.id.edtCPF);
        edtNome             = (EditText) findViewById(R.id.edtNome);
        edtEndereco         = (EditText) findViewById(R.id.edtEndereco);
        edtData             = (EditText) findViewById(R.id.edtDate);
        rbgCpfCnpj          = (RadioGroup) findViewById(R.id.rdgTypePeople);
        rbgSexo             = (RadioGroup) findViewById(R.id.rdgSexo);
        rbtCPF              = (RadioButton) findViewById(R.id.rbtCpf);

        cpfmaks             = Mask.insert("###.###.###-##",edtCpfCnpj);
        edtCpfCnpj          .addTextChangedListener(cpfmaks);

        cnpjMask            = Mask.insert("##.###.###/####-##",edtCpfCnpj);

        //Ação nos radio buttons que adiciona mascara segundo a escolha de cpf ou cnpj e limpa os campos quando muda de opção.
        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.setText(null);
                edtCpfCnpj.requestFocus();
                if(rbgCpfCnpj.getCheckedRadioButtonId() == rbtCPF.getId()){
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfmaks);
                }else{
                    edtCpfCnpj.removeTextChangedListener(cpfmaks);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                }

            }
        });

        //Limpa os campos quando perde o foco e é clicado no radioburrion
        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(rbgCpfCnpj.getCheckedRadioButtonId() == rbtCPF.getId()){
                    if(edtCpfCnpj.getText().length() <14){
                        edtCpfCnpj.setText("");
                    }
                }else{
                    if(edtCpfCnpj.getText().length() <18){
                        edtCpfCnpj.setText("");
                    }
                }
            }
        });

    }

    public void dataNascimentoOnClick(View view) {
        DatePickerFragment fragment = new DatePickerFragment();

        Calendar calendar  = Calendar.getInstance();
        Bundle bundle = new Bundle();

        bundle.putInt("ano",calendar.get(Calendar.YEAR));
        bundle.putInt("mes",calendar.get(Calendar.MONTH));
        bundle.putInt("dia",calendar.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(bundle);
        fragment.setDateListener(dateListener);
        fragment.show(getFragmentManager(),"Data Nasc.");
    }



    private DatePickerDialog.OnDateSetListener dateListener =  new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            edtData.setText(dayOfMonth+"/"+(month+1) +"/"+year);
        }
    };

    private void initProfissoes(){
        ArrayList<String> profissoes = new ArrayList<>();
        for (ProfissaoEnum p: ProfissaoEnum.values() ) {
            profissoes.add(p.getDescricao());
        }
        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item,profissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProfissao.setAdapter(adapter);
    }

    public void enviarPessoa(View view) {
        Pessoa p = montarPessoa();

        if(!validarPessoa(p)){
//            Util.showMsgToast(this,"Cadastro OK");
            pessoaRepository.salvarPessoa(p);
            finish();
        }
    }


    private boolean validarPessoa(Pessoa pessoa){
        boolean erro = false;
        if(pessoa.getNome() == null || "".equals(pessoa.getNome())){
            erro = true;
            edtNome.setError("Campo nome obrigatório!");
        }

        if(pessoa.getEndereco() == null || "".equals(pessoa.getEndereco())){
            erro = true;
            edtEndereco.setError("Campo endereço obrigatório!");
        }

        if(pessoa.getCpfCnepj() == null || "".equals(pessoa.getCpfCnepj())){
            erro = true;
            switch (rbgCpfCnpj.getCheckedRadioButtonId()){
                case R.id.rbtCpf:
                    edtCpfCnpj.setError("Campo CPF obrigatório!");
                    break;
                case R.id.rbtCnpj:
                    edtCpfCnpj.setError("Campo CNPJ obrigatório!");
                    break;
            }
        }else{
            switch (rbgCpfCnpj.getCheckedRadioButtonId()){
                case R.id.rbtCpf:
                    if(edtCpfCnpj.getText().length() <14){
                        erro = true;
                        edtCpfCnpj.setError("Campo CPF deve ter 11 caracteres");
                    }
                    break;
                case R.id.rbtCnpj:
                    if(edtCpfCnpj.getText().length() <18){
                        erro = true;
                        edtCpfCnpj.setError("Campo CNPJ deve ter 14 caracteres");
                    }
                    break;
            }
        }

        if(pessoa.getDtNascimento() == null){
            erro = true;
            edtData.setError("Campo Data obrigatório!");
        }

        return erro;
    }

    private Pessoa montarPessoa(){
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEndereco(edtEndereco.getText().toString());
        pessoa.setCpfCnepj(edtCpfCnpj.getText().toString());

        switch (rbgCpfCnpj.getCheckedRadioButtonId()){
            case R.id.rbtCpf:
                pessoa.setTipoPessoa(TipoPessoaEnum.FISICA);
                break;
            case R.id.rbtCnpj:
                pessoa.setTipoPessoa(TipoPessoaEnum.JURIDICA);
                break;
        }

        switch (rbgSexo.getCheckedRadioButtonId()){
            case R.id.rbtMasculino:
                pessoa.setSexo(SexoEnum.MASCULINO);
                break;
            case R.id.rbtFeminino:
                pessoa.setSexo(SexoEnum.FEMININO);
                break;
        }

        ProfissaoEnum profissaoEnum = ProfissaoEnum.getProfissao(spnProfissao.getSelectedItemPosition());
        pessoa.setProfissao(profissaoEnum);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = dateFormat.parse(edtData.getText().toString());
            pessoa.setDtNascimento(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        WrapperLog.info("PESSOA "+pessoa.toString());
        return pessoa;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
