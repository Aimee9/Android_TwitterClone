package aimee.morgan.com.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import aimee.morgan.com.twitterclone.R;
import aimee.morgan.com.twitterclone.models.User;

public class MainActivity extends ListActivity {

    private SharedPreferences mPreferences;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        if(!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private boolean isRegistered() {
        String username = mPreferences.getString("username", null);
        if(username == null) {
            return false;
        } else {
            setUser(username);
            return true;
        }
    }

    public void setUser(String username) {
        User user = User.find(username);
        if(user != null) {
            mUser = user;
        } else {
            mUser = new User(username);
            mUser.save();
        }
        Toast.makeText(this, "Welcome " + mUser.getName(), Toast.LENGTH_LONG).show();
    }
}
