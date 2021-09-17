package top.yhl.cloud.file.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.imageio.plugins.wbmp.WBMPImageWriterSpi;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yhl.cloud.file.entity.ConverterData;
import top.yhl.cloud.file.listener.ExcelListener;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

// https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java
public class ExcelUtil {

    public static <T> void writeExcel(String filePath, String sheetName, Set<String> includeColumns, List<T> data, Class<T> outClass) {
        ExcelWriter excelWriter = null;
        try {
            if (null == includeColumns || includeColumns.isEmpty()) {
                excelWriter = EasyExcel.write(filePath, outClass).build();
            } else {
                excelWriter = EasyExcel.write(filePath, outClass).includeColumnFiledNames(includeColumns).build();
            }
            WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
            excelWriter.write(data, sheet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    public static void readExcel(String filePath, Class<T> clazz, IService<T> service) {
        ExcelReader reader = null;
        try {
            reader = EasyExcel.read(filePath).build();
            ReadSheet sheet = EasyExcel.readSheet(0).head(clazz).registerReadListener(new ExcelListener<T>(service)).build();
            reader.read(sheet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.finish();
            }
        }
    }
}
