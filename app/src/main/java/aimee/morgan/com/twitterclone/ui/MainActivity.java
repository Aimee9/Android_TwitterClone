package aimee.morgan.com.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import aimee.morgan.com.twitterclone.R;
import aimee.morgan.com.twitterclone.adapters.TweetAdapter;
import aimee.morgan.com.twitterclone.models.Tweet;
import aimee.morgan.com.twitterclone.models.User;

public class MainActivity extends ListActivity {
    public static String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences mPreferences;
    private User mUser;
    private EditText mTweetText;
    private Button mSubmitButton;
    private ArrayList<Tweet> mTweets;
    private TweetAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        mTweetText = (EditText)findViewById(R.id.newTweetEdit);
        mSubmitButton = (Button) findViewById(R.id.tweetSubmitButton);
        mListView = (ListView) findViewById(android.R.id.list);
        mTweets = (ArrayList) Tweet.all();
        mAdapter = new TweetAdapter(this, mTweets);
        setListAdapter(mAdapter);

        if(!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = mTweetText.getText().toString();
                Tweet tweet = new Tweet(tweetContent, mUser);
                tweet.save();
                mTweets.add(tweet);
                mAdapter.notifyDataSetChanged();
                mTweetText.getText().clear();

            }
        });
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
