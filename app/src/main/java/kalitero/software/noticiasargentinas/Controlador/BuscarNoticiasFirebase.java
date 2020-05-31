package kalitero.software.noticiasargentinas.Controlador;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;

import kalitero.software.noticiasargentinas.Modelo.ListaNoticias;
import kalitero.software.noticiasargentinas.Modelo.Noticia;

import static android.content.ContentValues.TAG;

public class BuscarNoticiasFirebase {

    private String TAG = getClass().toString();
    private RecepcionNoticias listener;

    public BuscarNoticiasFirebase(RecepcionNoticias listener) {
        this.listener = listener;
    }

    public void porTema (final String tema) {
        FirebaseFirestore.getInstance().collection(tema).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ListaNoticias listaNoticias = new ListaNoticias(tema);
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            listaNoticias.agregar(queryDocumentSnapshot.toObject(Noticia.class));
                        }
                        Log.d(TAG, "Leimos noticias de Firebase");
                        listener.llegoPaqueteDeNoticias(listaNoticias);
                    }
                });
    }

    public void guargarNoticia ( String tema , Noticia noticia) {
        FirebaseFirestore.getInstance().collection(tema).add(noticia)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Exito en guardamos en firebase");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(TAG, "Fin de guardar en Firebase ");
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.d(TAG, "Cancelado Firebase");
                    }
                });
    }
}
