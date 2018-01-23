package com.thedancercodes.knownspies.Activities.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedancercodes.knownspies.Activities.SecretDetails.SecretDetailsActivity;
import com.thedancercodes.knownspies.Helpers.Constants;
import com.thedancercodes.knownspies.R;

public class SpyDetailsActivity extends AppCompatActivity {

    private SpyDetailsPresenter presenter;
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
        parseBundle();
    }

    // When we are given the Presenter, we set up from it
    public void configure(SpyDetailsPresenter presenter) {
        this.presenter = presenter;

        // Helps us get IDs to show the image
        this.presenter.configureWithContext(this);
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


    private void configureUIWith(SpyDetailsPresenter presenter) {
        ageTextView.setText(presenter.age);
        nameTextView.setText(presenter.name);
        genderTextView.setText(presenter.gender);
        profileImage.setImageResource(presenter.imageId);
    }

    // endregion

    //region Dependency Methods

    // When an ID is passed in, we will do some injection stuff here
    private void getPresenterFor(int spyId) {
        configure(new SpyDetailsPresenter(spyId));
    }

    //endregion

    // region Navigation
    private void parseBundle() {
        Bundle b = getIntent().getExtras();

        if(b != null) {
            int spyId = b.getInt(Constants.spyIdKey);
            getPresenterFor(spyId);
        }
    }

    //endregion


    //region navigation

    private void gotoSecretDetails() {
        Bundle bundle = new Bundle();
               bundle.putInt(Constants.spyIdKey, presenter.spyId);

        Intent intent = new Intent(SpyDetailsActivity.this, SecretDetailsActivity.class);
               intent.putExtras(bundle);

        startActivity(intent);
    }

    //endregion
}
