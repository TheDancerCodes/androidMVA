package com.thedancercodes.knownspies.Activities.SecretDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thedancercodes.knownspies.Activities.SpyList.SpyListActivity;
import com.thedancercodes.knownspies.Coordinators.RootCoordinator;
import com.thedancercodes.knownspies.Dependencies.DependencyRegistry;
import com.thedancercodes.knownspies.R;

public class SecretDetailsActivity extends AppCompatActivity {

    SecretDetailsPresenter presenter;
    private RootCoordinator coordinator;

    ProgressBar progressBar;
    TextView crackingLabel;
    Button finishedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_details);

        attachUI();

         // Add Dependency Registry
        Bundle bundle = getIntent().getExtras();
        DependencyRegistry.shared.inject(this, bundle);
    }

    // Set up the UI according to when we get a SecretDetailsPresenter
    public void configureWith(SecretDetailsPresenter presenter, RootCoordinator coordinator) {
        this.presenter = presenter;
        this.coordinator = coordinator;

        this.presenter.crackPassword(password -> {
          progressBar.setVisibility(View.GONE);
          crackingLabel.setText(presenter.getPassword());
        });
    }

    //region Helper Methods

    private void attachUI() {
        progressBar    = (ProgressBar) findViewById(R.id.secret_progress_bar);
        crackingLabel  = (TextView)    findViewById(R.id.secret_cracking_label);
        finishedButton = (Button)      findViewById(R.id.secret_finished_button);

        finishedButton.setOnClickListener(v -> finishedClicked());

    }

    //region User Interaction

    private void finishedClicked() {

        coordinator.handleFinishedClicked(this);
    }

    //endregion

}
