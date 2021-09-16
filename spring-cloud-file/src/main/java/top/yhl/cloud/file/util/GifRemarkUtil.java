package top.yhl.cloud.file.util;

import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.util.ImageReadHelper;
import com.alibaba.simpleimage.util.ImageWriteHelper;
import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class GifRemarkUtil {
    /**
     * @param logoText 水印文字
     * @throws Exception
     */
    public static byte[] drawText(InputStream gifInputStream, String logoText) {
        return drawText(gifInputStream, logoText, 0, 0, 0, null);
    }

    /**
     * @param logoText 水印文字
     * @param alpha    透明度
     * @throws Exception
     */
    public static byte[] drawText(InputStream gifInputStream, String logoText, float alpha) {
        return drawText(gifInputStream, logoText, alpha, 0, 0, null);
    }

    /**
     * @param logoText       水印文字
     * @param alpha          透明度
     * @param positionWidth  水印位置横坐标
     * @param positionHeight 水印文字纵坐标
     * @throws Exception
     */
    public static byte[] drawText(InputStream gifInputStream, String logoText, float alpha, int positionWidth, int positionHeight) {
        return drawText(gifInputStream, logoText, alpha, positionWidth, positionHeight, null);
    }

    /**
     * @param logoText       水印文字
     * @param alpha          透明度
     * @param positionWidth  水印位置横坐标
     * @param positionHeight 水印文字纵坐标
     * @param color          水印颜色
     * @throws Exception
     */
    public static byte[] drawText(@NotNull InputStream gifInputStream, @NotNull String logoText, float alpha, int positionWidth, int positionHeight, Color color) {
        if (null == color) {
            color = Color.black;
        }
        if (alpha == 0) {
            alpha = 0.5f;
        }
        if (positionWidth == 0) {
            positionWidth = 100;
        }
        if (positionHeight == 0) {
            positionHeight = 100;
        }
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = gifInputStream;
            if (!(gifInputStream instanceof BufferedInputStream)) {
                is = new BufferedInputStream(gifInputStream);
            }
            ImageWrapper imageWrap = ImageReadHelper.read(is);
            int width = imageWrap.getWidth(), height = imageWrap.getHeight();
            Font font = new Font("楷体", Font.BOLD, (int) (width * 0.07));
            for (int i = 0; i < imageWrap.getNumOfImages(); i++) {
                BufferedImage bufferedImage = imageWrap.getAsBufferedImage(i);
                Graphics2D g = bufferedImage.createGraphics();
                g.drawImage(bufferedImage.getScaledInstance(bufferedImage.getWidth(null), bufferedImage.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
                g.translate(bufferedImage.getWidth() - width, bufferedImage.getHeight() - height);
                g.setColor(color);
                g.setFont(font);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
                g.drawString(logoText, positionWidth, positionHeight);
                g.dispose();
                imageWrap.setImage(i, bufferedImage);
            }

            os = new ByteArrayOutputStream();
            ImageWriteHelper.writeGIF(imageWrap, os, WriteParameter.QuantAlgorithm.MedianCut);
            return os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
