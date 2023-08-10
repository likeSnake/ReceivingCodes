package net.code.free.fast.receivingcodes.ui;

import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.AllCountryURL;
import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.RecordByNumURL;
import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.ServerBeans;
import static net.code.free.fast.receivingcodes.util.HttpUtils.sendGetRequest;
import static net.code.free.fast.receivingcodes.util.MyUtil.copyToClipboard;
import static net.code.free.fast.receivingcodes.util.MyUtil.getFlagId;
import static net.code.free.fast.receivingcodes.util.MyUtil.getSmsList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;
import com.wang.avi.AVLoadingIndicatorView;

import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.adapter.PhoneAdapter;
import net.code.free.fast.receivingcodes.adapter.SmsAdapter;
import net.code.free.fast.receivingcodes.bean.PhoneBean;
import net.code.free.fast.receivingcodes.bean.SmsBean;
import net.code.free.fast.receivingcodes.util.HttpUtils;
import net.code.free.fast.receivingcodes.util.MyUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class SmsAct extends AppCompatActivity implements View.OnClickListener{

    private TextView detailsNum;
    private TextView detailsCountry;
    private TextView detailsRefresh;
    private RecyclerView detailsRecycler;
    private PhoneBean smsInfo;
    private ArrayList<SmsBean> smsList;
    private SmsAdapter smsAdapter;
    private Dialog dialog;
    private ImageView detailsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sms);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue1));
        initUI();
        initLoading();
        initData();
        initListener();

    }

    public void initUI(){
        detailsNum = findViewById(R.id.detailsNum);
        detailsCountry = findViewById(R.id.detailsCountry);
        detailsRefresh = findViewById(R.id.detailsRefresh);
        detailsRecycler = findViewById(R.id.detailsRecycler);
        detailsImg = findViewById(R.id.detailsImg);
    }

    public void initListener(){
        detailsRefresh.setOnClickListener(this);
        detailsNum.setOnClickListener(this);
    }

    public void initData(){
        getPhoneInfo();
        detailsNum.setText(smsInfo.getNumber());
        detailsCountry.setText(smsInfo.getCountryName());
        detailsImg.setImageResource(getFlagId(smsInfo.getCountryName()));
    }

    public void initLoading(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_simple);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AVLoadingIndicatorView id = dialog.findViewById(R.id.avi);
        id.show();


    }
    public void getPhoneInfo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            smsInfo =  getIntent().getSerializableExtra("SmsInfo",PhoneBean.class);
        }else {
            smsInfo = (PhoneBean)getIntent().getSerializableExtra("SmsInfo");
        }

        if (smsInfo==null){
           finish();
        }

        refresh(smsInfo.getNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detailsRefresh:
                refresh(smsInfo.getNumber());
                break;
            case R.id.detailsNum:
                if (copyToClipboard(SmsAct.this,detailsNum.getText().toString())){
                    MyUtil.MyToast(SmsAct.this,"number:"+detailsNum.getText().toString());
                }
                break;
        }
    }

    public void refresh(String num){
        dialog.show();
        String Url = RecordByNumURL+"?num="+num;
        sendGetRequest(Url, new HttpUtils.HttpCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MyUtil.MyLog(result);
                if (result!=null){
                    runOnUiThread(()->{
                        smsList = getSmsList(result);
                        smsList.get(0).setChecked(true);
                        start(false,smsList);
                        dialogDismiss();
                    });


                }else {
                    //空，重新请求
                    refresh(num);
                }

            }

            @Override
            public void onFailure(Exception e) {
                //加载失败，重新再次请求
                refresh(num);
            }
        });
    }
    public void dialogDismiss(){
        if (dialog!=null){
            dialog.dismiss();
        }
    }
    public void start(Boolean b, ArrayList<SmsBean> list){
        list.get(0).setChecked(true);
        LinearLayoutManager manager = new LinearLayoutManager(SmsAct.this, LinearLayoutManager.VERTICAL, b);
        smsAdapter = new SmsAdapter(this,list);
        detailsRecycler.setLayoutManager(manager);
        detailsRecycler.setAdapter(smsAdapter);
    }
}