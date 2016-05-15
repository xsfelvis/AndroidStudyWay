package xsf.customView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Toast;

import xsf.customView.base.BaseActvity;


public class StatelliteActivity extends BaseActvity {
    private Button btnMenu, btnItem1, btnItem2, btnItem3, btnItem4, btnItem5;
    private boolean isMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statellite);
        initViews();
    }

    private void initViews() {
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(this);
        btnItem1 = (Button) findViewById(R.id.btnItem1);
        btnItem1.setOnClickListener(this);

        btnItem2 = (Button) findViewById(R.id.btnItem2);
        btnItem2.setOnClickListener(this);
        btnItem3 = (Button) findViewById(R.id.btnItem3);
        btnItem3.setOnClickListener(this);
        btnItem4 = (Button) findViewById(R.id.btnItem4);
        btnItem4.setOnClickListener(this);
        btnItem5 = (Button) findViewById(R.id.btnItem5);
        btnItem5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        btnMenu.requestFocus();

        switch (v.getId()) {

            case R.id.btnMenu:
                showItemAnimator();
                break;
            case R.id.btnItem1:
                Toast.makeText(StatelliteActivity.this, "点击了Item1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnItem2:
                Toast.makeText(StatelliteActivity.this, "点击了Item2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnItem3:
                Toast.makeText(StatelliteActivity.this, "点击了Item3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnItem4:
                Toast.makeText(StatelliteActivity.this, "点击了Item4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnItem5:
                Toast.makeText(StatelliteActivity.this, "点击了Item5", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void showItemAnimator() {
        if (!isMenuOpen) {
            //此时menu是关闭的
            isMenuOpen = true;
            btnItemStartAnimator(btnItem1, 0, 5, 300);
            btnItemStartAnimator(btnItem2, 1, 5, 300);
            btnItemStartAnimator(btnItem3, 2, 5, 300);
            btnItemStartAnimator(btnItem4, 3, 5, 300);
            btnItemStartAnimator(btnItem5, 4, 5, 300);

        } else {
            //此时menu是打开的
            isMenuOpen = false;
            btnItemCloseAnimator(btnItem1, 0, 5, 300);
            btnItemCloseAnimator(btnItem2, 1, 5, 300);
            btnItemCloseAnimator(btnItem3, 2, 5, 300);
            btnItemCloseAnimator(btnItem4, 3, 5, 300);
            btnItemCloseAnimator(btnItem5, 4, 5, 300);
        }
    }

    /**
     * 关闭动画
     *
     * @param btnItem
     * @param index
     * @param total
     * @param radius
     */
    private void btnItemCloseAnimator(View btnItem, int index, int total, int radius) {

        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(btnItem, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(btnItem, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(btnItem, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(btnItem, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(btnItem, "alpha", 1f, 0f));
        set.setDuration(500).start();

        if (btnItem.getVisibility() == View.VISIBLE) {
            btnItem.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 开启动画
     *
     * @param btnItem
     * @param index
     * @param total
     * @param radius
     */
    private void btnItemStartAnimator(View btnItem, int index, int total, int radius) {
        if (btnItem.getVisibility() != View.VISIBLE) {
            btnItem.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90) / (total - 1) * index;//Math中根据度数得到弧度值的函数
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //实现平移缩放和透明动画
        set.playTogether(
                ObjectAnimator.ofFloat(btnItem, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(btnItem, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(btnItem, "scaleX", 0, 1),
                ObjectAnimator.ofFloat(btnItem, "scaleY", 0, 1),
                ObjectAnimator.ofFloat(btnItem, "alpha", 0, 1)
        );
        set.setInterpolator(new BounceInterpolator());

        set.setDuration(500).start();


    }
}
