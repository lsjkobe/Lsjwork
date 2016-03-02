package com.example.lsj.httplibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lsj.httplibrary.R;
import com.example.lsj.httplibrary.callback.ApiResponse;
import com.example.lsj.httplibrary.callback.ApiResponseNews;
import com.example.lsj.httplibrary.callback.BaseCallBack;
import com.example.lsj.httplibrary.callback.FailMessg;
import com.example.lsj.httplibrary.http.HttpUtil;
import com.example.lsj.httplibrary.utils.MyLogger;
import com.example.lsj.httplibrary.utils.MyToast;
import com.example.lsj.httplibrary.utils.PxDipUnti;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Le on 2016/1/9.
 */
public class BaseHelper {
    public static final int HTTPGET = 0x1;
    public static final int HTTPPOST = 0x2;
    protected View lyProgress = null; // 加载栏
    private ViewGroup view; // 根目录
    private View rlTopView; // 头部
    private ImageView imgLeft = null;
    private TextView tvTitle = null;
    private ImageView imgRight = null;
    private LinearLayout lyLeftContent = null;
    private LinearLayout lyRightContent = null;
    private RelativeLayout layout;
    private Context mContext;
    private boolean first = true; // 缓存处理，第一次记载缓存
    private OnErrorListener onErrorListener;

    public BaseHelper(Context context) {
        this.mContext = context;
    }

    public RelativeLayout initBaseView(int layoutId) {

        // 外层
        layout = new RelativeLayout(mContext);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));
        layout.setFitsSystemWindows(true);
        layout.setClipToPadding(true);
        // 用户自定义视图
        view = (ViewGroup) LayoutInflater.from(mContext).inflate(layoutId, null);
        // 头部
        rlTopView = LayoutInflater.from(mContext).inflate(R.layout.top_layout, null);
        lyLeftContent = (LinearLayout) rlTopView.findViewById(R.id.ly_top_layout_left);
        lyRightContent = (LinearLayout) rlTopView.findViewById(R.id.ly_top_layout_right);
        imgLeft = (ImageView) rlTopView.findViewById(R.id.img_top_layout_left);
        tvTitle = (TextView) rlTopView.findViewById(R.id.tv_top_layout_title);
        imgRight = (ImageView) rlTopView.findViewById(R.id.img_top_layout_right);
        rlTopView.setBackgroundResource(R.drawable.background_title);

        LinearLayout.LayoutParams topParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                PxDipUnti.dip2px(mContext, 42));

        // 加载栏
        lyProgress = LayoutInflater.from(mContext).inflate(
                R.layout.progress_layout, null);
        RelativeLayout.LayoutParams progressParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        progressParam.addRule(RelativeLayout.CENTER_IN_PARENT);

        RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        viewParam.addRule(RelativeLayout.BELOW, R.id.rl_top_layout);

        layout.addView(rlTopView, topParam); // 加入头部
        layout.addView(view, viewParam); // 内容
        layout.addView(lyProgress, progressParam); // 加入加载栏
        return layout;
    }

    public <T> void getData(RequestParams params,
                            int getOrPost, final Class<T> cls, final BaseCallBack callBack,
                            final LoadConfig loadConfig) {
        params.setCacheMaxAge(1000*60);
        params.setConnectTimeout(1000*6);
        Callback.CacheCallback<String> mCallBack = new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String s) {
                onSuccessByString(cls, callBack, s, loadConfig);
                return false;
            }

            @Override
            public void onSuccess(String s) {
                onSuccessByString(cls, callBack, s, loadConfig);
            }

            @Override
            public void onError(Throwable error, boolean b) {
                //是否提示错误
                if (loadConfig.isShowError) {
                    if (error instanceof HttpException) {
                        // 网络中断错误详情显示
                        MyToast.showLongToast(mContext, FailMessg.FIALUNKNOnHOSTMSG);
                    } else if (error instanceof SocketTimeoutException) {
                        // 请求超时详情
                        MyToast.showLongToast(mContext, FailMessg.FIALTIMEOUTMSG);
                    } else if (error instanceof SocketException) {
                        //连接错误
                        MyToast.showLongToast(mContext, FailMessg.FIALUNKNOnHOSTMSG);
                    } else {
                        //获取数据失败
                        MyToast.showLongToast(mContext, FailMessg.FIALGETDATAMSG);
                    }
                }

                callBack.onFail(mContext, null);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                callBack.onFinish();
            }
        };
        switch (getOrPost) {
            case HTTPGET:
                x.http().get(params, mCallBack);
                break;
            case HTTPPOST:

                x.http().post(params, mCallBack);
                break;
            default:
                break;
        }
    }

    public <T> void onSuccessByString(final Class<T> cls, final BaseCallBack callBack, String content, LoadConfig loadConfig) {
        callBack.onResult(content);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T t = null;
        try {
            t = objectMapper.readValue(content, cls);
        }catch (JsonParseException e) {
            if (loadConfig.isShowError) {
                MyToast.showLongToast(mContext, FailMessg.FIALPARSINGMSG);
            }
            callBack.onFail(mContext, null);
            e.printStackTrace();
        } catch (JsonMappingException e) {
            if (loadConfig.isShowError) {
                MyToast.showLongToast(mContext, FailMessg.FIALPARSINGMSG);
            }
            callBack.onFail(mContext, null);
            e.printStackTrace();
        } catch (IOException e) {
            if (loadConfig.isShowError) {
                MyToast.showLongToast(mContext, FailMessg.FIALPARSINGMSG);
            }
            callBack.onFail(mContext, null);
            e.printStackTrace();
        }
        /*if (t instanceof ApiResponse) {
            if (((ApiResponse) t).getResult() != ApiResponse.RESPONE_ERROR) {//404，服务器出错处理
                if (!callBack.onSuccesBefore(t, mContext)) {//可以在成功回调之前做处理，可以切断事件
                    callBack.onSucces(t);
                }
            } else {
                MyToast.showToast(mContext, ((ApiResponse) t).getContent());
            }

        }else if (t instanceof ApiResponseNews) {

            if (!callBack.onSuccesBefore(t, mContext)) {//可以在成功回调之前做处理，可以切断事件
                callBack.onSucces(t);
            }
        }else{
            callBack.onSucces(t);

        }*/
        if(t != null){
            callBack.onSucces(t);
        }

        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示头部
     *
     * @param isShow
     */
    public void showTopView(boolean isShow) {
//		if (isShow) {
//			rlTopView.setVisibility(View.VISIBLE);
//		} else {
//			rlTopView.setVisibility(View.GONE);
//		}
        if (!isShow) {
            layout.removeView(rlTopView);
            rlTopView = null;
        }
    }

    public TextView getTitle() {
        return tvTitle;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public ImageView getLeftImage() {
        return imgLeft;
    }

    public ImageView getRightImage() {
        return imgRight;
    }

    public LinearLayout getLeftContent() {
        return lyLeftContent;
    }

    public LinearLayout getRightContent() {
        return lyRightContent;
    }

    public View getTopView() {
        return rlTopView;
    }

    public View getBaseView() {
        return layout;
    }

    public void showProgressbar(LoadConfig loadConfig) {
        if (lyProgress != null && loadConfig.isShowDialog()) {
            lyProgress.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressbar() {
        if (lyProgress != null && lyProgress.getVisibility() == View.VISIBLE) {
            lyProgress.setVisibility(View.GONE);
        }
    }

    public interface OnErrorListener {

        void onError(View v);
    }

    public void setOnOnErrorListener(OnErrorListener onErrorListener) {
        // TODO Auto-generated method stub
        this.onErrorListener = onErrorListener;
    }

    public static class LoadConfig {
        private boolean isShowDialog = true;
        private boolean isCache = false;
        private boolean isShowError = true;

        public LoadConfig() {

        }

        public LoadConfig(boolean isShowDialog, boolean isCache) {
            this.isShowDialog = isShowDialog;
            this.isCache = isCache;
        }

        public LoadConfig(boolean isShowDialog, boolean isCache, boolean isErrorShow) {
            this.isShowDialog = isShowDialog;
            this.isCache = isCache;
            this.isShowError = isErrorShow;
        }

        public boolean isShowError() {
            return isShowError;
        }

        public void setShowError(boolean isShowError) {
            this.isShowError = isShowError;
        }

        public boolean isShowDialog() {
            return isShowDialog;
        }

        public void setShowDialog(boolean isShowDialog) {
            this.isShowDialog = isShowDialog;
        }

        public boolean isCache() {
            return isCache;
        }

        public void setCache(boolean isCache) {
            this.isCache = isCache;
        }
    }
}
