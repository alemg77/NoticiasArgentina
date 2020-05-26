package kalitero.software.noticiasargentinas.Vista.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentLoginBinding;

public class FragmentLogin extends Fragment {

    private String TAG = getClass().toString();
    private static final int RC_SIGN_IN = 7;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FragmentLoginBinding binding;
    private FragmentLogin.Aviso listener;
    private CallbackManager callbackManager;

    public FragmentLogin() {
    }

    private void verificarUsuarioFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "Estamos conectados con Firebase");

            binding.usuarioNombre.setText(user.getDisplayName());
            binding.usuarioMail.setText(user.getEmail());
            Uri photoUrl = user.getPhotoUrl();
            Picasso.get().load(photoUrl).into(binding.imgenUsuario);

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            boolean emailVerified = user.isEmailVerified();

            Log.d(TAG, "Nos conectamos!!!");
            Snackbar.make(binding.getRoot(), "Nos conectamos!!!", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        listener = (FragmentLogin.Aviso) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        verificarUsuarioFirebase();

        callbackManager = CallbackManager.Factory.create();

        EscucharBotonMail();
        EscucharBotonFacebook();
        EscucharBotonGoogle();

        return binding.getRoot();
    }

    private void EscucharBotonMail() {
        binding.buttonLogearMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Pasar a otro fragmente que logge por mail
                Snackbar.make(binding.getRoot(), "En construccion...", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void EscucharBotonFacebook() {
        binding.buttonLogearFacebook.setReadPermissions("email", "public_profile");
        binding.buttonLogearFacebook.setFragment(this);
        binding.buttonLogearFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Se conecto con Facebook, intenteremos con Firebase");
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, " onCancel Facebook");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, " onError Facebook");
                // App code
            }
        });
    }


    private void EscucharBotonGoogle() {
        binding.botonLogearGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();
                final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.d(TAG, "Se conecto con google, vamos a intentar con Firebase");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
                verificarUsuarioFirebase();
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
                verificarUsuarioFirebase();
            }
        });
    }

    public interface Aviso {

    }
}
