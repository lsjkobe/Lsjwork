package myhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import myhttp.parser.Parser;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:02
 */
public class NewCallback<T> implements com.squareup.okhttp.Callback {
    private Parser<T> mParser;
    private static final int CALLBACK_SUCCESSFUL=0x01;
    private static final int CALLBACK_FAILED=0x02;
    static class UIHandler<T> extends Handler{
        private WeakReference mWeakReference;
        public UIHandler(NewCallback newCallback){
            super(Looper.getMainLooper());
            mWeakReference=new WeakReference(newCallback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CALLBACK_SUCCESSFUL: {
                    T t = (T) msg.obj;
                    NewCallback newCallback = (NewCallback) mWeakReference.get();
                    if (newCallback != null) {
                        newCallback.onResponse(t);
                    }
                    break;
                }
                case CALLBACK_FAILED: {
                    IOException e = (IOException) msg.obj;
                    NewCallback newCallback = (NewCallback) mWeakReference.get();
                    if (newCallback != null) {
                        newCallback.onFailure(e);
                    }
                    break;
                }
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    private Handler mHandler=new UIHandler(this);

    public NewCallback(Parser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }


    @Override
    public void onFailure(Request request, IOException e) {
        Message message=Message.obtain();
        message.what=CALLBACK_FAILED;
        message.obj=e;
        mHandler.sendMessage(message);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            T parseResult = mParser.parse(response);
            Message message=Message.obtain();
            message.what=CALLBACK_SUCCESSFUL;
            message.obj=parseResult;
            mHandler.sendMessage(message);
        } else {
            Message message=Message.obtain();
            message.what=CALLBACK_FAILED;
            mHandler.sendMessage(message);
        }
    }

    public void onResponse(T t){

    }
    public  void onFailure(IOException e){

    }
}
