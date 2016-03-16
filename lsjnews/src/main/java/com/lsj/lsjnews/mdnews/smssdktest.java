package com.lsj.lsjnews.mdnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lsj.lsjnews.R;

public class smssdktest extends Activity implements View.OnClickListener{

	private Button sensmsButton;
	private EditText phonEditText;
	// 填写从短信SDK应用后台注册得到的APPKEY

	private static String APPKEY = "a5c767e5e284";
	// "7b985099eb84";//463db7238681 27fe7909f8e8
	// "a68ce1a3f824"
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "8f82201d0e6b53b06560d60cec8e2af2";
	// "9ebcaebd4e81ab6fca346720c4d69072";//
	public String phString; // 3684fd4f0e16d68f69645af1c7f8f5bd

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smssdktest);
		sensmsButton = (Button) findViewById(R.id.button1);
		phonEditText = (EditText) findViewById(R.id.editText1);
		sensmsButton.setOnClickListener(this);

		// System.loadLibrary("OSbase");
	//	SMSSDK.initSDK(this, "d650be8a70f6", "b48c7dbca02a75b2526b889f8fcf90ae");
		SMSSDK.initSDK(this, "f6a97e467f20", "7c75beadd45786c06c29df2f7d6f85f6");
		// this.getPackageName()
		// dayin();
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);
	}

	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:// 获取验证码
			if (!TextUtils.isEmpty(phonEditText.getText().toString())) {
				SMSSDK.getVerificationCode("86", phonEditText.getText()
						.toString());
				phString = phonEditText.getText().toString();
				/*
				 * SMSSDK.getVoiceVerifyCode("86", phonEditText.getText()
				 * .toString()); phString = phonEditText.getText().toString();
				 */
			}

			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event=" + event);
			Log.e("result", "result=" + result);

			if (result == SMSSDK.RESULT_COMPLETE) {
				// 短信注册成功后，返回MainActivity,然后提示新好友
				if (event == 3) {
					Log.e("submit", "this is submit");// 提交验证码成功
					Toast.makeText(getApplicationContext(), "提交验证码成功",
							Toast.LENGTH_SHORT).show();

					HashMap map = (HashMap) data;
					Toast.makeText(getApplicationContext(),
							"验证成功" + map.toString(), Toast.LENGTH_SHORT).show();

				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					if (result == SMSSDK.RESULT_COMPLETE) {
						boolean smart = (Boolean) data;
						if (smart) {
							// 通过智能验证
							Toast.makeText(getApplicationContext(), "走了智能验证",
									Toast.LENGTH_SHORT).show();
						} else {
							// 依然走短信验证
							Toast.makeText(getApplicationContext(), "走了短信验证",
									Toast.LENGTH_SHORT).show();
						}
					}

					Toast.makeText(getApplicationContext(), "验证码已经发送",
							Toast.LENGTH_SHORT).show();
				} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {// 返回支持发送验证码的国家列表
					Toast.makeText(getApplicationContext(), "获取国家列表成功",
							Toast.LENGTH_SHORT).show();
					ArrayList<HashMap<String, Object>> arry = (ArrayList) data;
					// System.out.println(first.toString());
					for (int i = 0; i < arry.size(); i++) {
						HashMap<String, Object> hashMap = arry.get(i);
						Iterator iter = hashMap.entrySet().iterator();
						while (iter.hasNext()) {

							Map.Entry entry = (Map.Entry) iter.next();
							Object key = entry.getKey();
							Object val = entry.getValue();
							if (key.toString().equalsIgnoreCase("zone")) {
								System.out.println(val.toString() + "______"
										+ key.toString());
							}

						}

					}

				}
			} else {

				((Throwable) data).printStackTrace();
				 
				Toast.makeText(smssdktest.this, "验证码错误", Toast.LENGTH_SHORT)
						.show();
			 

			}

		}

	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

}
