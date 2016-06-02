package test.bluerain.youku.com.downloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import test.bluerain.youku.com.downloader.R;
import test.bluerain.youku.com.downloader.entities.ViewItemInfo;

/**
 * Created by rain on 2016/6/3.
 */

public class MainListViewAdapter extends BaseAdapter {

    private List<ViewItemInfo> viewItemInfos;
    private int mLayoutId;
    private Context mContext;

    public MainListViewAdapter(List<ViewItemInfo> viewItemInfos, int mLayoutId, Context mContext) {
        this.viewItemInfos = viewItemInfos;
        this.mLayoutId = mLayoutId;
        this.mContext = mContext;
    }

    public void setViewItemInfos(List<ViewItemInfo> viewItemInfos) {
        this.viewItemInfos = viewItemInfos;
    }

    public void setmLayoutId(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    @Override
    public int getCount() {
        return viewItemInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return viewItemInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        ViewItemInfo viewItemInfo = viewItemInfos.get(position);
        holder.txvMainSpeed.setText("下载速度：[" + viewItemInfo.mSpeed + "]");
        holder.txvMainDuration.setText("用时：[" + viewItemInfo.mDuration + "]");
        holder.txvMainIndex.setText("第：" + viewItemInfo.mIndex + "下载");
        holder.progressBarMain.setProgress(viewItemInfo.mProgress);

        return convertView;
    }


    class ViewHolder {
        public View rootView;
        public TextView txvMainSpeed;
        public TextView txvMainDuration;
        public TextView txvMainIndex;
        public ProgressBar progressBarMain;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            txvMainSpeed = (TextView) rootView.findViewById(R.id.txv_main_speed);
            txvMainDuration = (TextView) rootView.findViewById(R.id.txv_main_duration);
            txvMainIndex = (TextView) rootView.findViewById(R.id.txv_main_index);
            progressBarMain = (ProgressBar) rootView.findViewById(R.id.progressBar_main);
        }

    }
}
