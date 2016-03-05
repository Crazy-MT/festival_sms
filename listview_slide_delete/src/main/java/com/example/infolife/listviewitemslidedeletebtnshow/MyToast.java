package com.example.infolife.listviewitemslidedeletebtnshow;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by infolife on 2015/12/4.
 */
public class MyToast {

    private static Toast toast;

    public static void show(Context context, String str) {
        if (toast != null) {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
