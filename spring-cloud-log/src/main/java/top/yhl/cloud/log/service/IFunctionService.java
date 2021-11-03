package top.yhl.cloud.log.service;

public interface IFunctionService {
    public String apply(String functionName, String value);

    public boolean beforeFunction(String functionName);
}
