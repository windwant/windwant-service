package org.windwant.designpattern.structure.adapter.objectadapter;

import org.windwant.designpattern.structure.adapter.ChinaVoltage;
import org.windwant.designpattern.structure.adapter.PowerVoltage;

/**
 * Created by aayongche on 2016/9/21.
 */
public class ChinaVoltageObjectAdapter implements PowerVoltage {

    private ChinaVoltage chinaVoltage;

    public ChinaVoltageObjectAdapter(ChinaVoltage chinaVoltage){
        this.chinaVoltage = chinaVoltage;
    }

    public void givePower() {
        chinaVoltage.chong();
    }
}
