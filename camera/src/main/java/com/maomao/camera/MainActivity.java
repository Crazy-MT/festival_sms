package com.maomao.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int REQ_I = 1;
    private static int REQ_II = 2;
    private ImageView cameraPicImageView ;

    private String mFilePath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        cameraPicImageView = (ImageView) findViewById(R.id.iv_camera_pic);

        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" + "temp.png";
    }

    public void startCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent , REQ_I);
    }

    public void startCameraPic(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT , photoUri);
        startActivityForResult(intent , REQ_II);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_I){
                Bundle bundle = data.getExtras() ;
                Bitmap bitmap = (Bitmap) bundle.get("data");
                cameraPicImageView.setImageBitmap(bitmap);
            } else if (requestCode == REQ_II){
                FileInputStream fileInputStream = null ;
                try {
                    fileInputStream = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    cameraPicImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
