package com.thugkd.view;

/**
 * Created by thugkd on 14/03/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.thugkd.wchat.R;


/**
 * @author gao_chun
 *         自定义标题栏
 */
public class TitleBarActivity extends Activity implements OnClickListener {

    //private RelativeLayout mLayoutTitleBar;
    private TextView tvTitle;
    private Button btnBackward;
    private Button btnForward;
   // private FrameLayout mContentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
    }


    private void findView() {
        setContentView(R.layout.layout_titlebar);
        tvTitle = (TextView) findViewById(R.id.text_title);
        //mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        btnBackward = (Button) findViewById(R.id.button_backward);
        btnForward = (Button) findViewById(R.id.button_forward);
    }

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (btnBackward != null) {
            if (show) {
               btnBackward.setText(backwardResid);
                btnBackward.setVisibility(View.VISIBLE);
            } else {
                btnBackward.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(int forwardResId, boolean show) {
        if (btnForward != null) {
            if (show) {
                btnForward.setVisibility(View.VISIBLE);
                btnForward.setText(forwardResId);
            } else {
                btnForward.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {

    }


    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        tvTitle.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }
//
//    //取出FrameLayout并调用父类removeAllViews()方法
//    @Override
//    public void setContentView(int layoutResID) {
//        mContentLayout.removeAllViews();
//        View.inflate(this, layoutResID, mContentLayout);
//        onContentChanged();
//    }
//
//    @Override
//    public void setContentView(View view) {
//        mContentLayout.removeAllViews();
//        mContentLayout.addView(view);
//        onContentChanged();
//    }

//    /* (non-Javadoc)
//     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
//     */
//    @Override
//    public void setContentView(View view, LayoutParams params) {
//        mContentLayout.removeAllViews();
//        mContentLayout.addView(view, params);
//        onContentChanged();
//    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;

            case R.id.button_forward:
                onForward(v);
                break;

            default:
                break;
        }
    }
}