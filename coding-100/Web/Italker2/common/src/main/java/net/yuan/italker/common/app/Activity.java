package net.yuan.italker.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        if(initArgs(getIntent().getExtras())){
            int layId = getContentLayoutId();
            setContentView(layId);
            initWidget();
            initData();
        }else{
            finish();
        }
    }


    protected void initWindows(){

    }


    /**
     * initialize args
     * @param bundle
     * @return if args are right, return true
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    /**
     * get current resource file's id
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * init widget
     */
    protected void initWidget(){

        ButterKnife.bind(this);

    }

    /**
     * init data
     */

    protected void initData(){

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed(){
        // get all fragments, for each fragment that is instance of
        //net.yuan.italker.common.app.Fragment ,  if this fragment has onBackPressed()
        //then return, otherwise call onBackPressed() and finifh.

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments != null && fragments.size()>0){
            for(Fragment fragment : fragments){
                if (fragment instanceof net.yuan.italker.common.app.Fragment){
                    if(((net.yuan.italker.common.app.Fragment) fragment).onBackPressed()){
                        return ;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
