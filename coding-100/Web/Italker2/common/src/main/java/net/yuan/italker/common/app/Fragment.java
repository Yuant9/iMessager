package net.yuan.italker.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends android.support.v4.app.Fragment {

    protected View mRoot;
    protected Unbinder mRootUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initArgs(getArguments());


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRoot == null){
            //initialize current widget, but don't
            // add layout to parent(viewGroup)-container
            int layId = getContentLayoutId();
            View root = inflater.inflate(layId,container,false);
            initWidget(root);
            mRoot = root;
        }else{
            if(mRoot.getParent()!=null){
                //remove current layout from its parent
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }

        }



        return mRoot;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initData();

    }

    /**
     * get the current resource file id
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutId();


    protected void initWidget(View root){
        mRootUnBinder = ButterKnife.bind(this,root);

    }

    protected void initData(){

    }

    protected void initArgs(Bundle bundle){

    }

    /**
     *if return true, it means the fragment already deal with the
     * onBackPressed logic
     * Activity doesn't need to finish()
     * @return
     */
    public boolean onBackPressed(){
        return false;

    }




}
