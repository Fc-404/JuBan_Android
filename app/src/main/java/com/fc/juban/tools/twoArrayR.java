package com.fc.juban.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 双层键值数据结构
 * Double KeyValue
 * Fc_404@20201012
 * 修改为可重复链@20201020
 * 此数据结构非常适用ini配置文件的存储
 */
public class twoArrayR {
    List<String> root = new ArrayList<String>();
    List<String> root2 = new ArrayList<String>();
    List<String> group = new ArrayList<String>();
    List<List> l = new ArrayList<List>();

    boolean repeat = false;

    //设置根键值
    public void setKeyValue(String key, String value) {
        for (int i = 0; ; i++) {
            if (root.indexOf(key + '_' + i) >= 0)
                continue;
            root.add(key + '_' + i);
            root2.add(value);
            break;
        }
    }

    //设置带组键值
    public void setKeyValue(String key, String value, String groupN) {
        int indexGroup;
        if (repeat) {
            if (groupLength() <= 0){
                group.add(groupN + '_' + 0);
                l.add(new ArrayList<String>());
                l.add(new ArrayList<String>());
            }
            indexGroup = group.indexOf(groupN + "_" + (groupLength(groupN) - 1));
        } else {
            for (int i = 0; ; i++) {
                if (group.indexOf(groupN + '_' + i) >= 0)
                    continue;
                group.add(groupN + '_' + i);
                l.add(new ArrayList<String>());
                l.add(new ArrayList<String>());
                indexGroup = group.indexOf(groupN + '_' + i);
                break;
            }
        }

        for (int i = 0; ; i++) {
            if (l.get(indexGroup * 2).indexOf(key + '_' + i) >= 0)
                continue;
            l.get(indexGroup * 2).add(key + '_' + i);
            l.get(indexGroup * 2 + 1).add(value);
            break;
        }
    }

    //设置是否重复
    public void repeat(boolean is){
        this.repeat = is;
    }

    //获取根键值
    public String getKeyValue(String key){
        return getKeyValue(key, keyLength() - 1);
    }
    public String getKeyValue(String key, int index) {
        int indexRoot = root.indexOf(key + '_' + index);
        if (indexRoot == -1)
            return null;
        return root2.get(indexRoot);
    }

    //获取带组键值
    public String getKeyValue(String key, String group){
        return getKeyValue(key, group, groupLength(group) - 1);
    }
    public String getKeyValue(String key, String group, int indexG){
        return getKeyValue(key, group, keyLength(key, group, indexG) - 1, indexG);
    }
    public String getKeyValue(String key, String group, int indexK, int indexG) {
        int indexGroup = this.group.indexOf(group + '_' + indexG);
        if (indexGroup == -1)
            return null;
        int indexRoot = l.get(indexGroup * 2).indexOf(key + '_' + indexK);
        if (indexRoot == -1)
            return null;
        return (String) l.get(indexGroup * 2 + 1).get(indexRoot);
    }
    public String[] getKeyValueIndex(int keyIndex, String group){
        int indexGroup = this.group.indexOf(group + '_' + (groupLength(group) - 1));
        if (indexGroup < 0)
            return  null;
        return new String[]{
                l.get(indexGroup * 2).get(keyIndex).toString(),
                l.get(indexGroup * 2 + 1).get(keyIndex).toString()};
    }

    //获取键长
    public int keyLength(){
        return root.size();
    }
    public int keyLength(String key){
        int i = 0;
        for (int n = 0; n < root.size(); n++){
            if (root.get(n).substring(0, root.get(n).indexOf("_")) == key)
                i++;
        }
        return i;
    }
    public int keyLength(String key, String group){
        return keyLength(key, group, groupLength(group) - 1);
    }
    public int keyLength(String key, String group, int indexGroup){
        int i = 0;
        int groupI = this.group.indexOf(group + "_" + indexGroup);
        if (groupI < 0)
            return i;
        for (int n = 0; n < l.get(groupI * 2).size(); n++){
            if (l.get(groupI * 2).get(n).toString().substring(
                    0,
                    l.get(groupI * 2).get(n).toString().indexOf("_")
            ).equals(key))
                i++;
        }
        return i;
    }

    //获取组长
    public int groupLength() {
        return group.size();
    }
    public int groupLength(String str){
        int i = 0;
        for (int n = 0; n < group.size(); n++){
            if (group.get(n).substring(0, group.get(n).indexOf("_")).equals(str))
                i++;
        }
        return i;
    }

    //获取组链
    public List<String> getGroupList() {
        return group;
    }

    //获取根链
    public List<String> getRootList() {
        return root;
    }

    public List<String> getRootList(String group) {
        return l.get(this.group.indexOf(group + '_' + (groupLength(group) - 1)) * 2);
    }
}
