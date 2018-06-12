package org.windwant.designpattern.structure.adapter.classadapter;

import org.windwant.designpattern.structure.adapter.ChinaVoltage;
import org.windwant.designpattern.structure.adapter.PowerVoltage;

/**
 * 适配类
 * Created by aayongche on 2016/9/21.
 */
public class ChinaVoltageClassAdapter extends ChinaVoltage implements PowerVoltage {

    /* 适配方法 使用标准接口方法内部调用 ChinaVoltage chong()方法 */
    public void givePower() {
        super.chong();
    }
}
