package net.yuan.italker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.yuan.italker.common.Common;
import net.yuan.italker.common.app.Activity;
import net.yuan.italker.common.widget.PortraitView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Override
    protected void initData(){

    }


    @Override
    protected int getContentLayoutId(){
        return R.layout.activity_main;
    }

    /**
     * 设置app bar的背景图片，
     * 不再layout.xml中设置是因为会把图片拉伸
     */
    @Override
    protected void initWidget(){
        super.initWidget();

        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this).load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation animation){
                        this.view.setBackground(resource.getCurrent());
                    }
                });




    }
    @OnClick(R.id.im_search)
    void onSearchMenuClick(){


    }

    @OnClick(R.id.btn_action)
    void onActionClick(){


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        mTitle.setText(item.getTitle());
        return false;
    }



}
