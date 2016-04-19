package xsf.adapterstudy;

import android.os.Bundle;
import android.view.View;

import xsf.adapterstudy.base.BaseActvity;

public class MainActivity extends BaseActvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintViews();
    }

    private void inintViews() {
        findViewById(R.id.btn_qqhealth).setOnClickListener(this);
        findViewById(R.id.btn_multitype).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_qqhealth:
                launch(TestActivity1.class);
                break;
            case R.id.btn_multitype:
                launch(TestActivity2.class);
                break;

        }
    }
}
