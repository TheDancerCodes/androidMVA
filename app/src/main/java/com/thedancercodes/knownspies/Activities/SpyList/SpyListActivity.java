package com.thedancercodes.knownspies.Activities.SpyList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thedancercodes.knownspies.Activities.Details.SpyDetailsActivity;
import com.thedancercodes.knownspies.Dependencies.DependencyRegistry;
import com.thedancercodes.knownspies.Helpers.Constants;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;
import com.thedancercodes.knownspies.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpyListActivity extends AppCompatActivity {

    private static final String TAG = "SpyListActivity";

    private SpyListPresenter presenter;
    private List<SpyDTO> spies = new ArrayList<>();
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

    public void configureWith(SpyListPresenter presenter) {
        this.presenter = presenter;

        // Initializes the list view and the data.
        loadData();
    }

    //endregion

    //region Helper Methods
    private void attachUI() {
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.spy_recycler_view);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        initializeListView();
    }


    //endregion

    //region Data Process specific to SpyListActivity

    private void loadData() {
        presenter.loadData(this::spiesUpdated, this::onDataReceived);

    }


    private void spiesUpdated(List<SpyDTO> spies) {
            this.spies = spies;

            SpyViewAdapter adapter = (SpyViewAdapter) recyclerView.getAdapter();
            adapter.spies = this.spies;
            adapter.notifyDataSetChanged();
    }

    //endregion

    //region User Interaction

    private void rowTapped(int position) {
        SpyDTO spy = spies.get(position);
        gotoSpyDetails(spy.id);
    }

    private void onDataReceived(Source source) {
        String message = String.format("Data from %s", source.name());
        Toast.makeText(SpyListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //endregion

    //region List View Adapter

    private void initializeListView() {
        SpyViewAdapter adapter = new SpyViewAdapter(spies, (v, position) -> rowTapped(position));
        recyclerView.setAdapter(adapter);
    }

    //endregion

    //region Navigation

    private void gotoSpyDetails(int spyId) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.spyIdKey, spyId);

        Intent intent = new Intent(SpyListActivity.this, SpyDetailsActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

  //endregion

}
