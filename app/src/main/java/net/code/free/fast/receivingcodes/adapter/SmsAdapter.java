package net.code.free.fast.receivingcodes.adapter;

import static net.code.free.fast.receivingcodes.util.MyUtil.copyToClipboard;
import static net.code.free.fast.receivingcodes.util.MyUtil.getVerCode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.bean.PhoneBean;
import net.code.free.fast.receivingcodes.bean.SmsBean;
import net.code.free.fast.receivingcodes.ui.SmsAct;
import net.code.free.fast.receivingcodes.util.MyUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SmsAdapter extends  RecyclerView.Adapter<SmsAdapter.ViewHolder>  {

private int selectedPosition = 0;
private Context context;
private ArrayList<SmsBean> states;

public SmsAdapter(Context context, ArrayList<SmsBean> phoneBeans) {
        this.context = context;
        this.states = phoneBeans;
        }

    static class ViewHolder extends RecyclerView.ViewHolder{

    private TextView timeStr;
    private TextView fromStr;
    private TextView msgStr;
    private TextView code;
    private TextView time;
    private TextView from;
    private TextView msg;
    private TextView msg_code;
    private LinearLayout recordRoot;


    public ViewHolder(View itemView) {
        super(itemView);
        timeStr = itemView.findViewById(R.id.timeStr);
        fromStr = itemView.findViewById(R.id.fromStr);
        msgStr = itemView.findViewById(R.id.msgStr);
        recordRoot = itemView.findViewById(R.id.recordRoot);
        code = itemView.findViewById(R.id.code);
        time = itemView.findViewById(R.id.time);
        from = itemView.findViewById(R.id.from);
        msg = itemView.findViewById(R.id.msg);
        msg_code = itemView.findViewById(R.id.msg_code);


    }
}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_msg,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        SmsBean smsBean = states.get(position);
       // holder.recordRoot.setBackgroundResource(smsBean.getChecked()?R.drawable.msg_item_firset_bg: R.drawable.msg_item_bg);

        holder.fromStr.setText(smsBean.getSmsFrom());
        holder.timeStr.setText(getData(smsBean.getTimeStamp()));
        holder.msgStr.setText(smsBean.getContent());
        String verCode = getVerCode(smsBean.getContent());
        holder.code.setText(verCode);

        setTestColor(holder,smsBean.getChecked());

        if (smsBean.getChecked()){
            setSelectedPosition(position);
        }
        holder.recordRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                states.get(getSelectedPosition()).setChecked(false);

                states.get(position).setChecked(true);

                setList(states);
            }
        });

        holder.code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (copyToClipboard(context,verCode)){
                    MyUtil.MyToast(context,"Copy:"+verCode);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return states.size();
    }


    public void setSelectedPosition(int position) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;
    //    notifyItemChanged(previousSelectedPosition);
      //  notifyItemChanged(selectedPosition);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<SmsBean> states) {
        this.states = states;
        notifyDataSetChanged();
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    @SuppressLint("SimpleDateFormat")
    public String getData(long time){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        MyUtil.MyLog(time);
        String date = format.format(time);

        return date;
    }


    public void setTestColor(ViewHolder holder, Boolean b){
        if (b){
            holder.recordRoot.setBackgroundResource(R.drawable.select_item_bg);
            holder.msg_code.setTextColor(context.getColor(R.color.white));
            holder.time.setTextColor(context.getColor(R.color.white));
            holder.from.setTextColor(context.getColor(R.color.white));
            holder.msg.setTextColor(context.getColor(R.color.white));
            holder.timeStr.setTextColor(context.getColor(R.color.white));
            holder.fromStr.setTextColor(context.getColor(R.color.white));
            holder.msgStr.setTextColor(context.getColor(R.color.white));
            holder.code.setTextColor(context.getColor(R.color.white));

        }else {
            holder.recordRoot.setBackgroundResource(R.drawable.no_select_item_bg);
            holder.msg_code.setTextColor(context.getColor(R.color.black));
            holder.time.setTextColor(context.getColor(R.color.black));
            holder.from.setTextColor(context.getColor(R.color.black));
            holder.msg.setTextColor(context.getColor(R.color.black));
            holder.timeStr.setTextColor(context.getColor(R.color.black));
            holder.fromStr.setTextColor(context.getColor(R.color.black));
            holder.msgStr.setTextColor(context.getColor(R.color.black));
            holder.code.setTextColor(context.getColor(R.color.black));
        }
    }
}
