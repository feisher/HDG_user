package com.yusong.yslib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yusong.yslib.R;


import java.io.ByteArrayOutputStream;
import java.io.File;


public class GlideImgManager {

    private static int noImg = R.drawable.iv_no_image;
//    private static int noImg = R.drawable.loading_large;

    //    public static final  float THUMBNAIL_LEVEL =0.2f;
    public GlideImgManager() {
    }

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 Api
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 Api
        Glide.with(context).load(url).placeholder(noImg).error(noImg).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv);
    }
    public static void loadVideoImage(Context context, String url, ImageView iv) {
        //原生 Api
        Glide.with(context).load(url).placeholder(noImg).error(noImg).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv);
    }
    public static void loadImageFitCenter(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv);
    }
    public static void loadBitmapFitCenter(Context context, Bitmap mBitmap, ImageView iv) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).fitCenter().error(noImg).diskCacheStrategy(DiskCacheStrategy.RESULT).into(iv);
}
    public static void loadImageCenterCrop(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).centerCrop().placeholder(noImg).diskCacheStrategy(DiskCacheStrategy.RESULT).error(noImg).into(iv);
    }

    public static void loadBitmap(Context context, final Bitmap mBitmap, final ImageView imageView) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(context)
                .load(bytes)
                .error(noImg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);


    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .error(noImg)
                .into(imageView);
    }

    public static void loadImageCenterCrop(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .error(noImg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(imageView);


    }
    public static void loadImageFitCenter(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .error(noImg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .fitCenter()
                .into(imageView);


    }

    public static void loadImageCenterCrop(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .error(noImg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(imageView);
    }
    public static void loadImageFitCenter(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .error(noImg)
                .fitCenter()
                .into(imageView);
    }
    /**
     * 显示gif图
     * @param context
     * @param url
     * @param iv
     */
    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(noImg).error(R.drawable.iv_no_image).into(iv);
    }

    /**
     * 显示圆形图片
     * @param context
     * @param iv
     */
    public static void loadCircleImage(final Context context, final int resourceId, final ImageView iv) {

        Glide.with(context).load(resourceId).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });

    }

    /**
     * 显示圆形图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadCircleImage(final Context context, String url, final ImageView iv) {

        Glide.with(context).load(url).asBitmap().centerCrop()
                .placeholder(R.drawable.iv_no_image)
                .error(R.drawable.iv_no_image)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });

    }



//    @Override
//    public void displayImage(Context context, Object path, View imageView) {
//        Glide.with(context).load(path).placeholder(R.mipmap.noImg).
//                error(R.mipmap.noImg).diskCacheStrategy(DiskCacheStrategy.RESULT).into((ImageView) imageView);
//    }

//    public ImageView createImageView(Context context) {
//        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        ImageView imageView=new ImageView(context);
//        return imageView;
//    }


}  