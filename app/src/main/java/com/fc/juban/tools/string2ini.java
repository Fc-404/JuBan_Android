package com.fc.juban.tools;

public class string2ini {
    twoArray result;

    public string2ini(String str){
        this.result = new twoArray();
        if (str.isEmpty())
            return;
        //
        String group = "";
        String key = "";
        String value = "";
        String strTemp = str;
        for (int i = 0; i < str.length();){
            strTemp = str.substring(i);
            if (strTemp.substring(0, 1).equals("[")){
                int groupIndex_ = strTemp.indexOf("]");
                group = strTemp.substring(1, groupIndex_);
                i += strTemp.indexOf("\n") + 1;
                continue;
            }
            if (strTemp.substring(0, 1).equals("\n")){
                i++;
                continue;
            }

            key = strTemp.substring(0, strTemp.indexOf("="));
            value = strTemp.substring(strTemp.indexOf("=") + 1, strTemp.indexOf("\n"));
            if (group.equals(""))
                result.setKeyValue(key, value);
            else
                result.setKeyValue(key, value, group);
            i += strTemp.indexOf("\n") + 1;
        }
    }

    public twoArray getResult(){
        return this.result;
    }

}
