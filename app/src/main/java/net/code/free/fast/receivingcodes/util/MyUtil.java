package net.code.free.fast.receivingcodes.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.adapter.PhoneAdapter;
import net.code.free.fast.receivingcodes.bean.PhoneBean;
import net.code.free.fast.receivingcodes.bean.SmsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {
    public static void MyLog(Object s){
        System.out.println("----*--------*"+s);

    }
    public static void MyToast(Context context,String s){
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast.setText(s);
        toast.show();
    }

    public static ArrayList<PhoneBean> getPhoneList(String phoneJson){

        ArrayList<PhoneBean> phoneBeans = new ArrayList<>();
        try {

            ArrayList<PhoneBean> phoneBean = new Gson().fromJson(phoneJson,new TypeToken<ArrayList<PhoneBean>>() {}.getType());

            if (phoneBean!=null){
                return phoneBean;
            }else {
                return phoneBeans;
            }

        }catch (Exception e){
            Log.e("getPhoneList",e.toString());
            return phoneBeans;
        }

    }

    public static HashMap<String, String> getCountryList(Context context) {
        String[] countryList = context.getResources().getStringArray(R.array.country_code_list_en);
        HashMap<String, String> hashMap = new HashMap<>();
        for (String temp : countryList) {
            String[] country = temp.split("\\*");
            String countryName = country[0];
            String countryNumber = country[1];
            hashMap.put(countryNumber, countryName);
            System.out.println(countryName + "-" + countryNumber);
        }
        return hashMap;
    }

    public static ArrayList<PhoneBean> getDataByCountry(String country, ArrayList<PhoneBean> originData){
        ArrayList<PhoneBean> phoneBeans = new ArrayList<>();
        for (PhoneBean originDatum : originData) {
            if (originDatum.getCountryName().equals(country)){
                phoneBeans.add(originDatum);
            }
        }
        return phoneBeans;
    }

    public static String getVerCode(String content) {
        Pattern pattern = Pattern.compile("(?<![0-9])([0-9]{4,6})(?![0-9])");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    public static boolean copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("label", text);
            clipboard.setPrimaryClip(clip);

            // 检查剪贴板中的内容是否与复制的文本匹配
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String copiedText = item.getText().toString();
            return text.equals(copiedText);
        }
        return false;
    }

    public static ArrayList<SmsBean> getSmsList(String smsJson){

        ArrayList<SmsBean> smsBeans = new ArrayList<>();
        try {

            ArrayList<SmsBean> smsBean = new Gson().fromJson(smsJson,new TypeToken<ArrayList<SmsBean>>() {}.getType());

            if (smsBean!=null){
                return smsBean;
            }else {
                MyLog("smsBean为空");
                return smsBeans;
            }

        }catch (Exception e){
            Log.e("getPhoneList",e.toString());
            return smsBeans;
        }

    }

    public static int getFlagId(String countryName){
        switch (countryName) {
            case "United States" : {
                return R.drawable.us;

            }

            case "United Kingdom" : {
                return R.drawable.au;

            }

            case "Germany" : {
                return (R.drawable.de);

            }

            case "France" : {
                return(R.drawable.fr);

            }

            case "Japan" : {
                return(R.drawable.jp);

            }

            case "Australia" : {
                return(R.drawable.au);

            }

            case "Singapore" : {
                return(R.drawable.sg);
            }

            default:
                return(R.drawable.us);

        }

    }
}
