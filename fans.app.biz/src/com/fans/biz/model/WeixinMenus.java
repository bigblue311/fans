package com.fans.biz.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.JsonTools;

public class WeixinMenus {
    
    private List<WeixinMenuVO> button;

    public List<WeixinMenuVO> getButton() {
        return button;
    }

    public void setButton(List<WeixinMenuVO> button) {
        this.button = button;
    }
    
    public static String getWzMenuJson(){
        WeixinMenuVO btn1 = new WeixinMenuVO();
        btn1.setName("微团官网");
        btn1.setType("view");
        btn1.setUrl("http://wz.wetuan.com/?_setOpenId=true&_goWetuan=1");
        
        WeixinMenuVO btn2 = new WeixinMenuVO();
        btn2.setName("用户中心");
        
        WeixinMenuVO btn21 = new WeixinMenuVO();
        btn21.setName("用户中心");
        btn21.setType("view");
        btn21.setUrl("http://wz.wetuan.com/?_setOpenId=true&_goWetuan=2");
        
        WeixinMenuVO btn22 = new WeixinMenuVO();
        btn22.setName("我要加入");
        btn22.setType("view");
        btn22.setUrl("http://wz.wetuan.com/?_setOpenId=true&_goWetuan=3");
        
        List<WeixinMenuVO> btn2Sub = Lists.newArrayList();
        btn2Sub.add(btn21);
        btn2Sub.add(btn22);
        btn2.setSub_button(btn2Sub);
        
        WeixinMenuVO btn3 = new WeixinMenuVO();
        btn3.setName("爆粉加人");
        
        WeixinMenuVO btn31 = new WeixinMenuVO();
        btn31.setName("躺着加粉");
        btn31.setType("view");
        btn31.setUrl("http://wz.wetuan.com/index.htm?_setOpenId=true");
        
        WeixinMenuVO btn32 = new WeixinMenuVO();
        btn32.setName("新手引导");
        btn32.setType("view");
        btn32.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzOTE2MzQyNQ==&mid=402379059&idx=1&sn=f104591f94ca2e59dd055e3149e03339&scene=18#wechat_redirect");
        
        WeixinMenuVO btn33 = new WeixinMenuVO();
        btn33.setName("微团必看");
        btn33.setType("view");
        btn33.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzOTE2MzQyNQ==&mid=403217830&idx=1&sn=926a8e3029701fea6b0136f2fa14b4cf#rd");
        
        List<WeixinMenuVO> btn3Sub = Lists.newArrayList();
        btn3Sub.add(btn31);
        btn3Sub.add(btn32);
        btn3Sub.add(btn33);
        btn3.setSub_button(btn3Sub);
        
        List<WeixinMenuVO> list = Lists.newArrayList();
        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        
        WeixinMenus menus = new WeixinMenus();
        menus.setButton(list);
        
        return JsonTools.toJson(menus);
    }
    
    public static String getWtMenuJson(){
        WeixinMenuVO btn1 = new WeixinMenuVO();
        btn1.setName("微团官网");
        btn1.setType("view");
        btn1.setUrl("http://wt.wetuan.com/?_setOpenId=true&_goWetuan=1");
        
        WeixinMenuVO btn2 = new WeixinMenuVO();
        btn2.setName("用户中心");
        
        WeixinMenuVO btn21 = new WeixinMenuVO();
        btn21.setName("用户中心");
        btn21.setType("view");
        btn21.setUrl("http://wt.wetuan.com/?_setOpenId=true&_goWetuan=2");
        
        WeixinMenuVO btn22 = new WeixinMenuVO();
        btn22.setName("我要加入");
        btn22.setType("view");
        btn22.setUrl("http://wt.wetuan.com/?_setOpenId=true&_goWetuan=3");
        
        WeixinMenuVO btn23 = new WeixinMenuVO();
        btn23.setName("用户绑定");
        btn23.setType("view");
        btn23.setUrl("http://wt.wetuan.com/?_setOpenId=true&_goWetuan=4");
        
        List<WeixinMenuVO> btn2Sub = Lists.newArrayList();
        btn2Sub.add(btn21);
        btn2Sub.add(btn22);
        btn2Sub.add(btn23);
        btn2.setSub_button(btn2Sub);
        
        WeixinMenuVO btn3 = new WeixinMenuVO();
        btn3.setName("爆粉加人");
        
        WeixinMenuVO btn31 = new WeixinMenuVO();
        btn31.setName("躺着加粉");
        btn31.setType("view");
        btn31.setUrl("http://wt.wetuan.com/index.htm?_setOpenId=true");
        
        WeixinMenuVO btn32 = new WeixinMenuVO();
        btn32.setName("新手引导");
        btn32.setType("view");
        btn32.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzOTE2MzQyNQ==&mid=402379059&idx=1&sn=f104591f94ca2e59dd055e3149e03339&scene=18#wechat_redirect");
        
        WeixinMenuVO btn33 = new WeixinMenuVO();
        btn33.setName("微团必看");
        btn33.setType("view");
        btn33.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzOTE2MzQyNQ==&mid=403217830&idx=1&sn=926a8e3029701fea6b0136f2fa14b4cf#rd");
        
        List<WeixinMenuVO> btn3Sub = Lists.newArrayList();
        btn3Sub.add(btn31);
        btn3Sub.add(btn32);
        btn3Sub.add(btn33);
        btn3.setSub_button(btn3Sub);
        
        List<WeixinMenuVO> list = Lists.newArrayList();
        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        
        WeixinMenus menus = new WeixinMenus();
        menus.setButton(list);
        
        return JsonTools.toJson(menus);
    }
    
    public static String getTestMenuJson(){
        WeixinMenuVO btn1 = new WeixinMenuVO();
        btn1.setName("微团官网");
        btn1.setType("view");
        btn1.setUrl("http://www.17yunshang.com/test/?_setOpenId=true&_goWetuan=1");
        
        WeixinMenuVO btn2 = new WeixinMenuVO();
        btn2.setName("用户中心");
        
        WeixinMenuVO btn21 = new WeixinMenuVO();
        btn21.setName("用户中心");
        btn21.setType("view");
        btn21.setUrl("http://www.17yunshang.com/test/?_setOpenId=true&_goWetuan=2");
        
        WeixinMenuVO btn22 = new WeixinMenuVO();
        btn22.setName("我要加入");
        btn22.setType("view");
        btn22.setUrl("http://www.17yunshang.com/test/?_setOpenId=true&_goWetuan=3");
        
        List<WeixinMenuVO> btn2Sub = Lists.newArrayList();
        btn2Sub.add(btn21);
        btn2Sub.add(btn22);
        btn2.setSub_button(btn2Sub);
        
        WeixinMenuVO btn3 = new WeixinMenuVO();
        btn3.setName("爆粉加人");
        
        WeixinMenuVO btn31 = new WeixinMenuVO();
        btn31.setName("躺着加粉");
        btn31.setType("view");
        btn31.setUrl("http://www.17yunshang.com/test/index.htm?_setOpenId=true");
        
        WeixinMenuVO btn32 = new WeixinMenuVO();
        btn32.setName("新手引导");
        btn32.setType("view");
        btn32.setUrl("http://mp.weixin.qq.com/s?__biz=MzIzOTE2MzQyNQ==&mid=402379059&idx=1&sn=f104591f94ca2e59dd055e3149e03339&scene=18#wechat_redirect");
        
        List<WeixinMenuVO> btn3Sub = Lists.newArrayList();
        btn3Sub.add(btn31);
        btn3Sub.add(btn32);
        btn3.setSub_button(btn3Sub);
        
        List<WeixinMenuVO> list = Lists.newArrayList();
        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        
        WeixinMenus menus = new WeixinMenus();
        menus.setButton(list);
        
        return JsonTools.toJson(menus);
    }
    
    public static void main(String[] args){
        System.out.println(WeixinMenus.getWzMenuJson());
        System.out.println(WeixinMenus.getWtMenuJson());
    }
}
