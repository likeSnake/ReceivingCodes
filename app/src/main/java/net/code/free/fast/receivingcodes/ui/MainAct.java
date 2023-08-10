package net.code.free.fast.receivingcodes.ui;

import static net.code.free.fast.receivingcodes.constant.MyAppApiConfig.ServerBeans;
import static net.code.free.fast.receivingcodes.util.MyUtil.getDataByCountry;
import static net.code.free.fast.receivingcodes.util.MyUtil.getPhoneList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;
import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.adapter.PhoneAdapter;
import net.code.free.fast.receivingcodes.bean.PhoneBean;
import net.code.free.fast.receivingcodes.util.RadioButtonManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainAct extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private RadioButton radioButton;
    private RadioGroup countryRg;
    private ArrayList<PhoneBean> phoneList;
    private PhoneAdapter mainAdapter;
    private RecyclerView countryRecycler;
    private TextView is_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue1));

        initUI();
        initData();
    }

    public void initUI(){
        countryRg = findViewById(R.id.countryRg);
        countryRecycler = findViewById(R.id.countryRecycler);
        is_empty = findViewById(R.id.is_empty);
    }

    public void initData(){
        if(MMKV.defaultMMKV().decodeString(ServerBeans)!=null){
            String serverList = MMKV.defaultMMKV().decodeString(ServerBeans);
            phoneList = getPhoneList(serverList);
            if(phoneList.isEmpty()){
                is_empty.setVisibility(View.VISIBLE);
            }else {
                is_empty.setVisibility(View.GONE);
                start(false,phoneList);
                initRadioButton(phoneList);
            }



        }
    }

    public void start(Boolean b, ArrayList<PhoneBean> list){
        list.get(0).setChecked(true);
        LinearLayoutManager manager = new LinearLayoutManager(MainAct.this, LinearLayoutManager.VERTICAL, b);
        mainAdapter = new PhoneAdapter(this,list);
        countryRecycler.setLayoutManager(manager);
        countryRecycler.setAdapter(mainAdapter);
    }
    public void initRadioButton(ArrayList<PhoneBean> data){
        radioButton = RadioButtonManager.get().createRadioButton(this, getString(R.string.all));
        radioButton.setOnCheckedChangeListener(this);
        radioButton.setChecked(true);
        countryRg.addView(radioButton);
        Map<String,String> map = new HashMap<>();

        for (PhoneBean datum : data) {
            String countryName = datum.getCountryName();
            if (map.containsKey(countryName)){
                continue;
            }

            map.put(countryName,countryName);

            RadioButton radio = RadioButtonManager.get().createRadioButton(this, countryName);
            radio.setOnCheckedChangeListener(this);
            countryRg.addView(radio);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            if (!phoneList.isEmpty()){
                String s = buttonView.getText().toString();

                for (PhoneBean phoneBean : phoneList) {
                    phoneBean.setChecked(false);
                }

                if (!s.equals(getString(R.string.all))){
                    radioButton.setChecked(false);
                    ArrayList<PhoneBean> dataByCountry = getDataByCountry(s, phoneList);
                    dataByCountry.get(0).setChecked(true);
                    mainAdapter.setList(dataByCountry);
                }else {
                    phoneList.get(0).setChecked(true);
                    mainAdapter.setList(phoneList);
                }

            }
        }
    }
}