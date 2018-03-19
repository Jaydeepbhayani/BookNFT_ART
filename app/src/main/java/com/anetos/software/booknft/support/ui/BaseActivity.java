package com.anetos.software.booknft.support.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.anetos.software.booknft.R;

import butterknife.ButterKnife;

/**
 * Created by Jayesh on 11-01-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BASE";
    private final Runnable mHideKeyBoard = () -> {
        View view = BaseActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) BaseActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
    public boolean progressAlive = false;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutResourceId());
        ButterKnife.bind(this);
        if (shouldInflateToolbar())
            inflateToolBar();
        // clearLightStatusBar(this.getWindow().getDecorView());
        //setLightStatusBar(getWindow().getDecorView());
    }

    public void clearLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public void setLightStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.material_grey_200_));
        }
    }

    protected abstract boolean shouldInflateToolbar();

    protected void inflateToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract int provideLayoutResourceId();

    @Override
    public void finish() {
        super.finish();
        hideKeyboard___();
        _overridePendingTransitionExit();
    }

    public void finishNormal() {
        hideKeyboard___();
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        hideKeyboard___();
        _overridePendingTransitionEnter();
    }

    public void startActivityNormal(Intent intent) {
        super.startActivity(intent);
    }

    protected void _overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void _overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void showMsgAndClose___(String msg) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                this).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(msg);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        alertDialog.setOnCancelListener(dialog -> finish());
        alertDialog.show();
    }

    public void showMsg___(String msg, boolean cancelableTouchOutside) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                this).create();
        alertDialog.setCanceledOnTouchOutside(cancelableTouchOutside);
        alertDialog.setMessage(msg);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void showSnackBar___(CoordinatorLayout container, String message, String buttonText) {
        final Snackbar snackbar = Snackbar.make(container, message, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.setAction(buttonText, view -> snackbar.dismiss());
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        snackbar.show();
    }

    public void isConnectedToInternetAndShowAlertDialog___() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            try {
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alert_checkinternet);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btn = dialog.findViewById(R.id.btn);
                btn.setOnClickListener(v -> {
                    dialog.cancel();
                    // isConnectedToInternetAndShowAlertDialog___();
                });
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            Toast.makeText(this, "Network Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isConnectedToInternet___() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void hideKeyboard___() {
        // Check if no view has focus:
        mHideKeyBoard.run();
    }

    public void showDialog___(String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                try {
                    pDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressAlive = false;
            }
            pDialog = new ProgressDialog(this);
            pDialog.setMessage(message);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setCancelable(false);
            progressAlive = true;
            pDialog.show();
        } else {
            if (progressAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                progressAlive = false;
            }
        }
    }
}
