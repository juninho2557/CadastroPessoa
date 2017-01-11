package com.wfghc.cadastrodepessoa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wfghc.cadastrodepessoa.entidade.model.Pessoa;
import com.wfghc.cadastrodepessoa.repository.PessoaRepository;
import com.wfghc.cadastrodepessoa.util.TipoMsg;
import com.wfghc.cadastrodepessoa.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListPessoaActivity extends AppCompatActivity {

    private ListView listViewPessoas;
    RecyclerView.LayoutManager      layoutManager;
    RecyclerView.Adapter            adapter;
    private PessoaRepository pessoaRepository;
    private List<Pessoa>            pessoaList;
    private int                     posicaoSelecionada;
    private ArrayAdapter            arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pessoa);
        pessoaRepository    = new PessoaRepository(this);
        listViewPessoas = (ListView) findViewById(R.id.list_pessoa);
        getSupportActionBar().setTitle("Lista de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        setArrayAdapterPessoas();

        listViewPessoas.setOnItemClickListener(onItemClickListener);
        listViewPessoas.setOnCreateContextMenuListener(onCreateContextMenuListener);
        listViewPessoas.setOnItemLongClickListener(onItemLongClickListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);

    }

    private void setArrayAdapterPessoas() {
        pessoaList = pessoaRepository.listaPessoa();
        List<String> valores = new ArrayList<String>();

        for (Pessoa p : pessoaList) {
            valores.add(p.getNome());
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(valores);
        listViewPessoas.setAdapter(arrayAdapter);
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListPessoaActivity.this, PessoaActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private View.OnCreateContextMenuListener onCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opções").setHeaderIcon(R.drawable.edit_64).add(1,10,1,"Editar");
            menu.add(1,20,2,"Deletar");
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 10:
                Pessoa pessoa = pessoaRepository.consultarPessoaPorId(pessoaList.get(posicaoSelecionada).getIdPessoa());
                Intent intent = new Intent(this, EditarPessoaActivity.class);
                intent.putExtra("pessoa",pessoa);
                startActivity(intent);
                finish();
                break;
            case 20:
                Util.showMsgConfirm(ListPessoaActivity.this, "Remover Pessoa", "Deseja realmente remover?", TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = pessoaList.get(posicaoSelecionada).getIdPessoa();
                        pessoaRepository.removerPessoaPorId(id);
                        setArrayAdapterPessoas();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = pessoaRepository.consultarPessoaPorId(pessoaList.get(position).getIdPessoa());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder info = new StringBuilder();
            info.append("Nome: "+pessoa.getNome());
            info.append("\nEndereço: "+pessoa.getEndereco());
            info.append("\nCPF/CNPJ: "+pessoa.getCpfCnepj());
            info.append("\nSexo: "+pessoa.getSexo().getDescricao());
            info.append("\nProfissão: "+pessoa.getProfissao().getDescricao());
            info.append("\nData de Nascimento: "+dateFormat.format(pessoa.getDtNascimento()));
            Util.showMsgAlertOK(ListPessoaActivity.this,"Informação", info.toString(), TipoMsg.INFO);
        }
    };

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
