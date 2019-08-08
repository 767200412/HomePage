package comdemo.example.dell.homepagedemo.Ui.MainPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import comdemo.example.dell.homepagedemo.BuildConfig;
import comdemo.example.dell.homepagedemo.Log.LogUtil;
import comdemo.example.dell.homepagedemo.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private  HomeFragment fragment1;
    private ProductFragment fragment2;
    private MessageFragment fragment3;
    private MeFragment fragment4;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;

                    }

                    return true;
                case R.id.navigation_product:
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;

                    }
                    return true;
                case R.id.navigation_message:
                    if(lastfragment!=2)
                    {
                        switchFragment(lastfragment,2);
                        lastfragment=2;

                    }
                    return true;
                case R.id.navigation_me:
                    if(lastfragment!=3)
                    {
                        switchFragment(lastfragment,3);
                        lastfragment=3;

                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化Looger工具
        LogUtil.init(BuildConfig.LOG_DEBUG);
        setContentView(R.layout.activity_main);

        fragment1 = new HomeFragment();
        fragment2 = new ProductFragment();
        fragment3 = new MessageFragment();
        fragment4 = new MeFragment();
        fragments = new Fragment[]{fragment1,fragment2,fragment3,fragment4};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,fragment1).show(fragment1).commit();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //切换Fragment
    private void switchFragment(int lastfragment,int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.mainview,fragments[index]);


        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }


}
