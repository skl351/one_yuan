package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.user_preent_3_adapter;
import com.yiyuangou.android.one_yuan.adapter.user_present_adapter;
import com.yiyuangou.android.one_yuan.bean.User_present;
import com.yiyuangou.android.one_yuan.bean.dingdan_zengsong_item;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class user_zengsong3 extends Fragment {
    private View view;
    private user_preent_3_adapter adapter;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<dingdan_zengsong_item> list ;
    private View xiangyaotoum;
    private View zhanwushuju;
    int flag=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_zengsong3, container, false);
        init();
        init_wangluo_init();

          /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());
        return view;

    }

    protected void init_wangluo_init() {
        flag=1;
        xiangyaotoum.setVisibility(View.VISIBLE);
        zhanwushuju.setVisibility(View.GONE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_sended;
        RequestParams requestParams=new RequestParams();
        requestParams.put("SDYHID", User.getuser().getUser_uuid());
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("donateLog");
                        list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String name = jsonArray.getJSONObject(i).getString("name");
                            String image_url = jsonArray.getJSONObject(i).getString("img");
                            String zsje = jsonArray.getJSONObject(i).getString("ZSJE");
                            String zt = jsonArray.getJSONObject(i).getString("DHZT");
                            String sdje = jsonArray.getJSONObject(i).getString("ZSKCJD");
                            dingdan_zengsong_item d1 = new dingdan_zengsong_item();
                            d1.setSPMC(name);
                            d1.setName(jsonArray.getJSONObject(i).getString("userName"));
                            d1.setOther_phone(jsonArray.getJSONObject(i).getString("phone"));
                            d1.setAll_jd(zsje);
                            d1.setUrl_img(image_url);
                            d1.setZT(zt);
                            d1.setSdje(sdje);
                            d1.setUuid(jsonArray.getJSONObject(i).getString("UUID"));
                            list.add(d1);
                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter = new user_preent_3_adapter(getActivity(), list);
                        gridView.setAdapter(adapter);
                    } else {
                        zhanwushuju.setVisibility(View.VISIBLE);
                    }
                    xiangyaotoum.setVisibility(View.GONE);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                xiangyaotoum.setVisibility(View.GONE);
                zhanwushuju.setVisibility(View.VISIBLE);
            }
        });
    }
    protected void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_sended;
        RequestParams requestParams=new RequestParams();
        requestParams.put("SDYHID", User.getuser().getUser_uuid());
        flag=flag+1;
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("donateLog");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String name=jsonArray.getJSONObject(i).getString("name");
                            String image_url=jsonArray.getJSONObject(i).getString("img");
                            String zsje=jsonArray.getJSONObject(i).getString("ZSJE");
                            String zt=jsonArray.getJSONObject(i).getString("DHZT");
                            String sdje=jsonArray.getJSONObject(i).getString("ZSKCJD");
                            dingdan_zengsong_item d1=new dingdan_zengsong_item();
                            d1.setSPMC(name);
                            d1.setAll_jd(zsje);
                            d1.setUrl_img(image_url);
                            d1.setZT(zt);
                            d1.setSdje(sdje);
                            d1.setUuid(jsonArray.getJSONObject(i).getString("UUID"));
                            list.add(d1);
                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter .notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
            }
        });
    }

    private void init() {
        list=new ArrayList<dingdan_zengsong_item>();
        gridView = (GridView) view.findViewById(R.id.content_view);
        xiangyaotoum=view.findViewById(R.id.xiangyaotoum);
        zhanwushuju=view.findViewById(R.id.zhanwushuju);
    }

    /**
     * 在其中实现上拉和下拉的功能-----4----最主要的地方
     * @return
     */
    private PullToRefreshLayout.OnRefreshListener getlin() {
        return re=new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        init_wangluo_init();
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);


            }


            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                init_wangluo_init2();
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

            }
        };

    }

}