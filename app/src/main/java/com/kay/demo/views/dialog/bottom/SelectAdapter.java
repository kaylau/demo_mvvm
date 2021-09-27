package com.kay.demo.views.dialog.bottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kay.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Administrator
 * @E-mail: liukai@duandai.com
 * @CreateDate: 2021/1/15 15:54
 * @Description:
 */
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {

    private String selectStr;
    private Context context;
    private List<String> content;
    private final LayoutInflater inflater;
    private ChooseItem chooseItem;

    public SelectAdapter(Context context, List<String> data, String select, ChooseItem listener) {
        this.context = context;
        if (data == null)
            content = new ArrayList<>();
        else
            content = data;
        selectStr = select;
        chooseItem = listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_select_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = content.get(position);
//        if (TextUtils.equals(title, selectStr))
//            holder.content.setTextColor(context.getResources().getColor(R.color.color_2A303C));
//        else
//            holder.content.setTextColor(context.getResources().getColor(R.color.color_2A303C));
        holder.content.setText(title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseItem != null)
                    chooseItem.chooseContent(title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface ChooseItem {
        void chooseContent(String select);
    }
}

