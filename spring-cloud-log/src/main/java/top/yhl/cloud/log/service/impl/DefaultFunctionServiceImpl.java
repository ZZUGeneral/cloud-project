package top.yhl.cloud.log.service.impl;

import top.yhl.cloud.log.config.ParseFunctionFactory;
import top.yhl.cloud.log.service.IFunctionService;
import top.yhl.cloud.log.service.IParseFunction;

public class DefaultFunctionServiceImpl implements IFunctionService {

    private final ParseFunctionFactory parseFunctionFactory;

    public DefaultFunctionServiceImpl(ParseFunctionFactory parseFunctionFactory) {
        this.parseFunctionFactory = parseFunctionFactory;
    }

    @Override
    public String apply(String functionName, String value) {
        IParseFunction function = parseFunctionFactory.getFunction(functionName);
        if (function == null) {
            return value;
        }
        return function.apply(value);
    }

    @Override
    public boolean beforeFunction(String functionName) {
        return parseFunctionFactory.isBeforeFunction(functionName);
    }
}
