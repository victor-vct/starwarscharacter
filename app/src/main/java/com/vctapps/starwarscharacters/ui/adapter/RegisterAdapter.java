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
    private LayoutInflater mInflater;
    private OnClickItem mOnClickItem;

    public interface OnClickItem{
        void onItemSelected(int position);
        void onLongItemSelected(int position);
    }

    public RegisterAdapter(Context context, List<Register> registers){
        mRegisters = registers;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.mOnClickItem = onClickItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_main_list, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        if(mOnClickItem != null) holder.setListener(mOnClickItem);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Register reg = mRegisters.get(position);

        if(reg != null){
            holder.userName.setText(reg.getUserName());
            holder.link.setText(reg.getLink());
            setCharacterName(holder.characterName, reg);
        }
    }

    /**
     * Verifica se o nome do personagem está gravado na instancia. Caso positivo coloca o nome do
     * personagem na view. Caso contrário, oculta a view.
     * @param view
     * @param register
     */
    private void setCharacterName(TextView view, Register register){
        //Verifica se existe a string e se ela não está vazia
        if(register.getCharacterName() != null && !register.getCharacterName().equals("")){
            view.setVisibility(View.VISIBLE);
            view.setText(register.getCharacterName());
            return;
        }
        view.setVisibility(View.GONE); //se não existe texto, oculta
    }

    @Override
    public int getItemCount() {
        return mRegisters != null ? mRegisters.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView link;
        TextView characterName;

        public MyViewHolder(View view){
            super(view);

            userName = (TextView) view.findViewById(R.id.text_main_list_user_name);
            link = (TextView) view.findViewById(R.id.text_main_list_link);
            characterName = (TextView) view.findViewById(R.id.text_main_list_character_name);
        }

        public void setListener(final OnClickItem onClickItem){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItem.onItemSelected(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onClickItem.onLongItemSelected(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
