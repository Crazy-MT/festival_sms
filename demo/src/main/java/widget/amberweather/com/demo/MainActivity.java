package widget.amberweather.com.demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView bitmap_img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bitmap_img = (ImageView) findViewById(R.id.img_bitmap);
        bitmap_img.setImageBitmap(canvasBitmap());
    }

    private Bitmap canvasBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(500 , 500 , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        canvas.drawText("画圆：", 10, 20, p);// 画文本
        canvas.drawCircle(60, 20, 10, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, p);// 大圆

        return bitmap;
    }
}
