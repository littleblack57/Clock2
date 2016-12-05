package com.example.littleblack57.clock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by littleblack57 on 2016/8/5.
 */
public class ClockOptionRecyclerViewAdapter extends RecyclerView.Adapter<ClockOptionRecyclerViewAdapter.ClockOptionViewHolder> {

    OnClockOptionRecyclerViewItemClickListener m_clock_option_item_clicklistener;
    private ClockOptionSelectAdapter clockOptionSelectAdapter
            = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
    private Context context;


    @Override
    public ClockOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clock_option,parent,false);

        ClockOptionViewHolder vh = new ClockOptionViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ClockOptionViewHolder holder, final int position) {

        holder.m_rl_item = (RelativeLayout)holder.itemView.findViewById(R.id.rl_item);
        holder.m_tv_option_title = (TextView)holder.itemView.findViewById(R.id.tv_option_title);
        holder.m_tv_option_content = (TextView)holder.itemView.findViewById(R.id.tv_option_content);
        holder.m_cb_option_checkbox = (CheckBox)holder.itemView.findViewById(R.id.cb_option_checkbox);
        holder.m_tv_option_date = (TextView)holder.itemView.findViewById(R.id.tv_option_date);

        switch (position){
            case 0:
                holder.m_tv_option_title.setText("時間");
                holder.m_cb_option_checkbox.setVisibility(View.GONE);
                holder.m_tv_option_content.setVisibility(View.GONE);
                holder.m_tv_option_date.setVisibility(View.GONE);
                setHourAndMinuteText(holder);
                break;
            case 1:
                holder.m_tv_option_title.setText("重複");
                holder.m_cb_option_checkbox.setVisibility(View.GONE);
                holder.m_tv_option_date.setVisibility(View.GONE);
                selectedDateIsChecked(holder);
                break;
            case 2:
                holder.m_tv_option_title.setText("鈴聲");
                holder.m_cb_option_checkbox.setVisibility(View.GONE);
                holder.m_tv_option_content.setVisibility(View.GONE);
                setRingtoneTitle(holder);
                break;
            case 3:
                holder.m_tv_option_title.setText("震動");
                holder.m_tv_option_content.setVisibility(View.GONE);
                holder.m_tv_option_date.setVisibility(View.GONE);
                holder.m_cb_option_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        clockOptionSelectAdapter.setIsChecked(isChecked);
                        Log.d("ok","CheckBox is checked 1 = " + isChecked);

                    }
                });
                holder.m_cb_option_checkbox.setChecked(clockOptionSelectAdapter.getIsChecked());




        }

        if(m_clock_option_item_clicklistener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_clock_option_item_clicklistener.OnClick(v,position);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private void setHourAndMinuteText(ClockOptionViewHolder holder){

       holder.m_tv_option_content.setVisibility(View.VISIBLE);
       holder.m_tv_option_content.setText(clockOptionSelectAdapter.toStringClock());

    }


    private void selectedDateIsChecked(ClockOptionViewHolder holder) {

        boolean[] selected = clockOptionSelectAdapter.getSelected();

        holder.m_tv_option_date.setText("");

        if (selected[0]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.setText("一");
        }

        if (selected[1]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 二");
        }

        if (selected[2]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 三");
        }

        if (selected[3]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 四");
        }

        if (selected[4]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 五");
        }

        if (selected[5]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 六");
        }

        if (selected[6]) {
            holder.m_tv_option_date.setVisibility(View.VISIBLE);
            holder.m_tv_option_date.append(" 日");
        }


    }


    private void setRingtoneTitle(ClockOptionViewHolder holder){

        holder.m_tv_option_content.setVisibility(View.VISIBLE);


        if(clockOptionSelectAdapter.getRingtoneTitle() != null) {


            holder.m_tv_option_content.setText(clockOptionSelectAdapter.getRingtoneTitle());
        }
    }


    public static class ClockOptionViewHolder extends RecyclerView.ViewHolder{


        private TextView m_tv_option_title;
        private CheckBox m_cb_option_checkbox;
        private TextView m_tv_option_content;
        private TextView m_tv_option_date;
        private RelativeLayout m_rl_item;

        public ClockOptionViewHolder(View itemView) {
            super(itemView);

            m_cb_option_checkbox = (CheckBox)itemView.findViewById(R.id.cb_option_checkbox);
            m_tv_option_title = (TextView)itemView.findViewById(R.id.tv_option_title);
            m_tv_option_content = (TextView)itemView.findViewById(R.id.tv_option_content);
            m_tv_option_date = (TextView)itemView.findViewById(R.id.tv_option_date);
            m_rl_item = (RelativeLayout)itemView.findViewById(R.id.rl_item);

        }
    }

    public interface OnClockOptionRecyclerViewItemClickListener {
        void OnClick(View view ,int position);
    }

    public void setOnClockOptionRecyclerViewItemClickListener
            (OnClockOptionRecyclerViewItemClickListener onClockOptionRecyclerViewItemClickListener){

        this.m_clock_option_item_clicklistener = onClockOptionRecyclerViewItemClickListener;

    }


}
