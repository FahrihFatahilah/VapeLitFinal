package vapelit.unikomapps.com.vapelitfinal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Main2Activity extends AppCompatActivity {
    private EditText volts, amps, ohms, watts;
    private List<EditText> editTexts;
    private Dialog dialog;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        initializeDialog();
        initializeEditTexts();
        initializeButtons();
        MobileAds.initialize(this, "ca-app-pub-9325254560662975~3258487880");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9325254560662975/9668789466");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }




    private void initializeDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_pop_up);
        dialog.setTitle(getResources().getText(R.string.alert_title));
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog.setTitle(getResources().getText(R.string.alert_title));
                TextView description = (TextView) dialog.findViewById(R.id.alert_description);
                description.setText(getResources().getText(R.string.alert_description));
                Button close = (Button) dialog.findViewById(R.id.alert_close);
                close.setText(getResources().getText(R.string.alert_close));
            }
        });

        Button alertClose = (Button) dialog.findViewById(R.id.alert_close);
        alertClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
    }

    /** Initializes the edit text fields */
    private void initializeEditTexts() {
        editTexts = new ArrayList<>();

        volts = (EditText) findViewById(R.id.volts_value);
        amps = (EditText) findViewById(R.id.amps_value);
        ohms = (EditText) findViewById(R.id.ohms_value);
        watts = (EditText) findViewById(R.id.watts_value);

        editTexts.add(volts);
        editTexts.add(amps);
        editTexts.add(ohms);
        editTexts.add(watts);
    }

    /** Initializes all buttons */
    private void initializeButtons() {
        Button calculate = (Button) findViewById(R.id.calculate);
        Button reset = (Button) findViewById(R.id.reset);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidEntries()) {
                    calculate();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText text : editTexts) {
                    text.setText("");
                }
            }
        });
    }
    private boolean isValidEntries() {
        int enteredCount = 0;
        for (EditText text : editTexts) {

            if (!text.getText().toString().equals("") && !text.getText().toString().equals("0")) {
                enteredCount += 1;
            }

        }

        if (enteredCount < 2 || enteredCount > 2) {

            if (!dialog.isShowing()) {
                dialog.show();
            }

            return false;
        }

        return true;
    }


    private void calculate() {
        double value1 = 0;
        double value2 = 0;
        int index1 = -1;
        int index2 = -1;
        int count = 0;

        for (EditText text : editTexts) {
            if (!text.getText().toString().equals("") && !text.getText().toString().equals("0")) {
                if (value1 == 0) {
                    value1 = Double.parseDouble(text.getText().toString());
                    index1 = count;
                } else {
                    value2 = Double.parseDouble(text.getText().toString());
                    index2 = count;
                }
            }
            count++;
        }

        if (index1 == 0 && index2 == 1) {
            double ohmsValue = value1 / value2;
            double wattsValue = value1 * value2;
            ohms.setText(String.valueOf(ohmsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 0 && index2 == 2) {
            double ampsValue = value1 / value2;
            double wattsValue = (Math.pow(value1, 2)) / value2;
            amps.setText(String.valueOf(ampsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 0 && index2 == 3) {
            double ampsValue = value2 / value1;
            double ohmsValue = (Math.pow(value1, 2)) / value2;
            amps.setText(String.valueOf(ampsValue));
            ohms.setText(String.valueOf(ohmsValue));
        } else if (index1 == 1 && index2 == 2) {
            double voltsValue = value1 * value2;
            double wattsValue = (Math.pow(value1, 2)) * value2;
            volts.setText(String.valueOf(voltsValue));
            watts.setText(String.valueOf(wattsValue));
        } else if (index1 == 1 && index2 == 3) {
            double voltsValue = value2 / value1;
            double ohmsValue =  value2 / (Math.pow(value1, 2));
            volts.setText(String.valueOf(voltsValue));
            ohms.setText(String.valueOf(ohmsValue));
        } else if (index1 == 2 && index2 == 3) {
            double voltsValue = Math.sqrt(value1 * value2);
            double ampsValue = Math.sqrt(value2 / value1);
            volts.setText(String.valueOf(voltsValue));
            amps.setText(String.valueOf(ampsValue));
        }
    }
}



