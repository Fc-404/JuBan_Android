package com.fc.juban.tools;

public class randPhrase {
    static String[] adj = {
            "漂亮的",
            "可爱的",
            "娇小的",
            "愤怒的",
            "柔弱的",
            "有趣的",
            "开心的",
            "痛苦的",
            "迷人的",
            "清新的",
            "动人的",
            "深情的",
            "幽默的",
            "勤劳的"
    };
    static String[] noun = {
            "小猫咪",
            "小王八",
            "小狼狗",
            "小兔子",
            "大老虎",
            "蓝鲸鱼",
            "小崽子",
            "兔崽子",
            "小胖虎",
            "李翠花",
            "小秃子",
            "王富贵"
    };

    public static String mineSign(){
        return "我是一只" + adj[(int) (Math.random() * adj.length)]
                + noun[(int) (Math.random() * noun.length)];
    }
}
