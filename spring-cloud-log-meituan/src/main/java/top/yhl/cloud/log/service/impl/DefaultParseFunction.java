package top.yhl.cloud.log.service.impl;

import top.yhl.cloud.log.service.IParseFunction;

public class DefaultParseFunction implements IParseFunction {
    @Override
    public String functionName() {
        return this.getClass().getName();
    }

    @Override
    public String apply(String value) {
        return null;
    }
}
