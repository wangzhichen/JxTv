package com.wantup.jxtv.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wantup.jxtv.R;
import com.wantup.jxtv.bean.TableData;
import com.wantup.jxtv.bean.UserData;
import com.wantup.jxtv.util.NetUtil;
import com.wantup.jxtv.view.MyTableTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private LinearLayout linear_mytable;
    private TextView tv_title;
    private UserData data = new UserData();
    private Socket mSocket;
    private BufferedReader mReader;
    private BufferedWriter mWriter;
    private int port = 2333;
    private boolean isReceiveData;

    private MainHandler handler = new MainHandler(new WeakReference<MainActivity>(this));

    private ArrayList<TableData> tableHeadList = new ArrayList<>();
    private ArrayList<ArrayList<TableData>> tableBodyList = new ArrayList<>();

    private ImageView img_background;


    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSocket();
        // TODO 测试数据
//        String hh = "{\"tableTitle\":\"南昌医院员工值班表\",\"tableHeadArr\":[{\"id\":\"43243244\",\"textName\":\"姓名\",\"textColor\":\"#000000\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"年龄\",\"textColor\":\"#000000\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"性别\",\"textColor\":\"#000000\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"电话\",\"textColor\":\"#000000\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"职位\",\"textColor\":\"#000000\",\"textSize\":\"16\"}],\"tableBodyArr\":[[{\"id\":\"43243244\",\"textName\":\"张三\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"23\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"男\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"18704578788\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"护士\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"}],[{\"id\":\"43243244\",\"textName\":\"李四\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"25\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"男\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"13364567890\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"医生\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"}],[{\"id\":\"43243244\",\"textName\":\"王五\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"33\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"女\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"15567895678\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"},{\"id\":\"43243244\",\"textName\":\"护士\",\"textColor\":\"#3F51B5\",\"textSize\":\"16\"}]],\"code\":\"0\"}";
//        Message m = handler.obtainMessage();
//        m.what = 0x01;
//        m.obj = hh;
//        handler.sendMessage(m);


    }

    private void initSocket() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                isReceiveData = true;
                try {
                    mSocket = new Socket(NetUtil.SOCKET_IP, port);

                    mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "utf-8"));
                    mWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "utf-8"));

                    dataInputStream = new DataInputStream(mSocket.getInputStream());
                    dataOutputStream = new DataOutputStream(mSocket.getOutputStream());

                    //发送数据
                    while (isReceiveData)
                    {
                        System.out.println("开始接受数据");
                        byte[] lengthByte = new byte[6];
                        dataInputStream.read(lengthByte);
                        String lengthStr = new String(lengthByte);
                        System.out.println("看了就看见"+lengthStr);
                        int len = Integer.valueOf(lengthStr);
                        byte[] contentByte = new byte[len];
//                            dataInputStream.read(contentByte);
                        dataInputStream.readFully(contentByte);
                        String contentStr = new String(contentByte, "utf-8");
                        System.out.println("凭借结果是："+contentStr);

                        Message m = handler.obtainMessage();
                        m.what = 0x01;
                        m.obj = contentStr;
                        handler.sendMessage(m);



//                        int totalLen = dataInputStream.readInt();
//                        byte[] data = new byte[totalLen - 4];
//                        dataInputStream.read(data);
//                        String msg = new String(data);
//                        System.out.println("接受的数据是:"+msg);
//
//                        Message m = handler.obtainMessage();
//                        m.what = 0x01;
//                        m.obj = msg;
//                        handler.sendMessage(m);


//                        //TODO 判断是否断开连接，断开会有异常
//                        if(mReader.ready())
//                        {
//                            Message m = handler.obtainMessage();
//                            m.what = 0x01;
//                            m.obj = mReader.readLine();
//                            handler.sendMessage(m);
//                            System.out.println("看见了"+mReader.readLine());
//                        }
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void initView()
    {
        img_background = (ImageView)findViewById(R.id.img_background);
        img_background.setAlpha(0.5f);
        tv_title = (TextView)findViewById(R.id.tv_title);
        linear_mytable = (LinearLayout)findViewById(R.id.linear_mytable);
    }

    private void updateData(int start, int end)
    {

        linear_mytable.removeAllViews();
        LinearLayout mytable_line = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.mytable_line, null);

        for(int i=0;i<tableHeadList.size();i++)
        {
            MyTableTextView myTableTextView = new MyTableTextView(this);
            myTableTextView.setText(tableHeadList.get(i).textName);
            myTableTextView.setTextColor(Color.parseColor(tableHeadList.get(i).textColor));
//            myTableTextView.setTextSize(tableHeadList.get(i).textSize);
            //指定设定字体大小的单位
            myTableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableHeadList.get(i).textSize);
            myTableTextView.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.y100), ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if(i == tableHeadList.size() - 1)
            {
                myTableTextView.setLayoutParams(params2);
            }
            else
            {
                myTableTextView.setLayoutParams(params1);
            }
            myTableTextView.setPadding(0, (int)getResources().getDimension(R.dimen.y2), 0, (int)getResources().getDimension(R.dimen.y2));
            myTableTextView.setMaxLines(3);
            myTableTextView.setEllipsize(TextUtils.TruncateAt.END);

            mytable_line.addView(myTableTextView);

        }
        linear_mytable.addView(mytable_line);

        for(int i = start; i < end; i++)
        {
            mytable_line = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.mytable_line, null);

            for(int j=0;j<tableBodyList.get(i).size();j++)
            {
                MyTableTextView myTableTextView = new MyTableTextView(this);
                myTableTextView.setText(tableBodyList.get(i).get(j).textName);
                myTableTextView.setTextColor(Color.parseColor(tableBodyList.get(i).get(j).textColor));
//                myTableTextView.setTextSize(tableBodyList.get(i).get(j).textSize);
                //指定设定字体大小的单位
                myTableTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tableBodyList.get(i).get(j).textSize);
                myTableTextView.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.y100), ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //最后一列充满剩余屏幕部分
                if(j == tableBodyList.get(i).size() - 1)
                {
                    myTableTextView.setLayoutParams(params2);
                }
                else
                {
                    myTableTextView.setLayoutParams(params1);
                }
                myTableTextView.setPadding(0, (int)getResources().getDimension(R.dimen.y2), 0, (int)getResources().getDimension(R.dimen.y2));
                myTableTextView.setMaxLines(3);
                myTableTextView.setEllipsize(TextUtils.TruncateAt.END);

                mytable_line.addView(myTableTextView);
            }
            linear_mytable.addView(mytable_line);
        }
    }

    private MyTimerTask task;
    private Timer timer;

    class MyTimerTask extends TimerTask
    {
        @Override
        public void run() {
            Message m = handler.obtainMessage();
            m.what = 0x02;
            handler.sendMessage(m);
        }
    }

    //启动定时器
    private void startTimer()
    {
        if(timer == null)
        {
            timer = new Timer();
            if(task != null)
            {
                task.cancel();
            }
            task = new MyTimerTask();
            timer.schedule(task, 10000, 10000);
        }
        else
        {
            if(task != null)
            {
                task.cancel();
            }
            task = new MyTimerTask();
            timer.schedule(task, 10000, 10000);
        }
    }
    //停止定时器
    private void stopTimer()
    {
        if(timer != null)
        {
            timer.cancel();
            timer = null;
        }
        if(task != null)
        {
            task.cancel();
            task = null;
        }
    }

    //显示页面的下标
    private int index = 0;

    private static class MainHandler extends Handler
    {
        private WeakReference<MainActivity> weakReference;

        public MainHandler(WeakReference<MainActivity> wk)
        {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            if(activity == null)
            {
                return;
            }

            switch (msg.what)
            {
                case 0x01:
                    activity.stopTimer();
                    String jsonString = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        int code = jsonObject.optInt("code");
                        //code是1代表成功，要更新表格所有内容
                        if(code == 1)
                        {
                            String url = jsonObject.optString("photo");
                            if(!activity.isFinishing())
                            {
                                Glide.with(activity.getApplicationContext()).load(url).into(activity.img_background);
                            }
                            String tableTitle = jsonObject.optString("tableTitle");
                            activity.tv_title.setText(tableTitle);


                            activity.tableHeadList.clear();
                            activity.tableBodyList.clear();
                            activity.index = 0;

                            JSONArray headArray = jsonObject.optJSONArray("tableHeadArr");
                            for(int i=0;i<headArray.length();i++)
                            {
                                TableData data = new TableData();
                                data.fromJson(headArray.optJSONObject(i));
                                activity.tableHeadList.add(data);
                            }
                            JSONArray bodyArray = jsonObject.optJSONArray("tableBodyArr");
                            for(int i=0;i<bodyArray.length();i++)
                            {
                                JSONArray bodyContentArr = bodyArray.optJSONArray(i);
                                ArrayList<TableData> bodyContentList = new ArrayList<>();
                                for(int j=0;j<bodyContentArr.length();j++)
                                {
                                    TableData data = new TableData();
                                    data.fromJson(bodyContentArr.optJSONObject(j));
                                    bodyContentList.add(data);
                                }
                                activity.tableBodyList.add(bodyContentList);
                            }

                            if(activity.tableBodyList.size() > 10)
                            {
                                activity.updateData(0, 10);
                            }
                            else
                            {
                                activity.updateData(0, activity.tableBodyList.size());
                            }
                            activity.startTimer();

                        }
                        else 
                        {
                            // TODO: 2017/1/19
                            activity.tableBodyList.get(3).get(4).textColor = "#ae443d";
                            activity.tableBodyList.get(3).get(4).textName = "改变";
                            activity.tableBodyList.get(3).get(4).textSize = 12;

                            if(activity.tableBodyList.size() > 10)
                            {
                                activity.updateData(0, 10);
                            }
                            else
                            {
                                activity.updateData(0, activity.tableBodyList.size());
                            }
                            activity.startTimer();
                        }
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x02:
                    //大于10才翻页，小于10不翻页
                    if(activity.tableBodyList.size() > 10)
                    {
                        if(activity.tableBodyList.size() > ((activity.index+1) * 10))
                        {
                            activity.updateData(activity.index*10, (activity.index+1)*10);
                            activity.index++;
                        }
                        else
                        {
                            activity.updateData(activity.index*10, activity.tableBodyList.size());
                            activity.index = 0;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.v(TAG, "ORIENTATION_LANDSCAPE");
        }
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Log.v(TAG, "ORIENTATION_PORTRAIT");
        }
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO)
        {
            Log.v(TAG, "HARDKEYBOARDHIDDEN_NO");
        }
        else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES)
        {
            Log.v(TAG, "HARDKEYBOARDHIDDEN_YES");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
