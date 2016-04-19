package xsf.customView.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;

/**
 * @author xushangfei
 * @time Created at 2016/4/2.
 * @email xsf_uestc_ncl@163.com
 */
public class BaseActvity extends AppCompatActivity implements View.OnClickListener {
    public static final String SER_KEY = "object";

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

    }
}
