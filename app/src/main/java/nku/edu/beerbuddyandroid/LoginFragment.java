package nku.edu.beerbuddyandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class LoginFragment  extends Fragment
{

    @Override
    public void onAttach( Activity activity )
    {
        super.onAttach( activity ) ;

    }

    @Override
    public void onCreate( Bundle state )
    {
        super.onCreate( state ) ;

    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle state )
    {

        View v = inflater.inflate( R.layout.fragment_login, container ,false) ;
        Button login = (Button)v.findViewById(R.id.login_button);
        Button signup = (Button)v.findViewById(R.id.signup_button);
        final EditText username = (EditText)v.findViewById(R.id.username_edittext);
        final EditText password = (EditText)v.findViewById(R.id.password_edittext);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptLogin(username.getText().toString(),password.getText().toString());
                    }
                });
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptSignup(username.getText().toString(),password.getText().toString());
                    }
                });


        return v ;
    }

    public void attemptLogin(String username, String password)
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String storedPassword = sharedPreferences.getString(username,null);
        if(storedPassword.equals(password))
        {
            login();
        }
        else
        {
            Toast.makeText(getActivity(), R.string.incorrect_error, Toast.LENGTH_SHORT).show();
        }



    }
    public void attemptSignup(String username, String password)
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        if(sharedPreferences.contains(username))
        {
            Toast.makeText(getActivity(), R.string.user_error, Toast.LENGTH_SHORT).show();
        }
        else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username, password);
            editor.commit();
            Toast.makeText(getActivity(), R.string.user_success, Toast.LENGTH_SHORT).show();
        }

    }
    public void login()
    {
        Fragment fragment = new BeerFragment();

        FragmentManager fragmentManager = getFragmentManager();


        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();


        return;
    }


}