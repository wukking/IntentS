package com.wuyson.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editInput;
    private TextView tvTip;
    private Button btnAction;
    private Button btnParse;
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editInput = findViewById(R.id.edit_input);
        tvTip = findViewById(R.id.tv_tip);
        btnAction = findViewById(R.id.btn_action);
        btnParse = findViewById(R.id.btn_parse);
        btnClear = findViewById(R.id.btn_clear);

        btnAction.setOnClickListener(this);
        btnParse.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
            case R.id.btn_clear:
                editInput.setText("");
                break;
            case R.id.btn_parse:
                if (TextUtils.isEmpty(getClipBoardText())){
                    Toast.makeText(this,"剪贴板无内容，请手动输入",Toast.LENGTH_LONG).show();
                    return;
                }
                editInput.setText(getClipBoardText());
                editInput.setSelection(getClipBoardText().length());
                break;
            case R.id.btn_action:
                String schemeStr = editInput.getText().toString().trim();
                if (TextUtils.isEmpty(schemeStr)){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvTip.setText("正在跳转"+schemeStr);
                Intent intent = new Intent();
                intent.setData(Uri.parse(schemeStr));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "未安装应用", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private String getClipBoardText(){
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if (data == null){
            return "";
        }
        ClipData.Item item = data.getItemAt(0);
        String content = item.getText().toString();
        return content;
    }
}
