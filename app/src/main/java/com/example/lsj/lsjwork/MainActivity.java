package com.example.lsj.lsjwork;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.util.MD5;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private String mString;
    private TextView mTxtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtView = (TextView) findViewById(R.id.textView);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
//                Toast.makeText(MainActivity.this, mString, Toast.LENGTH_SHORT).show();
                Log.i("-----------:",""+mString);

                Document content = Jsoup.parse(mString);
                Elements element = content.getElementsByTag("li");
                for(Element links : element){
                    Log.i("-----------:",links.toString());
                }
                mTxtView.setText(Html.fromHtml(element.toString()));
            }
        }
    };

    Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Toast.makeText(MainActivity.this, ""+msg.obj, Toast.LENGTH_SHORT).show();
                Log.i("-----------:",""+msg.obj);
                Document doc = Jsoup.parse(msg.obj.toString());
                Elements element = doc.getElementsByTag("li");
                for(Element links : element){
                    Log.i("-----------:",links.select("a").attr("title"));
                }
            }
        }
    };

    private void postHttp(){
//        String urlStr = "http://182.254.145.222/lsj/mdnews/user/user_login.php";
        String urlStr = "http://www.douyu.com/wtybill";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
//            String data = "phone=" + URLEncoder.encode("13726226699", "UTF-8")
//                    + "&password=" + URLEncoder.encode(MD5.md5("123456"), "UTF-8");
            // 设置请求的头
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
//            // 设置请求的头
//            httpURLConnection.setRequestProperty("Content-Length",
//                    String.valueOf(data.getBytes().length));
            // 设置请求的头
            httpURLConnection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
//            //获取输出流
//            OutputStream os = httpURLConnection.getOutputStream();
//            os.write(data.getBytes());
//            os.flush();
            if(httpURLConnection.getResponseCode() == 200){
                Log.i("1234----------123","成功");
                // 获取响应的输入流对象
                InputStream is = httpURLConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();
                // 返回字符串
                final String result = new String(baos.toByteArray());
                mString = result;
                handler.sendEmptyMessage(1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onbutton(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                postHttp();
//                try {
//                    Document doc = Jsoup.connect("http://www.douyu.com/67373").get();
//                    Document content = Jsoup.parse(doc.toString());
//                    Elements divs = content.select("#live-list-contentbox");
//                    Message message = new Message();
//                    message.what = 1;
//                    message.obj = content.toString();
//                    handler1.sendMessage(message);
////                    Toast.makeText(MainActivity.this, ""+doc.body(), Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();
//

    }
}
