package net.yuan.italker.common.widget.recycler;

import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.yuan.italker.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
    implements View.OnClickListener, View.OnLongClickListener,AdapterCallback<Data>{

    private List<Data> mDataList = new ArrayList<> ();

    private AdapterListener<Data> mListener;

    public RecyclerAdapter(List<Data> mDataList, AdapterListener<Data> mListener) {
        this.mDataList = mDataList;
        this.mListener = mListener;
    }


    public RecyclerAdapter(AdapterListener<Data> mListener) {
        this(new ArrayList<Data>(),mListener);
    }

    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        //get inflater to initialize xml as view
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        //initialize xml file whose id is viewType to a view
        View convertView = inflater.inflate(viewType, viewGroup,false);

        //create view holder
        ViewHolder<Data> holder = onCreateViewHolder(convertView,viewType);

        convertView.setOnClickListener(this);

        convertView.setOnLongClickListener(this);


        //convertView.setTag(holder);
        convertView.setTag(R.id.tag_recycler_holder,holder);


        //界面注解绑定
        holder.unbinder = ButterKnife.bind(holder,convertView);

        //绑定callback
        holder.callback = this;


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> dataViewHolder, int position) {
        Data data = mDataList.get(position);
        dataViewHolder.bind(data);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * add data and notify data change
     * @param data
     */
    public void add(Data data){
        mDataList.add(data);
        notifyItemInserted(mDataList.size()-1);
    }

    /**
     * add list of data and notify data change
     * @param dataList
     */
    public void add(Data...dataList){
        if(dataList!=null){
            int beforePos = mDataList.size();
            Collections.addAll(mDataList,dataList);
            notifyItemRangeInserted(beforePos,dataList.length);
        }
    }

    /**
     * add a collection of data
     * @param dataCollection
     */
    public void add(Collection<Data> dataCollection){
        if(dataCollection != null){
            int beforePos = mDataList.size();
            mDataList.addAll(dataCollection);
            notifyItemRangeInserted(beforePos,dataCollection.size());
        }
    }

    public void clear(){
       mDataList.clear();
       notifyDataSetChanged();
    }

    /**
     * replace current data list with a new data list
     * @param dataList
     */
    public void replace(Collection<Data> dataList){
        mDataList.clear();
        if(dataList == null || dataList.size() == 0){
            return;
        }
        mDataList.addAll(dataList);
        notifyDataSetChanged();

    }

    @Override
    public void onClick(View v){
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(this.mListener!=null){
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder,mDataList.get(pos));
        }


    }


    @Override
    public boolean onLongClick(View v){
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if(this.mListener!=null){
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder,mDataList.get(pos));
            return true;
        }
        return false;


    }

    public void setListener(AdapterListener<Data> adapterListener){
        this.mListener = adapterListener;
    }


    /**
     * customize Adapter listener
     * @param <Data>  generic
     */
    public interface AdapterListener<Data>{
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * create a viewHolder
     * @param convertView
     * @param viewType  xml file's id
     * @return  ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View convertView, int viewType);

    /**
     * customize view holder
     * @param <Data>  generic
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{
        protected Data mData;
        private Unbinder unbinder;
        private AdapterCallback<Data> callback;

        public ViewHolder(View itemView){
            super(itemView);
        }

        void bind (Data data){
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的回调的时候，必须覆写绑定的数据
         * @param data
         */
        protected abstract void onBind(Data data);

        public void updateData(Data data){
            if(this.callback!=null){
                this.callback.update(data,this);
            }

        }


    }
}
