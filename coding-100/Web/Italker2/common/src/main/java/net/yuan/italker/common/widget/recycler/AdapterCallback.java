package net.yuan.italker.common.widget.recycler;


/**
 * @author Yuan
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);

}
