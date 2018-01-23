package com.thedancercodes.knownspies.Activities.SecretDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thedancercodes.knownspies.Activities.SpyList.SpyListActivity;
import com.thedancercodes.knownspies.Helpers.Constants;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.R;

import io.realm.Realm;

public class SecretDetailsActivity extends AppCompatActivity {

    SecretDetailsPresenter presenter;

    ProgressBar progressBar;
    TextView crackingLabel;
    Button finishedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_details);

        attachUI();
        parseBundle();
    }

    // Set up the UI according to when we get a SecretDetailsPresenter
    public void configure(SecretDetailsPresenter presenter) {
        this.presenter = presenter;

        presenter.crackPassword(password -> {
          progressBar.setVisibility(View.GONE);
          crackingLabel.setText(presenter.password);
        });
    }

    //region Helper Methods

    private void attachUI() {
        progressBar    = (ProgressBar) findViewById(R.id.secret_progress_bar);
        crackingLabel  = (TextView)    findViewById(R.id.secret_cracking_label);
        finishedButton = (Button)      findViewById(R.id.secret_finished_button);

        finishedButton.setOnClickListener(v -> finishedClicked());

    }

  //region Helper Method
  private void setupPresenterFor(int spyId) {
      configure(new SecretDetailsPresenter(spyId));
  }
  //endregion


    private void parseBundle() {
        Bundle b = getIntent().getExtras();

        if(b != null) {
          int spyId = b.getInt(Constants.spyIdKey);
          setupPresenterFor(spyId);
        }
    }

    //endregion

    //region User Interaction

    private void finishedClicked() {
        Intent intent = new Intent(this, SpyListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    //endregion

}
