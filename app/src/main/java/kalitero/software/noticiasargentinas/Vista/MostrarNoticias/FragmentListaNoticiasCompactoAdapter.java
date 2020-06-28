package kalitero.software.noticiasargentinas.Vista.MostrarNoticias;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.CeldaNoticiaBinding;

public class FragmentListaNoticiasCompactoAdapter extends RecyclerView.Adapter {


    private List<Noticia> listaDeNoticias;
    private AvisoRecyclerView listener;
    private String TAG = getClass().toString();

    public FragmentListaNoticiasCompactoAdapter(List<Noticia> listaDeNoticias, AvisoRecyclerView listener) {
        this.listaDeNoticias = listaDeNoticias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CeldaNoticiaBinding celdaNoticiaBinding = CeldaNoticiaBinding.inflate(layoutInflater, parent, false);
        FragmentListaNoticiasCompactoAdapter.NoticiaViewHolder viewHolder = new FragmentListaNoticiasCompactoAdapter.NoticiaViewHolder(celdaNoticiaBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NoticiaViewHolder) holder).cargarNoticia(listaDeNoticias.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDeNoticias.size();
    }

    private class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private CeldaNoticiaBinding binding;
        private ImageView logo;

        private NoticiaViewHolder(@NonNull CeldaNoticiaBinding celdaNoticiaBinding) {
            super(celdaNoticiaBinding.getRoot());
            binding = celdaNoticiaBinding;

            logo = itemView.findViewById(R.id.CeladaNoticiaimageViewLogo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.recyclerViewClick(getAdapterPosition());
                }
            });
        }

        private void cargarNoticia(Noticia unaNoticia) {
            String titulo = unaNoticia.getTitulo();
            if (titulo.contains("-")) {
                binding.CeldaNoticiaTitulo.setText(titulo.substring(0, titulo.indexOf("-") - 1));
            } else {
                binding.CeldaNoticiaTitulo.setText(titulo);
            }

            String urlImagenStorage = unaNoticia.getUrlImagenStorage();
            if (urlImagenStorage != null) {
                StorageReference child = FirebaseStorage.getInstance().getReference().child(urlImagenStorage);
                Glide.with(binding.getRoot()).load(child).into(binding.CeldaNoticiaImagen);
            } else if (unaNoticia.getUrlImagen() != null) {
                try {
                    Glide.with(binding.getRoot()).load(unaNoticia.getUrlImagen()).into(binding.CeldaNoticiaImagen);
                } catch (Exception e) {
                    Log.d(TAG, "Problema al cargar imagen:" + e.toString());
                }
            }

            String fuente = unaNoticia.getFuente();
            switch (fuente) {
                case "La Nacion":
                    Picasso.get().load(R.drawable.logo_lanacion).into(logo);
                    break;

                case "Tntsports.com":
                    Picasso.get().load(R.drawable.logo_tntsports).into(logo);
                    break;

                case "Clarin.com":
                    Picasso.get().load(R.drawable.logo_clarin).into(logo);
                    break;

                case "Eldia.com":
                    Picasso.get().load(R.drawable.logo_eldia).into(logo);
                    break;

                case "Infobae":
                    Picasso.get().load(R.drawable.logo_infobae).into(logo);
                    break;

                case "Tycsports.com":
                    Picasso.get().load(R.drawable.logo_tycsports).into(logo);
                    break;

                case "Ole.com.ar":
                    Picasso.get().load(R.drawable.logo_ole).into(logo);
                    break;

                case "Mdzol.com":
                    Picasso.get().load(R.drawable.logo_mdzol).into(logo);
                    break;

                case "Marca":
                    Picasso.get().load(R.drawable.logo_marca).into(logo);
                    break;

                case "El Mundo":
                    Picasso.get().load(R.drawable.logo_elmundo).into(logo);
                    break;

                case "Clarín":
                    Picasso.get().load(R.drawable.clarin).into(logo);
                    break;

                case "ámbito.com":
                    Picasso.get().load(R.drawable.ambito).into(logo);
                    break;

                case "Computerhoy.com":
                    Picasso.get().load(R.drawable.logo_computerhoy).into(logo);
                    break;

                case "La Voz del Interior":
                    Picasso.get().load(R.drawable.logo_la_voz).into(logo);
                    break;

                case "Página 12":
                    Picasso.get().load(R.drawable.logo_pagina12).into(logo);
                    break;

                case "RT":
                    Picasso.get().load(R.drawable.logo_rt).into(logo);
                    break;

                case "Diario El Dia. www.eldia.com":
                    Picasso.get().load(R.drawable.logo_el_dia).into(logo);
                    break;


                default:
                    Log.d(TAG, "****** FALTA AGREGAR EL LOGO DE:" + fuente + "*****************");
                    break;
            }

        }
    }

    public interface AvisoRecyclerView {
        void recyclerViewClick(int posicion);
    }
}

