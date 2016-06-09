package xsf.customView.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xsf.customView.util.AppManager;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public abstract class BaseActvity extends AppCompatActivity implements View.OnClickListener {
    /* public static final String SER_KEY = "object";

     public void launch(Class<?> clazz) {
         Intent intent = new Intent(this, clazz);
         startActivity(intent);
     }

     public void launch(Class<?> clazz, Object object) {
         Intent intent = new Intent(this, clazz);
         intent.putExtra(SER_KEY, (Serializable) object);
         startActivity(intent);
     }


     @Override
     public void onClick(View v) {

     }*/
    protected Context mContext;
    protected String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(setLayoutResourceId());
        init();
        initView();
        initData();
        //管理activity
        AppManager.getAppManager().addActivity(this);
        //  Log.d("BaseActivity", "当前Actvity 栈中有：" + AppManager.getAppManager().getActivityCount() + "个Actvity");

    }

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int setLayoutResourceId();

    /**
     * 初始化View之前做的事
     */
    protected void init() {
    }


    /**
     * 初始化空控件
     */
    protected abstract void initView();

    /**
     * 处理数据
     */
    protected void initData() {
    }


    /**
     * 分装findviewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T IfindViewById(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 启动不带参数的Aactvity
     *
     * @param clazz
     */
    protected void launchActvity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 启动带参数的Activity
     *
     * @param clazz
     * @param extras
     */
    protected void launchActvityWithBundle(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    /**
     * 完全退出应用
     */
    protected void closeApp() {
        AppManager.getAppManager().AppExit(mContext);
    }

    @Override
    public void onClick(View v) {

    }


}
