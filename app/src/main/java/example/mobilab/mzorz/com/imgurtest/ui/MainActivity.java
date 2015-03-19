package example.mobilab.mzorz.com.imgurtest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.mobilab.mzorz.com.imgurtest.Application;
import example.mobilab.mzorz.com.imgurtest.R;
import example.mobilab.mzorz.com.imgurtest.api.Event;
import example.mobilab.mzorz.com.imgurtest.api.GalleryService;
import example.mobilab.mzorz.com.imgurtest.model.BaseModel;
import example.mobilab.mzorz.com.imgurtest.ui.adapter.ImagesListAdapter;


public class MainActivity extends BaseActivity {

    private static String TAG = "imgurTest";
    private PlaceholderFragment myFragment;
    private boolean bIncludeViral = true;
    private String section = "hot";
    private String window = "day";
    private String sort = "viral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            myFragment = new PlaceholderFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();
        } else  {
            //Restore the fragment's instance
            myFragment = (PlaceholderFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myfragment");
            section = savedInstanceState.getString("section");
            bIncludeViral = savedInstanceState.getBoolean("viral", false);
            sort = savedInstanceState.getString("sort");
            window = savedInstanceState.getString("window");
        }

        Application.getEventBus().register(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save the fragment's instance
        outState.putString("section", section);
        outState.putString("sort", sort);
        outState.putString("window", window);
        outState.putBoolean("viral", bIncludeViral);
        getSupportFragmentManager().putFragment(outState, "myfragment", myFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //MenuItem item = menu.getItem(0);
        MenuItem item = menu.findItem(R.id.viral);
        if (item != null)
            item.setChecked(bIncludeViral);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_section:
                Log.d(TAG, "SECTION PRESSED");
                return true;

            case R.id.viral:
                Log.d(TAG, "viral checked");
                bIncludeViral = !bIncludeViral;
                item.setChecked(bIncludeViral);
                reload();
                return true;

            case R.id.sectionhot:
                Log.d(TAG, "Section hot selected");
                section = "hot";
                reload();
                return true;

            case R.id.sectiontop:
                Log.d(TAG, "Section top selected");
                section = "top";
                reload();
                return true;

            case R.id.sectionuser:
                Log.d(TAG, "Section user selected");
                section = "user";
                reload();
                return true;


            //WINDOW section
            case R.id.window_all:
                Log.d(TAG, "window all");
                window = "all";
                reload();
                return true;

            case R.id.window_day:
                Log.d(TAG, "window day");
                window = "day";
                reload();
                return true;

            case R.id.window_top:
                Log.d(TAG, "window top");
                window = "top";
                reload();
                return true;

            case R.id.window_week:
                Log.d(TAG, "window week");
                window = "week";
                reload();
                return true;

            case R.id.window_year:
                Log.d(TAG, "window year");
                window = "year";
                reload();
                return true;

            //SORT
            case R.id.sort_viral:
                Log.d(TAG, "sort viral");
                sort = "viral";
                reload();
                return true;

            case R.id.sort_top:
                Log.d(TAG, "sort top");
                sort = "top";
                reload();
                return true;

            case R.id.sort_time:
                Log.d(TAG, "sort time");
                sort = "time";
                reload();
                return true;

            case R.id.sort_rising:
                Log.d(TAG, "sort rising");
                if (section != null && section.equalsIgnoreCase("user")){
                    sort = "rising";
                    reload();
                } else {
                    Toast.makeText(this,getString(R.string.sort_not_available), Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private GridView gridView;
        public ImagesListAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            gridView = (GridView) rootView.findViewById(R.id.gridview);
            adapter = new ImagesListAdapter(getActivity(), new ArrayList<BaseModel>());
            gridView.setAdapter(adapter);

            return rootView;
        }
    }


    private void reload(){
        GalleryService.getInstance().gallery(Application.getAuthorizationHeader(), section, sort, window, 0, bIncludeViral);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reload();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Application.getEventBus().unregister(this);
    }


    public void onEvent(Event.GalleryRequestStartEvent event) {
        showProgressDialog(getString(R.string.server_login), false);
    }

    public void onEvent(Event.GalleryRequestCompleteEvent event) {
        dismissProgressDialog();
        if (event.object == null || event.object.data == null)
            showError(this, getResources().getString(R.string.error_no_items_found), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        else{
            myFragment.adapter.setItemsList(event.object.data);
            myFragment.adapter.notifyDataSetChanged();
        }
    }

    public void onEvent(Event.GalleryRequestFailEvent event) {
        dismissProgressDialog();
        showError();
    }

}
