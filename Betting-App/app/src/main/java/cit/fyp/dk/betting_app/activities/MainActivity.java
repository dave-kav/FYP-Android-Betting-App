package cit.fyp.dk.betting_app.activities;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.R;
import cit.fyp.dk.betting_app.domain.Race;
import cit.fyp.dk.betting_app.fragments.AccountFragment;
import cit.fyp.dk.betting_app.fragments.MyBetsFragment;
import cit.fyp.dk.betting_app.fragments.RaceFragment;

public class MainActivity extends AppCompatActivity {

    private Customer customer;
    private ArrayList<Race> races;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        customer = (Customer) this.getIntent().getExtras().getSerializable("customer");
        races = (ArrayList<Race>) this.getIntent().getExtras().getSerializable("races");
    }

    @Override
    public void recreate() {
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        customer = (Customer) this.getIntent().getExtras().getSerializable("customer");
        races = (ArrayList<Race>) this.getIntent().getExtras().getSerializable("races");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RaceFragment(), "Racing");
        adapter.addFragment(new MyBetsFragment(), "My Bets");
        adapter.addFragment(new AccountFragment(), "My Account");
        viewPager.setAdapter(adapter);
    }

    @Override
    //create a pop up requesting user to confirm they wish to quit the app
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        dialog.setTitle("Logout");
        dialog.setMessage("Are you sure you wish to exit the app and log out?");

        dialog.setNegativeButton(("No"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialog.setPositiveButton(("Yes"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });

        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Race> getRaces() { return races; }

    public void setRaces(ArrayList<Race> races) { this.races = races; }

}