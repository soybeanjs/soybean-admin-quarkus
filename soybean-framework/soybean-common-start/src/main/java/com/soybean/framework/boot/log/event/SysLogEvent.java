package com.soybean.framework.boot.log.event;

import com.soybean.framework.boot.log.OptLogDTO;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 * @author wenxina
 * @since 2019-07-01 15:13
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(OptLogDTO source) {
        super(source);
    }
}
