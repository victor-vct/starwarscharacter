package com.vctapps.starwarscharacters.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.PerfilSingleton;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.presenter.RegistersPresenter;
import com.vctapps.starwarscharacters.service.LastLocationService;
import com.vctapps.starwarscharacters.service.OnFinish;
import com.vctapps.starwarscharacters.ui.adapter.RegisterAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnFinish<List<Register>>, View.OnClickListener, RegisterAdapter.OnClickItem{

    private static final String TAG = "mainDebug";
    private static final int REQUEST_QR_CODE = 1;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager managerLayout;
    private RegisterAdapter adapter;
    private RegistersPresenter presenter;
    private ViewGroup noCharacter;
    private ViewGroup progressLayout;
    private List<Register> registers;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Configura a toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FAFAFA"));
        setSupportActionBar(toolbar);

        //Recupera view
        noCharacter = (ViewGroup) findViewById(R.id.no_registers);
        progressLayout = (ViewGroup) findViewById(R.id.progress_layout);
        recycler = (RecyclerView) findViewById(R.id.recycler_main_list);

        //configura lista
        managerLayout = new LinearLayoutManager(this);
        recycler.setLayoutManager(managerLayout);

        //configura FAB
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        presenter = new RegistersPresenter(this);
        noCharacter.setVisibility(View.GONE);

        //Solicita as permissões de camera e localização
        Dexter.withActivity(this) //TODO fazer aviso amigável avisando o pq das permissões
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new EmptyMultiplePermissionsListener())
                .check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startRequestListRegisters();
    }

    /**
     * Solicita lista de registro salvas no banco
     */
    private void startRequestListRegisters(){
        if(presenter != null) {
            progressLayout.setVisibility(View.VISIBLE);
            presenter.getAllRegisters(this);
        }
    }

    /**
     * Callback chamado ao finalizar a busca de registros salvos no banco
     * @param registers
     */
    @Override
    public void onSuccess(List<Register> registers) {
        if(registers == null || registers.size() <= 0){
            onError();
            Log.d(TAG, "mainActivity - onSuccess: ocorreu erro ao receber registros");
        }else{
            this.registers = registers;
            adapter = new RegisterAdapter(this, registers);
            adapter.setOnClickItem(this);
            recycler.setAdapter(adapter);
            progressLayout.setVisibility(View.GONE);
            Log.d(TAG, "mainActivity - onSuccess: numero de registros: " + registers.size());
        }
    }

    /**
     * Chamado quando não foi encontrado nada no banco ou houve algum erro ao fazer a busca
     */
    @Override
    public void onError() {
        progressLayout.setVisibility(View.GONE); //desabilita a tela com progress view
        noCharacter.setVisibility(View.VISIBLE); //avisa que não tem registro
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab: //Botão para add novo registro
                Intent qrCodeScan = new Intent(this, BarcodeActivity.class);
                startActivityForResult(qrCodeScan, REQUEST_QR_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_QR_CODE){
            //recupera o link lido do QRCode
            String link = data.getStringExtra(BarcodeActivity.RESULT_REQUEST_QR_CODE);
            Log.d(TAG, "QRCode recebido: " + link);

            presenter.saveRegisterWithUrl(link, getCallbackForSave());
        }
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Callback com métodos chamados após salvar.
     * @return Interface OnFinish
     */
    private OnFinish<Register> getCallbackForSave(){
        return new OnFinish<Register>() {
            @Override
            public void onSuccess(Register register) {
                if(register == null){ //If para caso algo tenha dado errado
                    onError();
                    return;
                }

                Log.d(TAG, "Registro salvo com sucesso");
                if(registers != null){ //Add registro na lista
                    registers.add(register);
                    adapter.notifyItemInserted(registers.size() - 1);
                }
            }

            @Override
            public void onError() {
                Log.d(TAG, "Não foi possível salvar o registro");
                showMessage("Não foi possível salvar");
            }
        };
    }

    /**
     * Callback chamando quando um item da lista é chamado
     * @param position
     */
    @Override
    public void onItemSelected(int position) {
        Log.d(TAG, "Item da lista selecionado: " + position);
        //coloca um registro na intent e inicia uma activity
        Intent detailCharacter = new Intent(this, DetailCharacterActivity.class);
        detailCharacter.putExtra(DetailCharacterActivity.CHARACTER_FOR_DETAIL, registers.get(position));
        startActivity(detailCharacter);
    }

    @Override
    public void onLongItemSelected(int position) {
        //TODO fazer menu para deletar registro
        Log.d(TAG, "Longo click no item da lista: " + position);
    }
}
