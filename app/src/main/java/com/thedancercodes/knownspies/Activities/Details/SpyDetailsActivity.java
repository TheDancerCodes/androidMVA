package com.thedancercodes.knownspies.Activities.Details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedancercodes.knownspies.Coordinators.RootCoordinator;
import com.thedancercodes.knownspies.Dependencies.DependencyRegistry;
import com.thedancercodes.knownspies.R;

public class SpyDetailsActivity extends AppCompatActivity {

    private SpyDetailsPresenter presenter;
    private RootCoordinator coordinator;
    private ImageView profileImage;
    private TextView  nameTextView;
    private TextView  ageTextView;
    private TextView  genderTextView;
    private ImageButton calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spy_details);
        attachUI();

        // Add Dependency Registry
        Bundle bundle = getIntent().getExtras();
        DependencyRegistry.shared.inject(this, bundle);
    }

    //region UI Methods

    private void attachUI(){
        profileImage    = (ImageView)   findViewById(R.id.details_profile_image);
        nameTextView    = (TextView)    findViewById(R.id.details_name);
        ageTextView     = (TextView)    findViewById(R.id.details_age);
        genderTextView  = (TextView)    findViewById(R.id.details_gender);
        calculateButton = (ImageButton) findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(v -> gotoSecretDetails());
    }

    // endregion

    //region Injection Methods

    public void configureWith(SpyDetailsPresenter presenter, RootCoordinator coordinator) {
        this.presenter = presenter;
        this.coordinator = coordinator;

        ageTextView.setText(presenter.getAge());
        nameTextView.setText(presenter.getName());
        genderTextView.setText(presenter.getGender());
        profileImage.setImageResource(presenter.getImageId());

    }

    //endregion

    //region navigation

    private void gotoSecretDetails() {
        if (presenter == null) return;

        coordinator.handleSecretButtonTapped(this, presenter.getSpyId());
    }

  //endregion
}
