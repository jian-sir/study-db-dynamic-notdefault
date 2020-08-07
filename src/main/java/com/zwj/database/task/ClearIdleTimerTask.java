package com.zwj.database.task;

import com.zwj.database.pojo.DDSHolder;

import java.util.TimerTask;

/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
public class ClearIdleTimerTask extends TimerTask {
    @Override
    public void run() {
        DDSHolder.instance().clearIdleDDS();
    }
}
