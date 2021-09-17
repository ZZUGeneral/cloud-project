package top.yhl.cloud.file.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author General
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {
    List<T> list = new ArrayList<>();
    private static final int BATCH_COUNT = 100;
    private IService<T> dao;

    public ExcelListener(IService<T> dao) {
        this.dao = dao;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    public void saveData() {
        dao.saveBatch(list);
    }

}
