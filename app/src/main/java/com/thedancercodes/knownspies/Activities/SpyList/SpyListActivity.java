package com.thedancercodes.knownspies.Activities.SpyList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.thedancercodes.knownspies.Coordinators.RootCoordinator;
import com.thedancercodes.knownspies.Dependencies.DependencyRegistry;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.R;

import java.util.ArrayList;
import java.util.List;

public class SpyListActivity extends AppCompatActivity {

    private static final String TAG = "SpyListActivity";

    private SpyListPresenter presenter;
    private RootCoordinator coordinator;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spy_list);

        // Gets RecyclerView and sets it up for us
        attachUI();

        DependencyRegistry.shared.inject(this);
    }

    //region Injection Methods

    public void configureWith(SpyListPresenter presenter, RootCoordinator coordinator) {
        this.presenter = presenter;
        this.coordinator = coordinator;

        // Initializes the list view and the data.
        loadData();

        // Setup Observables
        setupObservables();
    }

    private void setupObservables() {
        presenter.spies().subscribe(spies -> {
            SpyViewAdapter adapter = (SpyViewAdapter) recyclerView.getAdapter();
            adapter.setSpies(spies);
        });
    }

    //endregion

    //region Helper Methods
    private void attachUI() {

        // Set the Button
        Button newSpyButton = (Button) findViewById(R.id.new_spy_button);
        newSpyButton.setOnClickListener(v -> presenter.addNewSpy());

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.spy_recycler_view);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        initializeListView();
    }


    //endregion

    //region Data Process specific to SpyListActivity

    private void loadData() {
        presenter.loadData(this::onDataReceived);
    }

    //endregion

    //region User Interaction

    private void rowTapped(int position) {
        SpyDTO spy = presenter.spies().getValue().get(position);
        gotoSpyDetails(spy.id);
    }

    private void onDataReceived(Source source) {
        String message = String.format("Data from %s", source.name());
        Toast.makeText(SpyListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region List View Adapter

    private void initializeListView() {
        SpyViewAdapter adapter = new SpyViewAdapter((v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    //endregion

    //region Navigation

    private void gotoSpyDetails(int spyId) {

        coordinator.handleSpyCellTapped(this, spyId);
    }

  //endregion

}
