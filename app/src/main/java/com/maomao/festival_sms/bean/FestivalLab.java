package com.maomao.festival_sms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mao on 2015/10/12.
 */
public class FestivalLab {

    private static FestivalLab mInstance ;

    private List<Festival> mFestival = new ArrayList<Festival>();
    private List<Msg> mMsg = new ArrayList<Msg>();

    private  FestivalLab(){
        mFestival.add(new Festival(1,"国庆节"));
        mFestival.add(new Festival(2,"中秋节"));
        mFestival.add(new Festival(3,"元旦节"));
        mFestival.add(new Festival(4,"春节"));
        mFestival.add(new Festival(5,"端午节"));
        mFestival.add(new Festival(6,"七夕节"));
        mFestival.add(new Festival(7,"圣诞节"));
        mFestival.add(new Festival(8,"儿童节"));

        mMsg.add(new Msg(1,1,"为了答谢一直以来深情支持我的粉丝，我特地准备了一首歌曲献给大家，这是一首说唱风格的作品希望你们跟我一起唱歌词是：节日快乐。"));
        mMsg.add(new Msg(2,1,"万紫千红迎国庆，片片红叶舞秋风！举国上下齐欢畅，家和国盛万事兴！愿这盛大的节日带给你永远的幸运"));
        mMsg.add(new Msg(3,1,"当你看到我给你的这份祝福，清将头用力撞墙，看到没有，你眼前无数的星星是我无限的祝福，国庆快乐！"));
        mMsg.add(new Msg(4,1,"当你看到这条信息的时候，我正在为你祝福，希望你能够在这金秋十月度过最开心的节日，让一切烦恼都离你远去。"));
        mMsg.add(new Msg(5,1,"爱加爱等于非常的爱，爱减爱等于爱的起点，爱乘爱等于无限的爱，爱除爱等于爱的唯一。亲爱的祝你国庆节快乐！"));
        mMsg.add(new Msg(6,1,"国庆快乐，加班快乐！嘻嘻，加班的日子天天快乐，别在意节日怎么过！但我的祝福一直随你一起度过！愿你事业顺利，合家幸福！"));
        mMsg.add(new Msg(7,1,"国庆中秋，双喜临门！无论你是打算出游还是在家休息，别忘了给亲人朋友捎去一份关怀和祝福。"));
        mMsg.add(new Msg(8,1,"摘一千颗星星照亮您的前程;种一千朵玫瑰陶醉您的心情;折一千只纸鹤放飞您的欢乐;找一千种理由让您幸福安宁;说一千个句子祝您国庆团圆喜庆！"));
        mMsg.add(new Msg(9,1,"一表人才、一鸣惊人、一呼百应、一举两得、一马平川、一鼓作气、一锤定音、一本万利、一帆风顺、一飞冲天：十一快乐！"));
    }

    public List<Msg> getMsgsByFestivalId(int fesId){
        List<Msg> msgs = new ArrayList<Msg>();

        for (Msg msg : mMsg){
            if (msg.getFestivalId() == fesId){
                msgs.add(msg);
            }
        }
        return msgs;
    }

    public Msg getMsgByid(int id ){
        for (Msg msg :mMsg){
            if (msg.getId() == id){
                return msg;
            }
        }
        return null;
    }

    public List<Festival> getFestival(){
        return new ArrayList<Festival>(mFestival);
    }

    public Festival getFestivalsById(int id){
        for (Festival festival:  mFestival) {
            if (festival.getId() == id){
                return festival;
            }
        }
        return null;
    }

    public static FestivalLab getInstance(){
        if (mInstance == null){
            synchronized (FestivalLab.class){
                if (mInstance == null){
                    mInstance = new FestivalLab();
                }
            }
        }

        return mInstance;
    }
}
