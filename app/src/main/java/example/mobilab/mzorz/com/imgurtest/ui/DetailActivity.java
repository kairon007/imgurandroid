package example.mobilab.mzorz.com.imgurtest.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.mobilab.mzorz.com.imgurtest.Application;
import example.mobilab.mzorz.com.imgurtest.R;
import example.mobilab.mzorz.com.imgurtest.api.Event;
import example.mobilab.mzorz.com.imgurtest.api.GalleryService;
import example.mobilab.mzorz.com.imgurtest.model.BaseModel;
import example.mobilab.mzorz.com.imgurtest.model.Image;
import example.mobilab.mzorz.com.imgurtest.ui.adapter.ImagesListAdapter;


public class DetailActivity extends BaseActivity {

    private static String TAG = "imgurTestDetail";

    private PlaceholderFragment myFragment;
    protected String title;
    protected String description;
    protected int ups;
    protected int downs;
    protected int scorei;
    protected String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            myFragment = new PlaceholderFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();


            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            ups = getIntent().getIntExtra("upvotes", 0);
            downs = getIntent().getIntExtra("downvotes", 0);
            scorei = getIntent().getIntExtra("score", 0);
            imageUrl = getIntent().getStringExtra("image");

        } else  {
            //Restore the fragment's instance
            myFragment = (PlaceholderFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myfragment");

            title = savedInstanceState.getString("title");
            description = savedInstanceState.getString("description");
            ups = savedInstanceState.getInt("upvotes");
            downs = savedInstanceState.getInt("downvotes");
            scorei = savedInstanceState.getInt("score");
            imageUrl = savedInstanceState.getString("image");

        }

        myFragment.setVars(title, description, imageUrl, ups, downs, scorei);


        //load stuff on the screen

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "myfragment", myFragment);

        outState.putString("title", title);
        outState.putString("description", description);
        outState.putInt("upvotes", ups);
        outState.putInt("downvotes", downs);
        outState.putInt("score", scorei);
        outState.putString("image", imageUrl);
        super.onSaveInstanceState(outState);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public ImageView image;
        public TextView titleTv;
        public TextView desc;
        public TextView upvotes;
        public TextView downvotes;
        public TextView score;


        protected String stitle;
        protected String sdescription;
        protected int iups;
        protected int idowns;
        protected int iscore;
        protected String simageUrl;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            image = (ImageView) rootView.findViewById(R.id.image);
            titleTv = (TextView) rootView.findViewById(R.id.title);
            desc = (TextView) rootView.findViewById(R.id.description);
            upvotes = (TextView) rootView.findViewById(R.id.upvotes);
            downvotes = (TextView) rootView.findViewById(R.id.downvotes);
            score = (TextView) rootView.findViewById(R.id.score);

            if (simageUrl != null)
                Picasso.with(getActivity())
                        .load(ImagesListAdapter.getThumbnailURL(simageUrl, "l"))
                        .placeholder(R.drawable.ic_launcher)
                        .error(R.drawable.ic_launcher)
                        .into(image);
            else
                image.setImageBitmap(null);


            titleTv.setText(stitle);
            desc.setText(sdescription);
            upvotes.setText("Upvotes: " + iups);
            downvotes.setText("Downvotes: " + idowns);
            score.setText("Score: " + iscore);


            return rootView;
        }

        public void setVars(String title, String desc, String imageUrl, int up, int down, int score){

            this.stitle = title;
            this.sdescription = desc;
            this.simageUrl = imageUrl;
            this.iups = up;
            this.idowns = down;
            this.iscore = score;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
