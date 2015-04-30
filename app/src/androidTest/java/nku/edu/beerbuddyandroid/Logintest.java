package nku.edu.beerbuddyandroid;


import android.support.v4.app.FragmentManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


// TEST CLASS
public class Logintest extends android.test.ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity myFragmentActivity;
    LoginFragment LoginFragment;
    FragmentManager FM;
    View v;

    public Logintest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFragmentActivity = (MainActivity) getActivity();
         FM = myFragmentActivity.getSupportFragmentManager();
        LoginFragment = new LoginFragment();
        v = myFragmentActivity.findViewById(R.id.fragment_container);
    }

    public void testPreConditions() {
        assertNotNull(myFragmentActivity);
        assertNotNull(LoginFragment);
    }

    public void testAnythingFromMyFragment() {
        // access any public members of myFragment to test
        final Button login = (Button) v.findViewById(R.id.login_button);
        final Button signup = (Button) v.findViewById(R.id.signup_button);
        final EditText username = (EditText) v.findViewById(R.id.username_edittext);
        final EditText password = (EditText) v.findViewById(R.id.password_edittext);

        //test for changes from preform login
        LoginFragment l =  (LoginFragment)FM.findFragmentById(R.id.fragment_container);

        assertEquals("", username.getText().toString());
        assertEquals("", password.getText().toString());

        myFragmentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setText("User1");
                password.setText("pass");
                signup.performClick();
            }
        });
        myFragmentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                login.performClick();
            }
        });



        getInstrumentation().waitForIdleSync();
        //test for changes from preform signup
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        assertTrue(sharedPreferences.contains("User1"));

        assertTrue(l.loginboolean);


    }

}
