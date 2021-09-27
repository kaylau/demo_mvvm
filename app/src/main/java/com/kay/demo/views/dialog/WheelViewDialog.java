package com.kay.demo.views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kay.demo.R;
import com.kay.demo.views.dialog.bottom.SelectAdapter;

import java.util.List;

/**
 * @Author: kayLau
 * @E-mail: kailiu@epochcredit.com
 * @CreateDate: 2021/9/28 00:41
 * @Description:
 */
public class WheelViewDialog {

    public static WheelViewDialog instance;
    private BottomSheetDialog bottomSheetDialog;
    private String selectItem;

    private WheelViewDialog() {
    }

    public static WheelViewDialog getInstance() {
        if (instance == null)
            instance = new WheelViewDialog();
        return instance;
    }

    public void show(Context context, List<String> data, SelectAdapter.ChooseItem listener) {
        View v = View.inflate(context, R.layout.dialog_wheel_view, null);
        bottomSheetDialog = new BottomSheetDialog(context, R.style.bottomDialog);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        TextView cancel = v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });
        TextView ok = v.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chooseContent(selectItem);
                bottomSheetDialog.dismiss();
                bottomSheetDialog = null;
            }
        });
        WheelView wheelView = v.findViewById(R.id.wheelview);

        wheelView.setCyclic(false);
        int currentItem = data.size() / 2;
        wheelView.setCurrentItem(currentItem);
        wheelView.setDividerColor(R.color.color_ff0000);

        wheelView.setAdapter(new ArrayWheelAdapter(data));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectItem = data.get(index);
            }
        });

        bottomSheetDialog.show();
    }
}
