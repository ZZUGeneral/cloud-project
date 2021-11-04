package top.yhl.cloud.log.service.impl;

import top.yhl.cloud.log.entity.Operator;
import top.yhl.cloud.log.service.IOperatorGetService;
import top.yhl.cloud.log.util.UserUtils;

import java.util.Optional;

public class DefaultOperatorGetServiceImpl implements IOperatorGetService {

    @Override
    public Operator getUser() {
        //UserUtils 是获取用户上下文的方法
        return Optional.ofNullable(UserUtils.getUser())
                .map(a -> new Operator(a.getName(), a.getLogin()))
                .orElseThrow(()->new IllegalArgumentException("user is null"));

    }
}
