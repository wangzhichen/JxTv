package com.wantup.jxtv.bean;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by wantup on 2017/1/13.
 */

public class UserData {

    //表格标题栏的所有的标题
    public ArrayList<String> titleList = new ArrayList<>();
    //每一行对应的数据
    public ArrayList<ArrayList<String>> contentList = new ArrayList<>();


    //{"titleArr":[{"title":"姓名"},{"title":"年龄"},{"title":"性别"},{"title":"电话"},{"title":"职位"}],"contentArr":[[{"content":"张三"},{"content":"23"},{"content":"男"},{"content":"18735392050"},{"content":"护士"}],[{"content":"李四"},{"content":"25"},{"content":"男"},{"content":"13335392050"},{"content":"医生"}],[{"content":"王五"},{"content":"33"},{"content":"女"},{"content":"17935392050"},{"content":"护士"}]]}

    public void fromJson(JSONObject json)
    {
        JSONArray titleJson = json.optJSONArray("titleArr");
        for(int i=0;i<titleJson.length();i++)
        {
            JSONObject titleObj = titleJson.optJSONObject(i);
            String title = titleObj.optString("title");
            titleList.add(title);
        }

        JSONArray contentArr = json.optJSONArray("contentArr");
        for(int i=0;i<contentArr.length();i++)
        {
            JSONArray contentLineArr = contentArr.optJSONArray(i);
            ArrayList<String> contentLineList = new ArrayList<>();
            for(int j=0;j<contentLineArr.length();j++)
            {
                JSONObject contentObj = contentLineArr.optJSONObject(j);
                String content = contentObj.optString("content");
                contentLineList.add(content);
            }
            contentList.add(contentLineList);
        }
    }

}
