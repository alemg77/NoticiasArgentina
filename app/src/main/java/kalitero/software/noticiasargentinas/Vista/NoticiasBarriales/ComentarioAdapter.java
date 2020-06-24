package kalitero.software.noticiasargentinas.Vista.NoticiasBarriales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.R;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolderComentario> {

    private List<Comentario> listaComentarios;
    private ComentarioAdapterListener comentarioAdapterListener;

    public ComentarioAdapter(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
      //  this.comentarioAdapterListener = comentarioAdapterListener;
    }

    @NonNull
    @Override
    public ComentarioAdapter.ViewHolderComentario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_comentario, parent, false);
        return new ViewHolderComentario(view);    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioAdapter.ViewHolderComentario holder, int position) {
        Comentario comentario = this.listaComentarios.get(position);
        holder.onBind(comentario);
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }

    public interface ComentarioAdapterListener {
        void onClickComentario(Comentario comentario);
    }

    //La representacion de mi celda.
    protected class ViewHolderComentario extends RecyclerView.ViewHolder {

        private TextView celdaComentariosTextViewComentario;
        private TextView likes;
        private TextView dislike;
        private TextView celdaComentariosTextViewEmail;
        private TextView fecha;
        private Button botonLike;
        private Button botonDislike;




        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);

            celdaComentariosTextViewComentario = itemView.findViewById(R.id.celdaComentariosTextViewComentario);
            likes = itemView.findViewById(R.id.celdaComentariosTextViewLike);
            dislike = itemView.findViewById(R.id.celdaComentariosTextViewDislike);
            celdaComentariosTextViewEmail = itemView.findViewById(R.id.celdaComentariosTextViewEmail);
            fecha = itemView.findViewById(R.id.celdaComentariosTextViewFecha);
            botonLike = itemView.findViewById(R.id.celdaComentariosBotonLike);
            botonDislike = itemView.findViewById(R.id.celdaComentariosBotonDislike);
        }

        public void onBind(Comentario comentario) {
            celdaComentariosTextViewComentario.setText(comentario.getOpinion());
            likes.setText(comentario.getPositivos().toString());
            dislike.setText(comentario.getNegativos().toString());
            celdaComentariosTextViewEmail.setText(comentario.getUsuario());
            fecha.setText(comentario.getFecha().toString());
        }
    }


}
