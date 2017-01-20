package com.vctapps.starwarscharacters.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.Register;

import java.util.List;

/**
 * Classe que gerencia os itens de uma lista com Registers
 */

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.MyViewHolder> {

    private List<Register> mRegisters;
    private LayoutInflater inflater;

    public RegisterAdapter(Context context, List<Register> registers){
        mRegisters = registers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_main_list, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Register reg = mRegisters.get(position);

        if(reg != null){
            holder.userName.setText(reg.getUserName());
            holder.link.setText(reg.getLink());
            holder.characterName.setText(reg.getCharacterName());
        }
    }

    @Override
    public int getItemCount() {
        return mRegisters != null ? mRegisters.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        TextView userName;
        TextView link;
        TextView characterName;

        public MyViewHolder(View view){
            super(view);

            photo = (ImageView) view.findViewById(R.id.image_main_list_placeholder);
            userName = (TextView) view.findViewById(R.id.text_main_list_user_name);
            link = (TextView) view.findViewById(R.id.text_main_list_link);
            characterName = (TextView) view.findViewById(R.id.text_main_list_character_name);
        }
    }
}
