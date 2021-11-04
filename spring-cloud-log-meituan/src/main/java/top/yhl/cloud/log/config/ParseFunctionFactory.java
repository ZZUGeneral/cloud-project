package top.yhl.cloud.log.config;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.yhl.cloud.log.service.IParseFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseFunctionFactory {
    private Map<String, IParseFunction> functionMap;

    public ParseFunctionFactory(List<IParseFunction> parseFunctions) {
        if (CollectionUtils.isEmpty(parseFunctions)) {
            return;
        }
        functionMap = new HashMap<>();
        for (IParseFunction parseFunction : parseFunctions) {
            if (StringUtils.isEmpty(parseFunction.functionName())) {
                continue;
            }
            functionMap.put(parseFunction.functionName(), parseFunction);
        }
    }

    public IParseFunction getFunction(String functionName) {
        return functionMap.get(functionName);
    }

    public boolean isBeforeFunction(String functionName) {
        return functionMap.get(functionName) != null && functionMap.get(functionName).executeBefore();
    }
}
