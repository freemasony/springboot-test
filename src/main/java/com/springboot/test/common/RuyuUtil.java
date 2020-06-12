package com.springboot.test.common;


import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.servlet.http.HttpServletResponse;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhoujian
 * @date 2019/10/25
 */
public class RuyuUtil {




    /*public static void main(String[] args) throws IOException {
        BufferedImage rgbImage = ImageIO.read(new File("F://testImage//sfzbeimian.jpg"));//读取rbg图片       
        BufferedImage cmykImage = null;
        //ICC_Profile.getInstance("profiles/ISOcoated_v2_300_eci.icc");
        ColorSpace cpace = new ICC_ColorSpace(ICC_Profile.getInstance("ISOcoated_v2_300_eci.icc"));
        //ColorSpace cpace = new ICC_ColorSpace(ICC_Profile.getInstance(RuyuUtil.class.getClassLoader().getResourceAsStream("ISOcoated_v2_300_eci.icc")));
        ColorConvertOp op = new ColorConvertOp(rgbImage.getColorModel().getColorSpace(), cpace, null);
        cmykImage = op.filter(rgbImage, null);
        JAI.create("filestore", cmykImage, "F:/TEMP/CMYK_IMAGE.TIF", "TIFF");//生成cmyk格式图片
    }*/

    public void getImagePixel(String image ,HttpServletResponse response) throws Exception {
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        OutputStream out =response.getOutputStream();
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //很重要的方法---将图片的RGB色域空间转化为CMYK色域空间
        BufferedImage cmykImg = changeColorSpace(bi);
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                int pixel = bi.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

                float[] abc = {rgb[0],rgb[1],rgb[2]};//获取RGB色域空间数组
                bi.setRGB(123, 123, 123);

                //将此像素RGB色值转化为cmyk色值
                float[] cmykRes = cmykImg.getColorModel().getColorSpace().fromRGB(abc);
                //把cmyk色值添加到色域空间
                cmykImg.getColorModel().getColorSpace().toRGB(cmykRes);
            }
        }
        //生成这个cmyk tiff 文件
        JAI.create("encode", cmykImg, out,"PNG", null);
        out.flush();
    }

    private BufferedImage changeColorSpace(BufferedImage targetImage) throws IOException {

        //Iss空间管理配置文件 这个文件我是放在项目resouce里面的 后面会贴上
        final ICC_Profile ip = ICC_Profile.getInstance(getClass().getResource("/ISOcoated_v2_300_eci.icc").getPath());

        //转化色值空间
        final ColorConvertOp cco = new ColorConvertOp(targetImage.getColorModel().getColorSpace(), new ICC_ColorSpace(ip), null);
        BufferedImage cmykImage = cco.filter(targetImage, null);
        ColorModel c = cmykImage.getColorModel();
        int ty  = cmykImage.getColorModel().getColorSpace().getType();//为了查看这个图片是不是CMYK的
        return cmykImage;
    }



}
