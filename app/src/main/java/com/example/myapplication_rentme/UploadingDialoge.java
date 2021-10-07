package com.example.myapplication_rentme;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class UploadingDialoge {
    private Context context;
    private TextView title_view, loading_view;
    private Dialog dialog;

    public UploadingDialoge(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature((Window.FEATURE_NO_TITLE));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.uploading_view);
        title_view = dialog.findViewById(R.id.Title_View_Id);
        loading_view = dialog.findViewById(R.id.Percentage_View_Id);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public void Set_Title(String title) {
        title_view.setText(title);
    }

    public void Set_Percetage(int value) {
        String P_Value = value + "%";
        loading_view.setText(P_Value);
    }

    public void Cancel_Able(boolean type) {
        dialog.setCancelable(true);
    }

    public void Show() {
        dialog.show();
    }

    public void Dissmis() {
        dialog.dismiss();
    }

    public void Set_Full_Width() {
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}