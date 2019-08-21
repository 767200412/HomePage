package comdemo.example.dell.homepagedemo.ui.showPicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import comdemo.example.dell.homepagedemo.R;

public class ShowImageActivity extends AppCompatActivity  {
    private ImageView show;
    private TextView number;
    private ViewPager viewPager;
    private MyPagerAdapter mAdapter;
    private ArrayList<String> urls = new ArrayList<>();
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        number = (TextView)findViewById(R.id.number);
        viewPager = (ViewPager)findViewById(R.id.show);
        Intent intent = getIntent();
        urls =intent.getStringArrayListExtra("url");
        position = intent.getStringExtra("position");
        Log.e("urls", String.valueOf(urls));
        mAdapter = new MyPagerAdapter(urls);
        viewPager.setAdapter(mAdapter);
        int index = urls.indexOf(position);
        viewPager.setCurrentItem(index);
        String s = viewPager.getCurrentItem()+1 + "/" +urls.size();
        number.setText(s);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                String s = viewPager.getCurrentItem()+1 + "/" +urls.size();
                number.setText(s);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }



    public class MyPagerAdapter extends PagerAdapter{

        private ArrayList<String> urlLists;

        public MyPagerAdapter() {
        }

        public MyPagerAdapter(ArrayList<String> urlLists) {
            super();
            this.urlLists = urlLists;
        }

        @Override
        public int getCount() {
            return urlLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            Glide.with(container.getContext()).load(urlLists.get(position)).into(imageView);
            container.addView(imageView); // 添加到ViewPager容器
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 注意这里不使用finish
                    ActivityCompat.finishAfterTransition(ShowImageActivity.this);
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

    }
}
