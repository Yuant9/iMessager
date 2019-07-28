package net.yuan.italker.push.helper;


import android.content.Context;
import android.net.sip.SipAudioCall;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import net.yuan.italker.common.app.Fragment;

/**
 *
 * 完成fragment的调度与重用问题，使fragment切换时，
 * 不会多次重新建fragment实例
 * @author yuan
 * @version 1.1.0
 */
public class NavHelper<T> {
    private final SparseArray<Tab<T>> tabs = new SparseArray<>();
    private final FragmentManager fragmentManager;
    private final int containerId;
    private final Context context;

    //onTabChangedListener 是一个interface，其中定义了 onTabChanged 必须被覆写
    //当某个tab被选中时，listener.onTabChanged就会被调用，以达到listener的作用
    private final onTabChangedListener<T> listener;

    private Tab<T> currentTab;

    public NavHelper( Context context, FragmentManager fragmentManager, int containerId,onTabChangedListener<T> listener ) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.context = context;
        this.listener = listener;

    }

    public static class Tab<T>{

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        public Class<?> clx;
        //额外的字段，用户自己设定
        public T extra;

        android.support.v4.app.Fragment fragment;
    }

    public void add(Tab<T> tab, int menuId){
        tabs.put(menuId, tab);

    }

    public Tab<T> getCurrentTab(){
        return currentTab;
    }

    /**
     * perform onClickItem
     * @param menuId
     * @return
     */
    public boolean performClickMenu(int menuId){
        Tab<T> tab = tabs.get(menuId);
        if(tab !=null){
            doSelect(tab);

            return true;
        }


        return false;
    }

    private void doSelect(Tab<T> tab){
        Tab<T> oldTab= null;

        if(currentTab!=null){
            oldTab = currentTab;
            if (oldTab == tab){
                notifyReselectTab(tab);
                return;
            }
        }
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    public void notifyReselectTab(Tab<T> tab ){
        //TODO reselect
    }

    /**
     * 回调监听器
     * @param currentTab
     * @param oldTab
     */
    public void notifyTabSelect(Tab<T> currentTab, Tab<T> oldTab){
        if(listener !=null){
            listener.onTabChanged(currentTab,oldTab);
        }
    }


    /**
     * 把当前的fragment从界面移除，换上新的fragment
     * @param currentTab
     * @param oldTab
     */
    public void doTabChanged(Tab<T> currentTab, Tab<T> oldTab){
        FragmentTransaction ft =  fragmentManager.beginTransaction();

        if(oldTab !=null){
            if(oldTab.fragment != null) {

                //把fragment从界面移除，但是还在fragment的缓存空间中
                ft.detach(oldTab.fragment);
            }

        }

        if(currentTab !=null){
            if(currentTab.fragment ==null){
                //新建fragment
                android.support.v4.app.Fragment fragment = Fragment.instantiate(context,currentTab.clx.getName(),null);
                //缓存
                currentTab.fragment = fragment;
                //提交到fragment manager
                ft.add(containerId,fragment,currentTab.clx.getName());
            }else{
                //从fragment manager 的缓存空间中调出fragment
                ft.attach(currentTab.fragment);
            }
            ft.commit();

            notifyTabSelect(currentTab,oldTab);
        }



    }

    /**
     * 定义事件处理完后的回调接口
     */
    public interface onTabChangedListener<T>{
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }

}
