package com.fc.juban.tools;

public class xml2ini {
    private twoArray result;

    public xml2ini(String source){
        this.result = new twoArray();
        if (source.isEmpty())
            return;
        //
        String group = null;
        String root = null;
        for (int i = 0; i < source.length();){
            String source_temp = source.substring(i);
            int keyHead = source_temp.indexOf("<");
            int keyHead_ = source_temp.indexOf(">");
            if (source_temp.substring(keyHead + 1, keyHead + 2).equals("/")){
                if (source_temp.substring(keyHead + 2, keyHead_).equals(group))
                    group = null;
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
                    result.setKeyValue(root, source_temp.substring(0, keyEnd));
                    i += keyEnd_ + keyHead_ + 2;
                    continue;
                }
                else{
                    result.setKeyValue(root, source_temp.substring(0, keyEnd), group);
                    i += keyEnd_ + keyHead_ + 2;
                    continue;
                }
            }
        }
    }

    public twoArray getResult(){
        return this.result;
    }
}
