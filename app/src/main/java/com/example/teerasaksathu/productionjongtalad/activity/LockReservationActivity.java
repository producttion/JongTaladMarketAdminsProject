package com.example.teerasaksathu.productionjongtalad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.teerasaksathu.productionjongtalad.R;
import com.example.teerasaksathu.productionjongtalad.fragment.LockReservationFragment;

public class LockReservationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_reservation);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, LockReservationFragment.newInstance())
                    .commit();
        }


    }

}



