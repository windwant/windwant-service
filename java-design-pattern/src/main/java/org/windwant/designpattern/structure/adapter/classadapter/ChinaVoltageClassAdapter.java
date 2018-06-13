package org.windwant.designpattern.structure.adapter.classadapter;

import org.windwant.designpattern.structure.adapter.ChinaVoltage;
import org.windwant.designpattern.structure.adapter.PowerVoltage;

/**
 * ������
 * Created by windwant on 2016/9/21.
 */
public class ChinaVoltageClassAdapter extends ChinaVoltage implements PowerVoltage {

    /* ���䷽�� ʹ�ñ�׼�ӿڷ����ڲ����� ChinaVoltage chong()���� */
    public void givePower() {
        super.chong();
    }
}
