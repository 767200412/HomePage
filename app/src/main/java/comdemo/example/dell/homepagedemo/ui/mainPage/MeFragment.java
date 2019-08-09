package comdemo.example.dell.homepagedemo.ui.mainPage;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comdemo.example.dell.homepagedemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view,view2;//定义view用来设置fragment的layout
    private TabLayout mytab;
    private RecyclerView mRecyclerView;
    private List<String> mEntityList;//公司列表


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_me, container, false);
        mytab = (TabLayout) view.findViewById(R.id.roll_tab);
        mRecyclerView = view.findViewById(R.id.review);
//        initData();
//        initRecyclerView();

        mytab.addTab(mytab.newTab().setText("选项卡一"));
        mytab.addTab(mytab.newTab().setText("选项卡2"));
        mytab.addTab(mytab.newTab().setText("1"));
        mytab.addTab(mytab.newTab().setText("选项卡4"));
        mytab.addTab(mytab.newTab().setText("选项卡5"));
        mytab.addTab(mytab.newTab().setText("选项卡6"));
        mytab.addTab(mytab.newTab().setText("选项卡7"));
        //reflex(mytab);
        mytab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //添加选中Tab的逻辑
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //添加未选中Tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               //再次选中tab的逻辑
            }
        });

        return view;
    }

//    private void initData(){
//        mEntityList = new ArrayList<>();
//        for(int i = 'A'; i <= 'z'; i++){
//            BaseEntity entity = new BaseEntity();
//            entity.setText("" + (char)i);
//            mEntityList.add(entity);
//        }
//    }
//    /**
//     * 初始化RecyclerView
//     */
//    private void initRecyclerView(){
//        // 定义一个线性布局管理器
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
//        // 设置布局管理器
//        mRecyclerView.setLayoutManager(manager);
//        // 设置adapter
//        DemoAdapter adapter = new DemoAdapter(getActivity().getApplicationContext(), mEntityList);
//        mRecyclerView.setAdapter(adapter);
//    }



   /* public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }*/


}
