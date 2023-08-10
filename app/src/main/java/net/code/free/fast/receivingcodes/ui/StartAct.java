package net.code.free.fast.receivingcodes.ui;

import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.AllCountryURL;
import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.ServerBeans;
import static net.code.free.fast.receivingcodes.util.HttpUtils.sendGetRequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.tencent.mmkv.MMKV;

import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.util.HttpUtils;
import net.code.free.fast.receivingcodes.util.MyUtil;

public class StartAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue1));

        initUI();
        initData();

    }

    public void initUI(){

    }

    public void initData(){

        sendGetRequest(AllCountryURL, new HttpUtils.HttpCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyUtil.MyLog(result);
                if (result!=null){
                    MMKV.defaultMMKV().encode(ServerBeans,result);
                }else {

                }

                startActivity(new Intent(StartAct.this,MainAct.class));
                finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}