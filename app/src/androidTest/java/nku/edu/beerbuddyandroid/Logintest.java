package nku.edu.beerbuddyandroid;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


// TEST CLASS
public class Logintest extends android.test.ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity myFragmentActivity;
    LoginFragment LoginFragment;
    View v;

    public Logintest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFragmentActivity = (MainActivity) getActivity();
        LoginFragment = new LoginFragment();
        v = myFragmentActivity.login.getView();
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
        //test for changes from preform login
        assertTrue(myFragmentActivity.login.loginboolean);



    }

}
