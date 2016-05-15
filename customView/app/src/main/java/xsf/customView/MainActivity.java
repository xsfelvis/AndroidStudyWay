package xsf.customView;

import android.os.Bundle;
import android.view.View;

import xsf.customView.base.BaseActvity;

public class MainActivity extends BaseActvity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inintViews();
    }

    private void inintViews() {
        findViewById(R.id.btn_qqhealth).setOnClickListener(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
        findViewById(R.id.btn_stateliteMenu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_qqhealth:
                launch(TestActivity1.class);
                break;
            case R.id.btn_stateliteMenu:
                launch(StatelliteActivity.class);
                break;
            case R.id.btn_more:
                launch(TestActivity3.class);
                break;

        }
    }
}
