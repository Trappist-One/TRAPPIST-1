package com.t1.log.service;

import com.t1.log.model.Audit;

/**
 * 审计日志接口
 *
 * @author Bruce Lee(copy)
 * @date 2020/2/3
 * <p>
 */
public interface IAuditService {
    void save(Audit audit);
}
