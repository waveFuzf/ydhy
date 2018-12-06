package com.example.ydhy.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRcodeEncode {
    private static String imageExcelUrl="C:\\Users\\YFZX-FZF-1777\\Desktop\\下载文件";

    public static void main(String[] args) {
        try {
//            URL url=new URL("www.baidu.com");
            String path = imageExcelUrl;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN,0);
            BitMatrix bitMatrix = multiFormatWriter.encode("https://jingyan.baidu.com/article/9faa723192da96473d28cb5b.html",
                    BarcodeFormat.QR_CODE, 400, 400,hints);
            File file1 = new File(path,"test.jpg");
            ImageWrite.writeToFile(bitMatrix, "jpg", file1);
            System.out.println("二维码已生成！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
