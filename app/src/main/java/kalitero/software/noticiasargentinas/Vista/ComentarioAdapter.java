package kalitero.software.noticiasargentinas.Vista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.Comentario;
import kalitero.software.noticiasargentinas.R;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolderComentario> {

    private List<Comentario> listaComentarios;

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

    //La representacion de mi celda.
    protected class ViewHolderComentario extends RecyclerView.ViewHolder {

        private TextView celdaComentariosTextViewComentario;
        private TextView likes;
        private TextView dislike;
        private TextView celdaComentariosTextViewEmail;
        private TextView fecha;


        public ViewHolderComentario(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Comentario comentario) {
            celdaComentariosTextViewComentario.setText(comentario.getOpinion());
            likes.setText(comentario.getPositivos());
            dislike.setText(comentario.getNegativos());
            celdaComentariosTextViewEmail.setText(comentario.getUsuario());
            fecha.setText(comentario.getFecha().toString());
        }
    }


}
