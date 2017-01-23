package com.vctapps.starwarscharacters.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vctapps.starwarscharacters.model.Film;
import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.service.ManagerFilms;
import com.vctapps.starwarscharacters.service.OnFinishFilm;

/**
 * Classe que gerencia os itens da lista de filmes
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {

    private static final String TAG = "filmAdapterDebug";
    private String[] urls;
    private Context context;
    private ManagerFilms managerFilms;

    public FilmAdapter(String[] urls, Context context){
        this.urls = urls;
        this.context = context;
        managerFilms = new ManagerFilms(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.adapter_film_list, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.progress.setVisibility(View.VISIBLE);
        holder.photo.setImageResource(R.drawable.ic_yoda);
        managerFilms.getFilm(urls[position], new OnFinishFilm() {
            @Override
            public void onGetFilm(Film film) {
                holder.title.setText(film.getTitle());
                holder.desc.setText(film.getOpeningCrawl().substring(0, 120) + "...");
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onGetUrlPoster(String url) {
                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.ic_yoda)
                        .into(holder.photo);
            }

            @Override
            public void onErro() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView title;
        TextView desc;
        ViewGroup progress;

        public MyViewHolder(View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.image_adapter_film);
            title = (TextView) itemView.findViewById(R.id.text_adapter_filme_name);
            desc = (TextView) itemView.findViewById(R.id.text_adapter_filme_desc);
            progress = (ViewGroup) itemView.findViewById(R.id.progress_layout);
        }
    }
}
