//package com.yusong.yslib.utils;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.RemoteViews;
//import android.widget.TextView;
//
//
//import com.yusong.yslib.App;
//import com.yusong.yslib.R;
//
//import java.util.TimerTask;
//import java.util.concurrent.TimeUnit;
//
//import static android.content.Context.NOTIFICATION_SERVICE;
//
//
///**
// * create by feisher on 2017/6/4
// * Email：458079442@qq.com
// */
//public class UpdateProgressDialog {
//    public static ProgressDialog mProgress;
//    public static TextView downloadSize;
//    public static TextView tvProgress;
//    public static TextView netSpeed;
//    public static ProgressBar pbProgress;
//    public static NotificationManager manager;
//    public static Notification notif;
//    public  Context mContext;
//    public  static UpdateProgressDialog mUpdateProgressDialog;
//    public static LayoutInflater factory;
//    public static Dialog pb;
//    public static AlertDialog.Builder mDownloadDialogBuilder;
//    public static AlertDialog mDownloadDialog;
//    public static int mPorgress;
//    public static long mDownSize;
//    public static long mDownSize2;
//    private static TimerTask task;
//    private static boolean isLoop = true;
//
//    public UpdateProgressDialog(Context contxt) {
//        this.mContext = contxt;
//    }
//    public static UpdateProgressDialog getInstance(Context contxt){
//        if (mUpdateProgressDialog ==null) {
//            mUpdateProgressDialog = new UpdateProgressDialog(contxt);
//        }
//        return mUpdateProgressDialog;
//    }
//
//    public static void show(final Context mContext){
//
//        //提示框
//        factory = LayoutInflater.from(mContext);
//        //这里必须是final的
//        final View mDownloadDialogView = factory.inflate(R.layout.dialog_downloading, null);
//        downloadSize = (TextView) mDownloadDialogView.findViewById(R.id.downloadSize);
//        tvProgress = (TextView) mDownloadDialogView.findViewById(R.id.tvProgress);
//        netSpeed = (TextView) mDownloadDialogView.findViewById(R.id.netSpeed);
//        pbProgress = (ProgressBar) mDownloadDialogView.findViewById(R.id.pbProgress);
//        pbProgress.setMax(100);
////        StyledDialog.buildCustom(mDownloadDialogView, Gravity.CENTER).show();
////        netSpeed.setVisibility(View.GONE);
//        mDownloadDialogBuilder = new AlertDialog.Builder(mContext);
//        mDownloadDialogBuilder.setTitle("下载中").setView(mDownloadDialogView);
//        mDownloadDialogBuilder.setPositiveButton("后台更新", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                initNotify(mContext);
//                dialogInterface.dismiss();
//            }
//        });
//        mDownloadDialog = mDownloadDialogBuilder.create();
//        mDownloadDialog.setCanceledOnTouchOutside(false);
//        mDownloadDialog.show();
////        pb = StyledDialog.buildProgress("下载中", false).show();
//        //execute task
//        task = new TimerTask() {
//            @Override
//            public void run() {
//                //execute task
//                mDownSize2 = mDownSize;
//                if (false == isLoop) {
//                    task.cancel();
//                }
//            }
//        };
////        new Timer().schedule(task,0,1000);
//        if (!App.Companion.getPool().isShutdown()) {
//            App.Companion.getPool().scheduleWithFixedDelay(task, 0 , 1000, TimeUnit.MILLISECONDS);
//        }
//    }
//    private static void initNotify(Context mContext) {
//        if (manager ==null&&notif == null) {
//            //点击通知栏后打开的activity
////        Intent intent = new Intent(mContext, SplashActivity.class);
////        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
//            manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
//            notif = new Notification();
//            notif.icon = R.mipmap.ic_launcher;
//            notif.tickerText = "正在下载";
//            //通知栏显示所用到的布局文件
//            notif.contentView = new RemoteViews( mContext.getPackageName(), R.layout.notification_apk_updata);
////                notif.contentIntent = pIntent;
////            manager.notify(0, notif);
//        }
//    }
//
//    public static void setProgress(final int progress, String s,long downSize){
//
//        if (pbProgress.getProgress()<progress) {
//            tvProgress.setText(""+progress + "%");
//            pbProgress.setProgress(progress);
//            downloadSize.setText(s);
//            long speed = downSize - mDownSize2;
//            if (speed>1000*1000) {
//                netSpeed.setText(speed/(1000*1000)+"m/s");
//            }else if (speed>1000) {
//                netSpeed.setText(speed/(1000)+"k/s");
//            }else {
//                netSpeed.setText(speed+"b/s");
//            }
//            mDownSize = downSize;
//        }
//
////        StyledDialog.updateProgress(pb,progress,100,format,false);
//        if (progress-mPorgress>=2) {
//            if (notif != null && manager != null) {
//                notif.contentView.setTextViewText(R.id.content_view_text1, progress + "%");
//                notif.contentView.setProgressBar(R.id.content_view_progress, 100, progress, false);
//                manager.notify(0, notif);
//            }
//            mPorgress = progress;
//        }
//
//        if (progress==98) {
//            dismiss();
//        }
//
//    }
//
//
//    public static void dismiss(){
////        StyledDialog.dismiss();
//        if (mDownloadDialog!=null&&mDownloadDialog.isShowing()) {
//            mDownloadDialog.dismiss();
//        }
//        if (manager != null) {
//            manager.cancelAll();
//        }
//        if (task != null) {
//           isLoop = false;
//        }
//    }
//
//}
