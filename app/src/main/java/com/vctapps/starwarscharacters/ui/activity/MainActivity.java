package com.vctapps.starwarscharacters.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.persistence.ConstDb;
import com.vctapps.starwarscharacters.persistence.dao.RegisterDAO;
import com.vctapps.starwarscharacters.presenter.RegistersPresenter;
import com.vctapps.starwarscharacters.service.OnFinish;
import com.vctapps.starwarscharacters.ui.adapter.RegisterAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFinish<List<Register>>{

    private static final String TAG = "mainDebug";
    private RecyclerView recycler;
    private RecyclerView.LayoutManager managerLayout;
    private RegisterAdapter adapter;
    private RegistersPresenter presenter;
    private ViewGroup noCharacter;
    private ViewGroup progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FAFAFA"));
        setSupportActionBar(toolbar);

        noCharacter = (ViewGroup) findViewById(R.id.no_registers);
        progressLayout = (ViewGroup) findViewById(R.id.progress_layout);
        recycler = (RecyclerView) findViewById(R.id.recycler_main_list);
        managerLayout = new LinearLayoutManager(this);
        recycler.setLayoutManager(managerLayout);

        presenter = new RegistersPresenter(this);
        noCharacter.setVisibility(View.GONE);

        startRequestListRegisters();
    }

    private void startRequestListRegisters(){
        if(presenter != null) presenter.getAllRegisters(this);
    }

    @Override
    public void onSuccess(List<Register> registers) {
        if(registers == null || registers.size() >= 0){
            onError();
        }else{
            adapter = new RegisterAdapter(this, registers);
            recycler.setAdapter(adapter);
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        progressLayout.setVisibility(View.GONE);
        noCharacter.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Não foi encontrado nenhum registro", Toast.LENGTH_SHORT).show();
    }
}
