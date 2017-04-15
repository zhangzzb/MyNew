package com.zzb.mynew.fragment.video;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zzb.mynew.activity.VideoDetailActivity;
import com.zzb.mynew.adapter.VideoAdapter;
import com.zzb.mynew.api.ApiClient;
import com.zzb.mynew.bean.video.netease.VideoBean;
import com.zzb.mynew.fragment.base.BaseListFragment;
import com.zzb.mynew.okhttp.IOkHttpStringCallBack;
import com.zzb.mynew.util.OkHttpUtil;
import com.zzb.mynew.view.EmptyLayout;

import java.util.List;

/**
 * @author 张智斌
 * @time 2017/4/9 22:19
 * @desc ${TODD}
 */

public class RecommendFragment extends BaseListFragment {
    private String url= ApiClient.getVideoUrl();
    @Override
    protected void initUIData() {
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<VideoBean> itemsBean=(List<VideoBean>) adapter.getData();
                VideoDetailActivity.startActionNet(position,getContext(),itemsBean);
            }
        });
    }
    @Override
    protected BaseQuickAdapter getListAdapter() {
        return new VideoAdapter();
    }
    @Override
    protected void sendRequestData() {
        OkHttpUtil.requestGETStringResult(url, new IOkHttpStringCallBack() {
            @Override
            public void onSuccess(String result) {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                List<VideoBean> videoBeanList = VideoBean.parse(result);
                mAdapter.setNewData(videoBeanList);
                finshRefresh();
            }
            @Override
            public void onFailure(Exception e) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }
        });
    }
    @Override
    public void onRefresh() {
        super.onRefresh();
        sendRequestData();
    }
}
