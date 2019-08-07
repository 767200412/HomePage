package comdemo.example.dell.homepagedemo.listener;

import android.support.v7.widget.RecyclerView;

//重写recycleview滑动监听
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
//        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        // 当不滑动时
//        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
////            boolean isToBottom = isSlideToBottom(recyclerView);
////            Log.d("isToBottom", String.valueOf(isToBottom));
//
//            //获取最后一个完全显示的itemPosition
//            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
//            int itemCount = manager.getItemCount();
//            Log.d("lastItemPosition", String.valueOf(lastItemPosition));
//            Log.d("itemCount", String.valueOf(itemCount));
//
//            // 判断是否滑动到了最后一个item，并且是向上滑动
//            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
//                Log.d("","LoadMore");
//                //加载更多
//                onLoadMore();
//            }
////            // 判断是否滑动到了最后一个item，并且是向上滑动
////            if(isToBottom ){
////                onLoadMore();
////            }
//        }



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


