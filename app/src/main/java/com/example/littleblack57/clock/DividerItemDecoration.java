package com.example.littleblack57.clock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.ItemDecoration;

/**
 * Created by littleblack57 on 2016/8/4.
 */
public class DividerItemDecoration extends ItemDecoration {

    private static final int[] ATTRS = new int[]{

            android.R.attr.listDivider

    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable m_Divider;

    private int m_Orientation;

    public DividerItemDecoration(Context context, int orentation){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        m_Divider = a.getDrawable(0);
        a.recycle();
        setOrientation(orentation);

    }

    public void setOrientation(int orientation) {
        this.m_Orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if(m_Orientation == VERTICAL_LIST){
            deawVertical(c,parent);
        }else {
            drawHorizontal(c,parent);
        }

    }



    public void deawVertical(Canvas c , RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount ; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child
                    .getLayoutParams();

            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + m_Divider.getIntrinsicHeight();
            m_Divider.setBounds(left,top,right,bottom);
            m_Divider.draw(c);
        }
    }


    public void drawHorizontal(Canvas c , RecyclerView parent){

        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child
                    .getLayoutParams();

            final int left = child.getRight() + params.rightMargin;
            final int right = left + m_Divider.getIntrinsicHeight();
            m_Divider.setBounds(left,top,right,bottom);
            m_Divider.draw(c);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(m_Orientation == VERTICAL_LIST){
            outRect.set(0, 0, 0, m_Divider.getIntrinsicHeight());
        }else {
            outRect.set(0, 0,m_Divider.getIntrinsicWidth(), 0);
        }
    }
}
