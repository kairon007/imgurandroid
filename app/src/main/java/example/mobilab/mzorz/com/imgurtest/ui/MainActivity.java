package example.mobilab.mzorz.com.imgurtest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import example.mobilab.mzorz.com.imgurtest.Application;
import example.mobilab.mzorz.com.imgurtest.R;
import example.mobilab.mzorz.com.imgurtest.api.Event;
import example.mobilab.mzorz.com.imgurtest.api.GalleryService;
import example.mobilab.mzorz.com.imgurtest.ui.adapter.ImagesListAdapter;


public class MainActivity extends BaseActivity {

    private PlaceholderFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            myFragment = new PlaceholderFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myFragment)
                    .commit();
        }

        Application.getEventBus().register(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            adapter = new ImagesListAdapter(getActivity(), null);
            gridView.setAdapter(adapter);

            return rootView;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        GalleryService.getInstance().gallery(Application.getAuthorizationHeader(), "hot", "viral", 0, true);

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
            ///myWebView.loadData(event.object.data,"text/html","utf-8");
            //TODO: show grid
            myFragment.adapter.setItemsList(event.object.data);
        }
    }

    public void onEvent(Event.GalleryRequestFailEvent event) {
        dismissProgressDialog();
        showError();
    }

}
