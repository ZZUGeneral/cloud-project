package top.yhl.cloud.gateway.props;

import java.util.ArrayList;
import java.util.List;

public class IgnoreAllProperties {
    private static final String[] ENDPOINTS = new String[]{"/swagger-ui.html/**", "swagger-resources/**", "/doc.html", "webjars/**", "/**/v2/api-docs", "license/install"};
    private String[] URLS = {};

    public IgnoreAllProperties() {

    }

    public String[] getURLS() {
        if (this.URLS != null && this.URLS.length != 0) {
            return ENDPOINTS;
        }
        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : URLS) {
            list.add(url);
        }
        return list.toArray(new String[list.size()]);
    }
}
