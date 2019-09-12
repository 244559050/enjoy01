package com.example.androidperformancetest.fragment.performance.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;

import com.example.androidperformancetest.R;
import com.example.androidperformancetest.fragment.base.BaseFragment;


public class ViewStubDemoFragment extends BaseFragment {
    private Context mContext = null;
    static private boolean changeView = false;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getHostActivity();
        View view = inflater.inflate(R.layout.fm_xml_show, container, false);
        if (changeView) {
            ViewStub stub = (ViewStub) view.findViewById(R.id.viewstub_text);
            stub.inflate();
            changeView = false;
        } else {
            ViewStub stub = (ViewStub) view.findViewById(R.id.viewstub_image);
            stub.inflate();
            changeView = true;
        }
        return view;
    }

    @Override
    protected void resume() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void pause() {

    }

    @Override
    protected void start() {

    }

    @Override
    public void onEnterAnimationEnd(Animation animation) {

    }

    @Override
    public void clearView() {

    }

    @Override
    public void clear() {

    }

    @Override
    protected void initData(Bundle data) {

    }
}
