package com.yummyerp.cloud.utils;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP方法
 */
public class IpUtils {
    private static final Logger log = LoggerFactory.getLogger(IpUtils.class);
    
    private static Searcher searcher;
    
    static {
        try {
            ClassPathResource resource = new ClassPathResource("ip2region.xdb");
            byte[] cBuff = resource.getInputStream().readAllBytes();
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("初始化ip2region searcher失败", e);
        }
    }

    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址异常", e);
                }
            }
        }
        
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip != null && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }

    /**
     * 根据IP获取归属地
     *
     * @param ip IP地址
     * @return 归属地信息
     */
    public static String getRegionByIp(String ip) {
        if (StringUtils.isEmpty(ip) || "unknown".equals(ip)) {
            return "未知";
        }
        
        // 本地IP直接返回
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || ip.startsWith("192.168.") || ip.startsWith("10.")) {
            return "内网IP";
        }
        
        if (searcher == null) {
            return "未知";
        }
        
        try {
            String region = searcher.search(ip);
            if (StringUtils.isNotEmpty(region)) {
                // 处理返回格式：国家|区域|省份|市|ISP
                String[] parts = region.split("\\|");
                StringBuilder result = new StringBuilder();
                
                // 去除0和空值，组装有效的地理位置信息
                for (String part : parts) {
                    if (StringUtils.isNotEmpty(part) && !"0".equals(part)) {
                        if (result.length() > 0) {
                            result.append(" ");
                        }
                        result.append(part);
                    }
                }
                
                return result.length() > 0 ? result.toString() : "未知";
            }
        } catch (Exception e) {
            log.error("根据IP获取归属地失败，IP: {}", ip, e);
        }
        
        return "未知";
    }
    
    /**
     * 根据HttpServletRequest获取归属地
     *
     * @param request 请求对象
     * @return 归属地信息
     */
    public static String getRegion(HttpServletRequest request) {
        String ip = getIpAddr(request);
        return getRegionByIp(ip);
    }
} 