package com.fykj.pds.work.backlogWork.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 16:02
 * Description:
 **/
public class BacklogWorkPageInVO implements JInputModel {

    // 状态
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
