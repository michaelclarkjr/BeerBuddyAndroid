package nku.edu.beerbuddyandroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment
{
    //boolean to see if code runs in Unit test
    public boolean loginboolean = false;
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity) ;
    }

    @Override
    public void onCreate(Bundle state)
    {
        super.onCreate(state) ;
        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        View v = inflater.inflate( R.layout.fragment_login, container ,false) ;
        Button login = (Button)v.findViewById(R.id.login_button);
        Button signup = (Button)v.findViewById(R.id.signup_button);
        final EditText username = (EditText)v.findViewById(R.id.username_edittext);
        final EditText password = (EditText)v.findViewById(R.id.password_edittext);
        login.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    attemptLogin(username.getText().toString(),password.getText().toString());
                }
            });

        signup.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    attemptSignup(username.getText().toString(),password.getText().toString());
                }
            });

        return v ;
    }

    private void attemptLogin(String username, String password)
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String storedPassword = sharedPreferences.getString(username,null);

        if(storedPassword != null &&storedPassword.equals(password))
        {
            login();
        }
        else
        {
            Toast.makeText(getActivity(), R.string.incorrect_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void attemptSignup(String username, String password)
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
            login();
        }
    }

    private void login()
    {
        Fragment fragment = new BeerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
        loginboolean = true;
        return;
    }
}