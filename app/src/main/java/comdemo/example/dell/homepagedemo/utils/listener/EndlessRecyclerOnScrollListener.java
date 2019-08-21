package comdemo.example.dell.homepagedemo.utils.listener;

import android.support.v7.widget.RecyclerView;

//重写recycleview滑动监听
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(!recyclerView.canScrollVertically(1)){
            onLoadMore();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
        //Log.d("dy", String.valueOf(dy));
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}


