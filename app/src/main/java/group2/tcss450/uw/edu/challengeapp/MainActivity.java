package group2.tcss450.uw.edu.challengeapp;

import android.net.Uri;
import android.net.nsd.NsdManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements
        FrontFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener,
        RegistrationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new FrontFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(int button_Id, String userName, String password) {
        switch(button_Id) {
            case R.id.login_button:
                LoginFragment fragment = new LoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null);
                transaction.commit();
                break;
            case R.id.registration_button:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new RegistrationFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                DisplayFragment df = new DisplayFragment();
                Bundle args = new Bundle();
                args.putSerializable(df.USERNAME, userName);
                args.putSerializable(df.PASSWORD, password);
                df.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, df)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
