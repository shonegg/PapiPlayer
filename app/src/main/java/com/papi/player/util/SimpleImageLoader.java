package com.papi.player.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * author   Shone
 * date     11/06/16.
 * github   https://github.com/shonegg
 */
public class SimpleImageLoader {

    private static final long discCacheLimitTime = 3600 * 24 * 15L;

    public static void checkImageLoaderConfiguration(Context context) {
        if (!checkImageLoader()) {
            // This configuration tuning is custom. You can tune every option,
            // you may tune some of them,
            // or you can create default configuration by
            // ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY)
                    .threadPoolSize(3) // default
                    .denyCacheImageMultipleSizesInMemory()
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                    .discCache(new LimitedAgeDiskCache(StorageUtils
                            .getCacheDirectory(context), null,
                            new Md5FileNameGenerator(),
                            discCacheLimitTime))
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);
        }
    }

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static boolean checkImageLoader() {
        return imageLoader.isInited();
    }

    public static void display(String uri, ImageAware imageAware,
                               int default_pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(default_pic)
                .showImageForEmptyUri(default_pic).showImageOnFail(default_pic)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        imageLoader.displayImage(uri, imageAware, options);
    }

    public static void display(String uri, ImageView view, int default_pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(default_pic)
                .showImageForEmptyUri(default_pic).showImageOnFail(default_pic)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        imageLoader.displayImage(uri, view, options);
    }

    public static void display(String uri, ImageView view){
        if (TextUtils.isEmpty(uri) || view == null){
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        imageLoader.displayImage(uri, view, options);
    }

    public interface ImageLoaderListener{
        void onLoadingComplete(String url);
    }

    public static Bitmap loadImageSync(String uri) {
        return imageLoader.loadImageSync(uri, null, null);
    }

    public static void display(String uri, ImageView view,final ImageLoaderListener listener){
        if (TextUtils.isEmpty(uri) || view == null){
            return;
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        imageLoader.displayImage(uri, view, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                 if (listener != null){
                     listener.onLoadingComplete(imageUri);
                 }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    public static void displayListener(String uri, ImageView imageAware,
                                       int default_pic, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(default_pic)
                .showImageForEmptyUri(default_pic).showImageOnFail(default_pic)
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer()).build();
        imageLoader.displayImage(uri, imageAware, options, listener);
    }

    public static void clear() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiscCache();
    }

    public static void resume() {
        imageLoader.resume();
    }

    /**
     * 暂停加载
     */
    public static void pause() {
        imageLoader.pause();
    }

    /**
     * 停止加载
     */
    public static void stop() {
        imageLoader.stop();
    }


    public static void destroy() {
        imageLoader.destroy();
    }


}
