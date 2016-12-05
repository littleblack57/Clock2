package com.example.littleblack57.clock;

import android.graphics.Color;
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
 * Created by littleblack57 on 2016/8/3.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    public OnItemClickListener m_OnItemClickListener;
    private TextView m_tv_text_clock;
    private ClockOptionSelectAdapter clockOptionSelectAdapter
            = ClockOptionSelectAdapterFactory.getClockOptionSelectAdapter();
    private int m_count = clockOptionSelectAdapter.getCount();




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clockbox, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.m_tv_text_clock.setText(clockOptionSelectAdapter.toStringMainClock(position));
        holder.m_cb_clock_check.setTag(position);
        holder.m_cb_clock_check.setChecked(clockOptionSelectAdapter.getClockPowerArrayPosition(position));
        holder.m_cb_clock_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                clockOptionSelectAdapter.setClockPower(isChecked);
                clockOptionSelectAdapter.setClockPowerArrayPosition(position,isChecked);
                if(!isChecked){

                    holder.m_tv_text_clock.setTextColor(Color.DKGRAY);

                }else {

                    holder.m_tv_text_clock.setTextColor(Color.parseColor("#00BFA5"));


                }

            }
        });

        holder.m_tv_month.setVisibility(View.GONE);
        selectedDayOfWeekColor(holder,position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_OnItemClickListener.OnClick(v,position);

            }
        });




    }



    public void selectedDayOfWeekColor(MyViewHolder holder, int position){

        boolean[] selected_array = clockOptionSelectAdapter.getDateSelectedArrayPosition(position);
        Log.d("666","date_selected_position" + position);
        for (int i = 0 ; i < selected_array.length ; i++) {
            switch (i){
                case 0:
                    if(selected_array[0]) {

                        holder.m_tv_monday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_monday.setTextColor(Color.DKGRAY);

                    }
                    break;
                case 1:
                    if(selected_array[1]) {

                        holder.m_tv_tuesday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_tuesday.setTextColor(Color.DKGRAY);

                    }
                    break;
                case 2:
                    if(selected_array[2]) {

                        holder.m_tv_wednesday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_wednesday.setTextColor(Color.DKGRAY);

                    }
                    break;
                case 3:
                    if(selected_array[3]) {

                        holder.m_tv_thursday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_thursday.setTextColor(Color.DKGRAY);

                    }
                    break;
                case 4:
                    if(selected_array[4]) {

                        holder.m_tv_friday.setTextColor(Color.parseColor("#00BFA5"));

                    }else{

                        holder.m_tv_friday.setTextColor(Color.DKGRAY);

                    }

                    break;
                case 5:
                    if(selected_array[5]) {

                        holder.m_tv_saturday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_saturday.setTextColor(Color.DKGRAY);

                    }
                    break;
                case 6:
                    if(selected_array[6]) {

                        holder.m_tv_sunday.setTextColor(Color.parseColor("#00BFA5"));

                    }else {

                        holder.m_tv_sunday.setTextColor(Color.DKGRAY);

                    }
                    break;
            }

        }

    }

    public void addItem(){

        ++m_count;
        notifyItemInserted(m_count);
        setCount(m_count);


    }

    public void deleteItem(int position){

        --m_count;
        notifyItemRemoved(position);
        setCount(m_count);

    }




    @Override
    public int getItemCount() {
       return m_count;
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.m_OnItemClickListener = onItemClickListener;

    }

    public int getCount() {
        return m_count;
    }

    public void setCount(int count) {
        this.m_count = count;
    }

    public interface OnItemClickListener {
        void OnClick(View view,int position);
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView m_tv_text_clock;
        private CheckBox m_cb_clock_check;
        private TextView m_tv_month;
        private TextView m_tv_monday;
        private TextView m_tv_tuesday;
        private TextView m_tv_wednesday;
        private TextView m_tv_thursday;
        private TextView m_tv_friday;
        private TextView m_tv_saturday;
        private TextView m_tv_sunday;
        private RelativeLayout m_rl_relativelayout;



        public MyViewHolder(View itemView) {
            super(itemView);
            m_tv_text_clock = (TextView) itemView.findViewById(R.id.tv_text_clock);
            m_cb_clock_check = (CheckBox) itemView.findViewById(R.id.cb_clock_check);
            m_tv_month = (TextView) itemView.findViewById(R.id.tv_month);
            m_tv_monday = (TextView) itemView.findViewById(R.id.tv_monday);
            m_tv_tuesday = (TextView) itemView.findViewById(R.id.tv_tuesday);
            m_tv_wednesday = (TextView) itemView.findViewById(R.id.tv_wednesday);
            m_tv_thursday = (TextView) itemView.findViewById(R.id.tv_thursday);
            m_tv_friday = (TextView) itemView.findViewById(R.id.tv_friday);
            m_tv_saturday = (TextView) itemView.findViewById(R.id.tv_saturday);
            m_tv_sunday = (TextView) itemView.findViewById(R.id.tv_sunday);
            m_rl_relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_relativelayout);


        }



    }




}


