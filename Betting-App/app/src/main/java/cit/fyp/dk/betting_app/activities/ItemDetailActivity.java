package cit.fyp.dk.betting_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import cit.fyp.dk.betting_app.R;
import cit.fyp.dk.betting_app.domain.Bet;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.fragments.ItemDetailFragment;
import cit.fyp.dk.betting_app.fragments.MyBetsFragment;

/**
 * Created by davyk on 30/03/2017.
 */

public class ItemDetailActivity extends AppCompatActivity {

    private Bet bet;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        customer = (Customer) this.getIntent().getExtras().getSerializable("customer");

        int betID = this.getIntent().getIntExtra(ItemDetailFragment.ARG_ITEM_ID, 0);
        for (Bet b: customer.getBets()) {
            if (b.getBetID() == betID)
                bet = b;
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(bet.getStatus() + "");
        }

        TextView betIdTv = (TextView) findViewById(R.id.bet_id);
        betIdTv.setText(String.format("%d", betID));

        TextView stakeTv = (TextView) findViewById(R.id.bet_stake);
        stakeTv.setText(String.format("\u20ac%.2f", bet.getStake()));

        TextView horseNumberTv = (TextView) findViewById(R.id.horse_number);
        horseNumberTv.setText(String.format("%d", bet.getHorse().getNumber()));

        TextView horseNameTv = (TextView) findViewById(R.id.horse_name);
        horseNameTv.setText(bet.getHorse().getName());

        TextView raceTimeTv = (TextView) findViewById(R.id.race_time);
        raceTimeTv.setText(bet.getRace().getTime());

        TextView raceTrackTv = (TextView) findViewById(R.id.race_track);
        raceTrackTv.setText(bet.getRace().getTrack());

        TextView winningsTv = (TextView) findViewById(R.id.winnings);
        winningsTv.setText(String.format("\u20ac%.2f", bet.getWinnings()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MyBetsFragment.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
