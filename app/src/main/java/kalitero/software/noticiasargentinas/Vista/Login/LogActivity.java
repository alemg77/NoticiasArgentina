package kalitero.software.noticiasargentinas.Vista.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.Vista.Login.FragmentLogin;

public class LogActivity extends AppCompatActivity {

    private FragmentLogin fragmentLogin;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        frameLayout = findViewById(R.id.activityLogContenedorFragment);
        pegarFragment(fragmentLogin);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    public void pegarFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityLogContenedorFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}