package top.yhl.cloud.log.entity;

import java.util.Map;
import java.util.Stack;

public class LogRecordContext {
    private static final InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static Map<String, Object> getVariables() {
        return variableMapStack.get().pop();
    }

    public static void putEmptySpan() {
    }

    public static void clear() {
    }
}
