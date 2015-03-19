package example.mobilab.mzorz.com.imgurtest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import example.mobilab.mzorz.com.imgurtest.R;

/**
 * Created by mariozorz on 3/18/15.
 */
public class BaseActivity extends ActionBarActivity {

    protected ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        progressDialog = new ProgressDialog(this);
    }

    public void showError(Activity activity, String errormsg, boolean bShowCancel, DialogInterface.OnClickListener listener){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setIcon(R.drawable.ic_launcher);
        alert.setTitle(getString(R.string.message));
        alert.setMessage(errormsg);
        if (listener != null){
            alert.setPositiveButton(getString(R.string.accept_lower), listener);
        } else {
            alert.setPositiveButton(getString(R.string.accept_lower), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //finish();
                    //dismiss();
                }
            });
        }
        if (bShowCancel)
            alert.setNegativeButton(getString(R.string.cancel_lower), null);
        alert.show();
    }

    protected void showError(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_launcher);
        alert.setMessage(getString(R.string.error_occurred_server));
        alert.setPositiveButton(getString(R.string.accept_lower), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }


    public void showProgressDialog(String strToShow, boolean cancelable) {
        progressDialog.setMessage(strToShow);
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
            }
        });
        progressDialog.setCancelable(cancelable);
    }

    public void showProgressDialog(String strToShow, DialogInterface.OnCancelListener listenerCancel) {
        progressDialog.setMessage(strToShow);
        progressDialog.show();
        if (listenerCancel != null){
            progressDialog.setOnCancelListener(listenerCancel);
        }
        progressDialog.setCancelable(true);
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }


}
