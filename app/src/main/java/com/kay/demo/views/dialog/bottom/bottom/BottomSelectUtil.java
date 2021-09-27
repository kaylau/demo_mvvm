package com.kay.demo.views.dialog.bottom.bottom;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kay.demo.R;

import java.util.List;

/**
 * @Author: Administrator
 * @E-mail: liukai@duandai.com
 * @CreateDate: 2021/1/15 15:54
 * @Description:
 */
public class BottomSelectUtil {

    public static BottomSelectUtil instance;
    private BottomSheetDialog bottomSheetDialog;

    private BottomSelectUtil() {
    }

    public static BottomSelectUtil getInstance() {
        if (instance == null)
            instance = new BottomSelectUtil();
        return instance;
    }

    public void show(Context context, List<String> data, String select, SelectAdapter.ChooseItem listener) {
        View v = View.inflate(context, R.layout.dialog_select_layout, null);
        bottomSheetDialog = new BottomSheetDialog(context, R.style.bottomDialog);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        SelectAdapter adapter = new SelectAdapter(context, data, select, new SelectAdapter.ChooseItem() {
            @Override
            public void chooseContent(String select) {
                listener.chooseContent(select);
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });
        recyclerView.setAdapter(adapter);
        TextView cancel = v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();
    }
}
