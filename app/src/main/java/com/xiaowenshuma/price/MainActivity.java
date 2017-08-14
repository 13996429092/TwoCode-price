package com.xiaowenshuma.price;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaowenshuma.twocode.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgsys;
    private EditText  number;
    private Button    savepw;
    private EditText  password;
    private bikedao dao=new bikedao(this);
    private TextView tv_passwordcount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        imgsys= (ImageView) findViewById(R.id.img_sys);
        number= (EditText) findViewById(R.id.et_number);
        savepw= (Button) findViewById(R.id.bt_save);
        password= (EditText) findViewById(R.id.et_password);
        tv_passwordcount=(TextView)findViewById(R.id.tv_showpasswordcount);
        imgsys.setOnClickListener(this);
        savepw.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
          
            case R.id.img_sys:
                Intent inten = new Intent(MainActivity.this, ScannerActivity.class);
                startActivityForResult(inten,1);
                break;
            case R.id.bt_save:
                long id=-1;
                String hq_number=number.getText().toString().trim();
                String hq_password=password.getText().toString().trim();
                if(!("".equals(hq_number)||"".equals(hq_password))  ){

                    bike newbike=new bike(hq_number,hq_password);

                    id=dao.insert(newbike);
                    if(id==-1){
                        Toast.makeText(MainActivity.this,"保存失败/已经存在",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        number.setText("");
                        password.setText("");
                        password.requestFocus();
                        showpasswordcount();
                        Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    }

                    break;
                }else {
                    Toast.makeText(MainActivity.this,"请确认扫码成功并输入价格",Toast.LENGTH_SHORT).show();

                }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showpasswordcount();


    }

    private void showpasswordcount() {
        int passwordcount=dao.query();
        tv_passwordcount.setText("你现在拥有"+passwordcount+"个产品");
    }

    @Override
    //重写活动获得结果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){//分别处理不同的请求码
            case 1:
                if(resultCode==RESULT_OK){  //根据其他活动发送的结果码来获取数据
                    String returnData=data.getStringExtra("fasong");
                    number.setText(returnData);
                    List<bike> list=dao.query("_number=?",new String[] { returnData });
                    //
                    if(list.size()==1){
                        bike bk=list.get(0);
                        String intpassword=bk.getPassword();

                        password.setText(intpassword+"");
                        Toast.makeText(MainActivity.this,"恭喜价格是:"+intpassword,Toast.LENGTH_LONG).show();
                    }else {
                        password.setText("");
                        Toast.makeText(MainActivity.this,"积少成多,保存价格,努力吧",Toast.LENGTH_LONG).show();
                    }

                    password.requestFocus();

                }
        }
    }
}
