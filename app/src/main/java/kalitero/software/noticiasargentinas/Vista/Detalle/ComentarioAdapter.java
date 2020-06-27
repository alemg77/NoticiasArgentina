package kalitero.software.noticiasargentinas.Vista.Detalle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase;
import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.Modelo.Noticia;
import kalitero.software.noticiasargentinas.Modelo.Voto;
import kalitero.software.noticiasargentinas.databinding.CeldaComentarioBinding;
import kalitero.software.noticiasargentinas.util.ResultListener;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolderComentario> {

    private List<Comentario> listaComentarios;
    private Votacion listener;
    private Noticia noticia;

    public ComentarioAdapter(Noticia noticia, List<Comentario> listaComentarios, Votacion listener) {
        this.listaComentarios = listaComentarios;
        this.listener = listener;
      //  this.comentarioAdapterListener = comentarioAdapterListener;
    }

    @NonNull
    @Override
    public ComentarioAdapter.ViewHolderComentario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CeldaComentarioBinding binding = CeldaComentarioBinding.inflate(layoutInflater, parent, false);
        ComentarioAdapter.ViewHolderComentario viewHolder = new  ComentarioAdapter.ViewHolderComentario(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioAdapter.ViewHolderComentario holder, int position) {
        Comentario comentario = this.listaComentarios.get(position);
        holder.onBind(comentario);
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }


    //La representacion de mi celda.
    protected class ViewHolderComentario extends RecyclerView.ViewHolder {

        private CeldaComentarioBinding binding;
        private String TAG = getClass().toString();

        public ViewHolderComentario(@NonNull CeldaComentarioBinding celdaComentarioBinding) {
            super(celdaComentarioBinding.getRoot());
            binding = celdaComentarioBinding;
        }

        public void onBind(Comentario comentario) {

            /*
            NoticiaDaoFirebase.Companion.getIntancia()
                    .buscarVotosComentario(noticia, comentario, new ResultListener<List<Voto>>() {
                        @Override
                        public void onFinish(List<Voto> result) {
                            // TODO: Contar los votos y mostrarlos!!!!
                            Log.d(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }

                        @Override
                        public void onError(@NotNull String message) {

                        }
                    });

             */

            binding.celdaComentariosTextViewComentario.setText(comentario.getOpinion());
            binding.celdaComentariosTextViewLike.setText(comentario.getPositivos().toString());
            binding.celdaComentariosTextViewDislike.setText(comentario.getNegativos().toString());
            binding.celdaComentariosTextViewEmail.setText(comentario.getUsuario());
            binding.celdaComentariosTextViewFecha.setText(comentario.getFecha().toString());

            binding.celdaComentariosBotonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.votoPositivo(getAdapterPosition());
                }
            });

            binding.celdaComentariosTextViewDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.votoNegativo(getAdapterPosition());
                }
            });
        }
    }

    public interface Votacion {
        void votoPositivo ( int posicion );
        void votoNegativo ( int posicion );
    }


}
