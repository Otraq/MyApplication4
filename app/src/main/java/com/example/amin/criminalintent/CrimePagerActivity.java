package com.example.amin.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeDetailFragment.Callbacks {

    public static final String EXTRA_CRIME_ID = "com.example.amin.criminalintent.CrimePagerActivity.crime_id";
    public static final String TAG = "CrimePagerActivity";

    private ViewPager viewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mCrimes = CrimeLab.getInstance(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager = (ViewPager)findViewById(R.id.crime_detail_view_pager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, "position: " + position);
                Crime crime = mCrimes.get(position);
                return CrimeDetailFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(CrimeLab.getInstance(this).getIndex(crimeId));
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
