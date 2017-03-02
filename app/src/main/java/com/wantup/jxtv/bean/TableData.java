package com.wantup.jxtv.bean;

import org.json.JSONObject;

/**
 * Created by wantup on 2017/1/19.
 */

public class TableData {

    public String id;
    public String textName;
    public String textColor;
    public int textSize;

    //{"tableTitle":"南昌医院员工值班表","tableHeadArr":[{"id":"43243244","textName":"姓名","textColor":"#000000","textSize":"16"},{"id":"43243244","textName":"年龄","textColor":"#000000","textSize":"16"},{"id":"43243244","textName":"性别","textColor":"#000000","textSize":"16"},{"id":"43243244","textName":"电话","textColor":"#000000","textSize":"16"},{"id":"43243244","textName":"职位","textColor":"#000000","textSize":"16"}],"tableBodyArr":[[{"id":"43243244","textName":"张三","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"23","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"男","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"18704578788","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"护士","textColor":"#3F51B5","textSize":"16"}],[{"id":"43243244","textName":"李四","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"25","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"男","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"13364567890","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"医生","textColor":"#3F51B5","textSize":"16"}],[{"id":"43243244","textName":"王五","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"33","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"女","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"15567895678","textColor":"#3F51B5","textSize":"16"},{"id":"43243244","textName":"护士","textColor":"#3F51B5","textSize":"16"}]],"code":"0"}
    public void fromJson(JSONObject jsonObject)
    {
        id = jsonObject.optString("id");
        if(id == null || id.equals("null") || id.equals(""))
        {
            id = null;
        }
        textName = jsonObject.optString("textName");
        if(textName == null || textName.equals("null") || textName.equals(""))
        {
            textName = null;
        }
        textColor = jsonObject.optString("textColor");
        if(textColor == null || textColor.equals("null") || textColor.equals(""))
        {
            textColor = null;
        }
        textSize = jsonObject.optInt("textSize");
    }
}
