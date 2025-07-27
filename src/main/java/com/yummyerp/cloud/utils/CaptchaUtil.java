package com.yummyerp.cloud.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码生成工具类
 */
public class CaptchaUtil {

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int FONT_SIZE = 20;
    private static final int CODE_LENGTH = 4;

    /**
     * 生成验证码图片和文本
     */
    public static CaptchaResult generateCaptcha() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        
        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // 生成随机验证码
        String code = generateRandomCode();
        
        // 设置字体
        g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        
        Random random = new Random();
        
        // 绘制验证码字符
        for (int i = 0; i < code.length(); i++) {
            // 随机颜色
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            
            // 随机位置
            int x = 20 + i * 20;
            int y = 20 + random.nextInt(10);
            
            // 随机旋转
            g.rotate(Math.toRadians(random.nextInt(30) - 15), x, y);
            g.drawString(String.valueOf(code.charAt(i)), x, y);
            g.rotate(-Math.toRadians(random.nextInt(30) - 15), x, y);
        }
        
        // 添加干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT), 
                      random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }
        
        g.dispose();
        
        // 转换为Base64
        String base64Image = imageToBase64(image);
        
        return new CaptchaResult(code, base64Image);
    }
    
    /**
     * 生成随机验证码文本
     */
    private static String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return code.toString();
    }
    
    /**
     * 图片转Base64
     */
    private static String imageToBase64(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("验证码图片生成失败", e);
        }
    }
    
    /**
     * 验证码结果类
     */
    public static class CaptchaResult {
        private final String code;
        private final String image;
        
        public CaptchaResult(String code, String image) {
            this.code = code;
            this.image = image;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getImage() {
            return image;
        }
    }
}