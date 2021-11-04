package top.yhl.cloud.log.service;

import top.yhl.cloud.log.entity.Operator;

public interface IOperatorGetService {
    /**
     * 可以在里面外部的获取当前登陆的用户，比如 UserContext.getCurrentUser()
     *
     * @return 转换成Operator返回
     */
    Operator getUser();
}
