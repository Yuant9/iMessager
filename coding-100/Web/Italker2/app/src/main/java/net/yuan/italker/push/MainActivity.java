package net.yuan.italker.push;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;
import net.yuan.italker.common.Common;
import net.yuan.italker.common.app.Activity;
import net.yuan.italker.common.widget.PortraitView;
import net.yuan.italker.push.fragments.main.ActiveFragment;
import net.yuan.italker.push.fragments.main.ContactFragment;
import net.yuan.italker.push.fragments.main.GroupFragment;
import net.yuan.italker.push.helper.NavHelper;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener
,NavHelper.onTabChangedListener<Integer>{


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

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    @Override
    protected void initData(){
        super.initData();

        Menu menu = mNavigation.getMenu();
        //手动触发第一次点击，点击home
        menu.performIdentifierAction(R.id.action_home,0);




    }

    private NavHelper<Integer> mNavHelper ;



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

        mNavHelper = new NavHelper(this,getSupportFragmentManager(),R.id.lay_container,this);

        mNavHelper.add(new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home),R.id.action_home);
        mNavHelper.add(new NavHelper.Tab<>(GroupFragment.class, R.string.title_group),R.id.action_group);
        mNavHelper.add(new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact),R.id.action_contact);



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


        return mNavHelper.performClickMenu(item.getItemId());

    }


    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab){
        mTitle.setText(newTab.extra);

        //对浮动按钮进行隐藏与显示
        float transY=0;
        float rotation=0;

        if(Objects.equals(newTab.extra,R.string.title_home)){
            transY= Ui.dipToPx(getResources(),130);
        }else{
            if(Objects.equals(R.string.title_group,newTab.extra)){
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            }else{
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;

            }
        }

        //开始浮动按钮的动画
        mAction.animate().rotation(rotation).translationY(transY).setDuration(480).start();
    }



}
