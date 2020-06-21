package kalitero.software.noticiasargentinas.Vista.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentLoginBinding;
import kalitero.software.noticiasargentinas.databinding.FragmentLoginMailRegisterBinding;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLoginMailRegister extends Fragment {

    private FragmentLoginMailRegisterBinding binding;
    private String nombreUsuario;
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    public FragmentLoginMailRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginMailRegisterBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();

        binding.fragmentLoginImageViewMarca.setImageResource(R.drawable.namarca);
        binding.fragmentRegisterBotonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreUsuario = binding.fragmentRegisterNombreUsuario1.getText().toString();
                email = binding.fragmentRegisterEMail.getEditText().toString();
                password = binding.fragmentRegisterPassword.getEditText().toString();
                crearUsuarioConMailYPassword(email, password);

            }
        });



        View view = binding.getRoot();
        return view;
    }

    private void crearUsuarioConMailYPassword (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(getContext(), "Te logueaste Carajo", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Nada de nada de nada", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
