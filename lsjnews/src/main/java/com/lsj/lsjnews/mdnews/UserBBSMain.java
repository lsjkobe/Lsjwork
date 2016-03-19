package com.lsj.lsjnews.mdnews;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.alibaba.fastjson.JSON;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.lsj.lsjnews.R;
import com.lsj.lsjnews.adapter.UserMsgAdapter;
import com.lsj.lsjnews.base.MyBaseActivity;
import com.lsj.lsjnews.base.NewCommonCallBack;
import com.lsj.lsjnews.bean.mdnewsBean.bbsBean;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class UserBBSMain extends MyBaseActivity{

    private Toolbar mToolbar;
    private RecyclerView mRecyleMsg;
    private UserMsgAdapter mMsgAdapter;
    private List<bbsBean.Lists> bbsBeanList = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_bbs);
        mRecyleMsg = (RecyclerView) findViewById(R.id.recyle_user_bbs);
    }

    @Override
    protected void initData() {
        initRecyleView();
        getBBSData();
    }

    private void initRecyleView(){
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyleMsg.setLayoutManager(mLinearLayoutManager);
    }
    public void getBBSData() {
        RequestParams params = new RequestParams("http://182.254.145.222/lsj/mdnews/user/getBBSDate.php");
        x.http().post(params, new NewCommonCallBack() {
            @Override
            public void onSuccess(String s) {

                bbsBean mbbsBean = JSON.parseObject(s, bbsBean.class);
//                        DbCookieStore instance = DbCookieStore.INSTANCE;
//                        List cookies = instance.getCookies();
                switch(mbbsBean.getResultCode()){
                    case 400:
                        MyToast.showToast(mContext,"没登录");
                        break;
                    case 1:
                        if(mbbsBean.getLists() != null && mbbsBean.getLists().size() != 0){
                            bbsBeanList.addAll(mbbsBean.getLists());
                            initOrRefresh();
                        }
                        MyLogger.showLogWithLineNum(3,"--------------userBBS:"+s);
                        break;
                    case 0:
                        MyToast.showToast(mContext,"没数据");
                        break;
                }
            }

        });
    }

    private void initOrRefresh(){
        if(mMsgAdapter == null){
            mMsgAdapter = new UserMsgAdapter(mContext, bbsBeanList);
            mRecyleMsg.setAdapter(mMsgAdapter);
        }else{
            mMsgAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_bbs;
    }
}
