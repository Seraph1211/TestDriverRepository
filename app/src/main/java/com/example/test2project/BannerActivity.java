package com.example.test2project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {

    List<Integer> imageUrlList;
    List<String> titleList;
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initBanner();

    }

    public void initBanner(){
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();

        imageUrlList.add(R.drawable.banner_1);
        imageUrlList.add(R.drawable.banner_2);
        imageUrlList.add(R.drawable.banner_3);
        imageUrlList.add(R.drawable.banner_4);

        titleList.add("标题1");
        titleList.add("标题2");
        titleList.add("标题3");
        titleList.add("标题4");

        banner = findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new MyLoader());
        banner.setImages(imageUrlList);
        banner.setBannerTitles(titleList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(2000);  //切换频率
        banner.isAutoPlay(true);  //自动启动
        banner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        banner.start();

    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(BannerActivity.this)
                    .load(path).
                    into(imageView);
        }
    }
}
