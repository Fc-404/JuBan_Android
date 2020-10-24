package com.fc.juban.tools;

public class xml2iniR {
    private twoArrayR resultR;

    public xml2iniR(String source){
        this.resultR = new twoArrayR();
        resultR.repeat(true);
        if (source == null || source.isEmpty())
            return;
        //
        String group = null;
        String root = null;
        for (int i = 0; i < source.length();){
            String source_temp = source.substring(i);
            int keyHead = source_temp.indexOf("<");
            int keyHead_ = source_temp.indexOf(">");
            if (source_temp.substring(keyHead + 1, keyHead + 2).equals("/")){
                if (source_temp.substring(keyHead + 2, keyHead_).equals(group)) {
                    group = null;
                    resultR.repeat(false);
                }
                i += keyHead_ + 1;
                continue;
            }
            if (source_temp.substring(keyHead_ + 1, keyHead_ + 2).equals("<")
                    && !source_temp.substring(keyHead_ + 2, keyHead_ + 3).equals("/")) {
                group = source_temp.substring(keyHead + 1, keyHead_);
                i += keyHead_ + 1;
                continue;
            } else {
                root = source_temp.substring(keyHead + 1, keyHead_);
                source_temp = source_temp.substring(keyHead_ + 1);
                int keyEnd = source_temp.indexOf("<");
                int keyEnd_ = source_temp.indexOf(">");
                if (group == null) {
                    resultR.setKeyValue(root, source_temp.substring(0, keyEnd));
                    i += keyEnd_ + keyHead_ + 2;
                    continue;
                }
                else{
                    resultR.setKeyValue(root, source_temp.substring(0, keyEnd), group);
                    resultR.repeat(true);
                    i += keyEnd_ + keyHead_ + 2;
                    continue;
                }
            }
        }
    }

    public twoArrayR getResult(){
        return this.resultR;
    }
}
