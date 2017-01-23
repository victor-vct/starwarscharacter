package com.vctapps.starwarscharacters.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Character;
import com.vctapps.starwarscharacters.model.PerfilSingleton;
import com.vctapps.starwarscharacters.model.Register;
import com.vctapps.starwarscharacters.model.CharacterObservable;
import com.vctapps.starwarscharacters.service.ManagerRegister;
import com.vctapps.starwarscharacters.service.OnFinish;
import com.vctapps.starwarscharacters.ui.adapter.AboutCharacterAdapter;

public class DetailCharacterActivity extends AppCompatActivity
        implements OnFinish<Character>{

    private static final String TAG = "detailCharacterDebug";
    public static final String CHARACTER_FOR_DETAIL = "com.vctapps.characterfordetails";

    private Register register;
    private CharacterObservable observable;

    //Views
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ViewGroup progressLayout;
    private ViewGroup noCharacterLayout;
    private AboutCharacterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressLayout = (ViewGroup) findViewById(R.id.progress_layout);
        noCharacterLayout = (ViewGroup) findViewById(R.id.no_character);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_detail_character);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_detail_character);

        noCharacterLayout.setVisibility(View.GONE);

        //Observable para atualizar as informações em todos fragments do viewpager
        observable = new CharacterObservable();

        if(getIntent().getExtras().containsKey(CHARACTER_FOR_DETAIL)){
            register = (Register) getIntent().getExtras().getSerializable(CHARACTER_FOR_DETAIL);

            Log.d(TAG, "Registro recebido: " + register.getLink());

            ManagerRegister manager = new ManagerRegister(this);

            manager.getCharacter(register, this);
        }else{
            noCharacterLayout.setVisibility(View.VISIBLE);
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método chamado quando a busca por um personagem for finalizada
     * @param character instancia com informações do personagem
     */
    @Override
    public void onSuccess(Character character) {
        if(character != null) {
            Log.d(TAG, "Character recebido na activity: " + character.getName());
            mAdapter = new AboutCharacterAdapter(getSupportFragmentManager(), PerfilSingleton.getInstance(), character, observable);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
            observable.setCharacter(character);
        }else{
            noCharacterLayout.setVisibility(View.VISIBLE);
        }
        progressLayout.setVisibility(View.GONE);
    }

    /**
     * Método chamado quando a busca por um personagem conter erros
     */
    @Override
    public void onError() {
        noCharacterLayout.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
    }
}
