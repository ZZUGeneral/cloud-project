package top.yhl.cloud.file.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.*;

public class PdfUtil {
    public static byte[] drawText(InputStream is, String logoText) {
        return drawText(is, logoText, 0.2f);
    }

    public static byte[] drawText(InputStream is, String logoText, float alpha) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            PdfReader reader = new PdfReader(is);
            PdfStamper stamper = new PdfStamper(reader, os);
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte content;
            BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            int textH = 26, textW = 110;
            PdfGState state = new PdfGState();
            Rectangle page = null;

            for (int i = 1; i < total; i++) {
                page = reader.getPageSizeWithRotation(i);
                // 在内容上方添加水印
                content = stamper.getOverContent(i);
                state.setFillOpacity(alpha);
                content.beginText();
                content.setColorFill(BaseColor.LIGHT_GRAY);
                content.setFontAndSize(font, 20);
                int interval = 0;
                for (int height = interval + textH; height < page.getHeight(); height = height + textH * 5) {
                    for (int width = interval + textW; width < page.getWidth(); width = width + textW + 30) {
                        content.showTextAligned(Element.ALIGN_CENTER, logoText, width - textW + 30, height - textH, 30);
                    }
                }
                content.endText();
            }
            stamper.close();
            return os.toByteArray();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
