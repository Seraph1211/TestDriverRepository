package com.example.test2project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Base64Activity extends AppCompatActivity {
    private Button buttonLoadBase64Image;
    private ImageView imageBase64;
    private static final String TAG = "Base64Activity";
    private String str1="data:image/jpg;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAjCAIAAACmdes6AAABa0lEQVR42u3Yza3CMAwA4Nw4cOPMAm8DdmEFFmAEDszMixTJMrZjO/+oShUhFNHir06cNuFzuCNs0iZt0iZt0iZtUl9DOBYpeg5F4p560uv61tsqT2uWOGCaKuexSee/U2x+kt4/wWOQkufXSLpHI5meJSTTs550ebgm8/15iw17Ug/5QeoJOY85kXKhF3n8JO7hpGyWgJEjwaVbKl7yOEnJA0GXkbBBJKWrJ1XRogQ3AjCkmR5Mgk8+CEORh/wNMYgeuAV4WvtTBOdC3PiLQSIAcyJhFR+HWMJrlJOETy8m8ehNElYBSZfUkUjcfPhhzxdJaUrFIwaStxYSuSlQEryk0keh3NDqVe54knNVjnhqSGJOxDJYTRIHbStJSVEuJwSpkCo8Y0n6g4JnOpG1CPOUosKjFz3yUuupDcoK68mVuMjqRbKeNO15uf2U2TtERSF29IzdTjEX3BGe4TtE9htob8/iTa8Rnnj8A2JPWWXlAigEAAAAAElFTkSuQmCC";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base64);

        buttonLoadBase64Image = findViewById(R.id.buttonLoadBase64Image);
        imageBase64 = findViewById(R.id.imageBase64);

        buttonLoadBase64Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Base64Activity.this, "load image!", Toast.LENGTH_SHORT).show();
                loadBase64Image(str1, imageBase64);

            }
        });
    }

    public void loadBase64Image(String base64String, ImageView imageView){
        Log.d(TAG, "loadBase64Image: ");
        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64String.split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //设置ImageView图片
        imageView.setImageBitmap(decodedByte);
    }

    public String encodeImageToBase64String(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //读取图片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream); //参数如果为100那么就不压缩
        byte[] bytes = outputStream.toByteArray();
        String strImg = Base64.encodeToString(bytes, Base64.DEFAULT);
        return strImg;
    }

}
