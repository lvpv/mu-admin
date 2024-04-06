package com.lvpb.mu.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.lvpb.mu.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 19:56
 * @description IpUtils
 */

@Slf4j
public class IpUtils {

    private final static Searcher SEARCHER;

    private final static String LOCAL_IP = "127.0.0.1";

    private final static String UN_KNOW_IP = "unknown";

    private final static String IPV6_LOCAL = "0:0:0:0:0:0:0:1";

    static {
        // ip地址库
        //PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // Resource resource = resolver.getResource("classpath:ip2region.xdb");

        ClassPathResource resource = new ClassPathResource("ip2region.xdb");
        try {
            SEARCHER = Searcher.newWithBuffer(resource.getStream().readAllBytes());
        } catch (Exception e) {
            log.error("ip2region 初始化异常", e);
            throw new BusinessException("ip2region初始化异常", e);
        }
    }

    public static String getAddressByIp(String ip) {
        // 内网
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String address = SEARCHER.search(ip);
            return address.replace("0|", "").replace("|0", "");
        } catch (Exception e) {
            log.error("根据IP获取地址异常 {}", ip);
            return "未知";
        }
    }

    /**
     * 获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UN_KNOW_IP;
        }
        String ip = request.getHeader("X-Original-Forwarded-For");
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isBlank(ip) || UN_KNOW_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return IPV6_LOCAL.equals(ip) ? LOCAL_IP : getMultistageReverseProxyIp(ip);
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param ip IP地址
     */
    private static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || LOCAL_IP.equals(ip);
    }

    /**
     * 检查是否为内部IP地址
     *
     * @param addr byte地址
     */
    private static boolean internalIp(byte[] addr) {
        if (addr == null || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte section1 = 0x0A;
        // 172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                return true;
            case section2:
                if (b1 >= section3 && b1 <= section4) {
                    return true;
                }
            case section5:
                if (b1 == section6) {
                    return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    private static byte[] textToNumericFormatV4(String text) {
        if (StrUtil.isBlank(text)) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
            return LOCAL_IP;
        }
    }

    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignored) {
            return "未知";
        }
    }

    /**
     * 从反向代理中，获得第一个非 unknown IP地址
     */
    private static String getMultistageReverseProxyIp(String ip) {
        // 反向代理检测
        if (ip.indexOf(StrUtil.COMMA) > 0) {
            final String[] ips = ip.trim().split(StrUtil.COMMA);
            for (String sub : ips) {
                if (!UN_KNOW_IP.equalsIgnoreCase(sub)) {
                    ip = sub;
                    break;
                }
            }
        }
        return ip;
    }
}
