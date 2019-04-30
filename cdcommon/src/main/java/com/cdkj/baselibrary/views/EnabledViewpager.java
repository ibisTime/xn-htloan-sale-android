package com.cdkj.baselibrary.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**重写Viewpager禁止左右滑动

 */
public class EnabledViewpager extends ViewPager {

    private boolean isPagingEnabled = false;

    public EnabledViewpager(Context context) {
        super(context);
    }

    public EnabledViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
    @Override
    public int getCurrentItem() {
        return PagerAdapter.POSITION_NONE;
    }

    //预加载所有的界面  源码最小为1  小于1的会默认变成1
//    @Override
//    public void setOffscreenPageLimit(int limit) {
//        super.setOffscreenPageLimit(100);
//
//    }

//    @Override
//    public void setCurrentItem(int item) {
//        //去除页面切换时的滑动翻页效果
//        super.setCurrentItem(item, false);
//    }

}
