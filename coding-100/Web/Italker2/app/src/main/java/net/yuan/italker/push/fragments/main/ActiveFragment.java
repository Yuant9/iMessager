package net.yuan.italker.push.fragments.main;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.yuan.italker.common.widget.GalleyView;
import net.yuan.italker.push.R;

import butterknife.BindView;


public class ActiveFragment extends net.yuan.italker.common.app.Fragment {

    @BindView(R.id.galleyView)
    GalleyView mGalley;


    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId(){
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        mGalley.setup(getLoaderManager(), new GalleyView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });

    }
}
