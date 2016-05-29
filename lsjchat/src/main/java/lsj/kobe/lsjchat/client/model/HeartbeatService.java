package lsj.kobe.lsjchat.client.model;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import lsj.kobe.lsjchat.R;
import lsj.kobe.lsjchat.client.bean.MessageBean;
import lsj.kobe.lsjchat.client.view.MainActivity;
import lsj.kobe.lsjchat.comment.LsjHelp;

/**
 * Created by lsj on 2016/5/29.
 */
public class HeartbeatService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initBroadcast();
        return super.onStartCommand(intent, flags, startId);
    }
    private void initBroadcast() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(LsjHelp.ACTION_MSG);
        chatBroadcastReceiver broadcastReceiver = new chatBroadcastReceiver();
        registerReceiver(broadcastReceiver,mFilter);
    }
    public class chatBroadcastReceiver extends BroadcastReceiver{
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            MessageBean msg = (MessageBean) intent.getSerializableExtra("msg");
            Intent notificationIntent = new Intent(HeartbeatService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(HeartbeatService.this,0,notificationIntent,0);
            Notification notification = new Notification.Builder(HeartbeatService.this)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(msg.getSender())
                    .setContentText(msg.getContent())
                    .setTicker("通知")
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            notification.defaults = Notification.DEFAULT_SOUND;
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.notify(Integer.parseInt(msg.getSender()),notification);
//            startForeground(Integer.parseInt(msg.getSender()),notification);//前台service
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
