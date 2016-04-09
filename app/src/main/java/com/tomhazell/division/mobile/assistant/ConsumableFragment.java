package com.tomhazell.division.mobile.assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tomhazell.division.battleassistant.R;
import com.tomhazell.division.mobile.assistant.Intro.IntroActivity;

/**
 * Created by Tom Hazell on 01/04/2016.
 *
 * This is the fragment for the view pager. It will show
 */
public class ConsumableFragment extends Fragment implements MainView {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private ConsumablesPresenter presenter;

    private String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        type = getArguments().getString("Type");
        Log.w("app", type);
        return inflater.inflate(R.layout.fragment_consumable, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        presenter = new ConsumablesPresenterImpl(this, getActivity());

        mRecyclerView = (RecyclerView) v.findViewById(R.id.RecyclerConsumables);
        mRecyclerView.setHasFixedSize(true);


        SharedPreferences prefs = getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE);
        String view = prefs.getString("view", "List");
        int rows;
        int gap;
        int itemLayout;

        switch(view){
            case "List":
                rows = 1;
                gap = 10;
                itemLayout = R.layout.listitem2;
                break;
            case "Icon":
                rows = 3;
                gap = 35;
                itemLayout = R.layout.list_item_icon;
                break;
            default:
                rows = 1;
                gap = 10;
                itemLayout = R.layout.listitem2;
                break;
        }


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), rows));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), gap, false));

        mAdapter = new ListAdapter(presenter.getListItems(type), itemLayout,  getActivity(), new ListAdapter.OnRecyclerClick(){
            @Override public void onItemClick(ListItem item) {
                presenter.onClick(item);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showToast(String message) {
        MainView view = (MainView) getActivity();
        view.showToast(message);
    }

    @Override
    public void navigateToFirstLanuch() {
        Intent i = new Intent(getActivity(), IntroActivity.class);
        startActivity(i);
    }

    @Override
    public void navigateToTheWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://TheDivisionBA.com"));
        startActivity(intent);
    }
}
