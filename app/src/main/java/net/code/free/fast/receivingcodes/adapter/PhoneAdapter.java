package net.code.free.fast.receivingcodes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.code.free.fast.receivingcodes.R;
import net.code.free.fast.receivingcodes.bean.PhoneBean;
import net.code.free.fast.receivingcodes.ui.SmsAct;

import java.util.ArrayList;

public class PhoneAdapter extends  RecyclerView.Adapter<PhoneAdapter.ViewHolder>  {

private int selectedPosition = 0;
private Context context;
private ArrayList<PhoneBean> states;

public PhoneAdapter(Context context, ArrayList<PhoneBean> phoneBeans) {
        this.context = context;
        this.states = phoneBeans;
        }

    static class ViewHolder extends RecyclerView.ViewHolder{

    private TextView itemCountry;
    private TextView itemNum;
    private TextView itemOpenTv;
    private RelativeLayout itemRoot;
    private RelativeLayout itemOpen;
    private ImageView itemImg;
    private CardView cards;

    public ViewHolder(View itemView) {
        super(itemView);
        itemRoot = itemView.findViewById(R.id.itemRoot);
        itemCountry = itemView.findViewById(R.id.itemCountry);
        itemNum = itemView.findViewById(R.id.itemNum);
        itemOpenTv = itemView.findViewById(R.id.itemOpenTv);
        itemOpen = itemView.findViewById(R.id.itemOpen);
        itemImg = itemView.findViewById(R.id.itemImg);
        cards = itemView.findViewById(R.id.cards);

    }
}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_num_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        PhoneBean phoneBean = states.get(position);
        holder.itemRoot.setBackgroundResource(phoneBean.getChecked()?R.drawable.num_first_bg: R.drawable.number_item_selector);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        holder.itemRoot.getLayoutParams().height = height/10;

        holder.itemCountry.setText(phoneBean.getCountryName());
        holder.itemNum.setText(phoneBean.getNumber());
        setFlag(holder,phoneBean.getCountryName());
      //  holder.itemImg.getLayoutParams().height = (displayMetrics.heightPixels)/11;

        holder.itemImg.getLayoutParams().width = (int) ((displayMetrics.widthPixels)/3.5);
        holder.cards.getLayoutParams().height =  (displayMetrics.heightPixels)/10;

        holder.itemOpenTv.setTextColor(phoneBean.getChecked()?context.getColor(R.color.white):context.getColor(R.color.blue1));
        holder.itemOpenTv.setBackgroundResource(phoneBean.getChecked()? R.drawable.open_first:R.drawable.open_selector);

        if (phoneBean.getChecked()){
            setSelectedPosition(position);
        }

        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                states.get(getSelectedPosition()).setChecked(false);

                states.get(position).setChecked(true);

                setList(states);
            }
        });

        holder.itemOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneBean phoneBean1 = states.get(position);

                Intent intent = new Intent(context, SmsAct.class);
                intent.putExtra("SmsInfo",phoneBean1);
                ((Activity)(context)).startActivity(intent);
            }
        });

    }

    public static void setFlag(ViewHolder holder,String countryName){
        switch (countryName) {
            case "United States" : {
                holder.itemImg.setImageResource(R.drawable.us);
                break;
            }

            case "United Kingdom" : {
                holder.itemImg.setImageResource(R.drawable.au);
                break;
            }

            case "Germany" : {
                holder.itemImg.setImageResource(R.drawable.de);
                break;
            }

            case "France" : {
                holder.itemImg.setImageResource(R.drawable.fr);
                break;

            }

            case "Japan" : {
                holder.itemImg.setImageResource(R.drawable.jp);
                break;
            }

            case "Australia" : {
                holder.itemImg.setImageResource(R.drawable.au);
                break;
            }

            case "Singapore" : {
                holder.itemImg.setImageResource(R.drawable.sg);
                break;
            }

            default:
                holder.itemImg.setImageResource(R.drawable.us);
                break;

        }

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
    public void setList(ArrayList<PhoneBean> states) {
        this.states = states;
        notifyDataSetChanged();
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }
}
