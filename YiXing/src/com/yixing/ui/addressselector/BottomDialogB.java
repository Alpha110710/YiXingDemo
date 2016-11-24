package com.yixing.ui.addressselector;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yixing.R;
import com.yixing.ui.addressselector.Dev;


public class BottomDialogB extends Dialog {
    private AddressSelectorB selector;

    public BottomDialogB(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    public BottomDialogB(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomDialogB(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        selector = new AddressSelectorB(context);
        setContentView(selector.getView());

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = Dev.dp2px(context, 256);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    public static BottomDialogB show(Context context) {
        return show(context, null);
    }

    public static BottomDialogB show(Context context, OnAddressSelectedListener listener) {
        BottomDialogB dialog = new BottomDialogB(context, R.style.bottom_dialog);
        dialog.selector.setOnAddressSelectedListener(listener);
        dialog.show();

        return dialog;
    }

    @Override
    public void dismiss() {
       /* if (selector != null) {
            selector.closeDB();
        }*/
        super.dismiss();
    }

}
