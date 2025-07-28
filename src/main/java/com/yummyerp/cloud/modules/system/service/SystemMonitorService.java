package com.yummyerp.cloud.modules.system.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统监控服务
 * @author 周欢
 * @since 2025-07-28
 */
@Service
public class SystemMonitorService {

    /**
     * 获取系统基本信息
     */
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();
        
        // 操作系统信息
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        systemInfo.put("os_name", System.getProperty("os.name"));
        systemInfo.put("os_arch", System.getProperty("os.arch"));
        systemInfo.put("os_version", System.getProperty("os.version"));
        systemInfo.put("processors", osBean.getAvailableProcessors());
        
        // Java运行时信息
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        systemInfo.put("java_version", System.getProperty("java.version"));
        systemInfo.put("java_vendor", System.getProperty("java.vendor"));
        systemInfo.put("java_home", System.getProperty("java.home"));
        systemInfo.put("start_time", runtimeBean.getStartTime());
        systemInfo.put("uptime", runtimeBean.getUptime());
        
        // 系统负载（如果支持）
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = 
                (com.sun.management.OperatingSystemMXBean) osBean;
            systemInfo.put("system_cpu_load", String.format("%.2f", sunOsBean.getSystemCpuLoad() * 100));
            systemInfo.put("process_cpu_load", String.format("%.2f", sunOsBean.getProcessCpuLoad() * 100));
            systemInfo.put("total_physical_memory", formatBytes(sunOsBean.getTotalPhysicalMemorySize()));
            systemInfo.put("free_physical_memory", formatBytes(sunOsBean.getFreePhysicalMemorySize()));
            systemInfo.put("used_physical_memory", formatBytes(sunOsBean.getTotalPhysicalMemorySize() - sunOsBean.getFreePhysicalMemorySize()));
        }
        
        return systemInfo;
    }

    /**
     * 获取JVM内存信息
     */
    public Map<String, Object> getJvmMemoryInfo() {
        Map<String, Object> jvmInfo = new HashMap<>();
        
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        
        // 堆内存
        MemoryUsage heapMemory = memoryBean.getHeapMemoryUsage();
        Map<String, Object> heap = new HashMap<>();
        heap.put("init", formatBytes(heapMemory.getInit()));
        heap.put("used", formatBytes(heapMemory.getUsed()));
        heap.put("committed", formatBytes(heapMemory.getCommitted()));
        heap.put("max", formatBytes(heapMemory.getMax()));
        heap.put("usage_percent", String.format("%.2f", (double) heapMemory.getUsed() / heapMemory.getMax() * 100));
        jvmInfo.put("heap_memory", heap);
        
        // 非堆内存
        MemoryUsage nonHeapMemory = memoryBean.getNonHeapMemoryUsage();
        Map<String, Object> nonHeap = new HashMap<>();
        nonHeap.put("init", formatBytes(nonHeapMemory.getInit()));
        nonHeap.put("used", formatBytes(nonHeapMemory.getUsed()));
        nonHeap.put("committed", formatBytes(nonHeapMemory.getCommitted()));
        nonHeap.put("max", nonHeapMemory.getMax() == -1 ? "无限制" : formatBytes(nonHeapMemory.getMax()));
        jvmInfo.put("non_heap_memory", nonHeap);
        
        // 内存池信息
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            MemoryUsage usage = pool.getUsage();
            if (usage != null) {
                Map<String, Object> poolInfo = new HashMap<>();
                poolInfo.put("type", pool.getType().toString());
                poolInfo.put("used", formatBytes(usage.getUsed()));
                poolInfo.put("max", usage.getMax() == -1 ? "无限制" : formatBytes(usage.getMax()));
                if (usage.getMax() > 0) {
                    poolInfo.put("usage_percent", String.format("%.2f", (double) usage.getUsed() / usage.getMax() * 100));
                }
                jvmInfo.put(pool.getName().toLowerCase().replace(" ", "_"), poolInfo);
            }
        }
        
        return jvmInfo;
    }

    /**
     * 获取垃圾回收信息
     */
    public Map<String, Object> getGarbageCollectorInfo() {
        Map<String, Object> gcInfo = new HashMap<>();
        
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            Map<String, Object> gc = new HashMap<>();
            gc.put("collection_count", gcBean.getCollectionCount());
            gc.put("collection_time", gcBean.getCollectionTime() + "ms");
            gcInfo.put(gcBean.getName().toLowerCase().replace(" ", "_"), gc);
        }
        
        return gcInfo;
    }

    /**
     * 获取线程信息
     */
    public Map<String, Object> getThreadInfo() {
        Map<String, Object> threadInfo = new HashMap<>();
        
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        threadInfo.put("thread_count", threadBean.getThreadCount());
        threadInfo.put("daemon_thread_count", threadBean.getDaemonThreadCount());
        threadInfo.put("peak_thread_count", threadBean.getPeakThreadCount());
        threadInfo.put("total_started_thread_count", threadBean.getTotalStartedThreadCount());
        
        return threadInfo;
    }

    /**
     * 获取磁盘信息
     */
    public Map<String, Object> getDiskInfo() {
        Map<String, Object> diskInfo = new HashMap<>();
        
        File[] roots = File.listRoots();
        for (File root : roots) {
            Map<String, Object> disk = new HashMap<>();
            long total = root.getTotalSpace();
            long free = root.getFreeSpace();
            long used = total - free;
            
            disk.put("total", formatBytes(total));
            disk.put("free", formatBytes(free));
            disk.put("used", formatBytes(used));
            if (total > 0) {
                disk.put("usage_percent", String.format("%.2f", (double) used / total * 100));
            }
            
            diskInfo.put(root.getPath().replace(":", "").replace("\\", "").toLowerCase(), disk);
        }
        
        return diskInfo;
    }

    /**
     * 格式化字节数
     */
    private String formatBytes(long bytes) {
        if (bytes < 0) return "0 B";
        
        final String[] units = {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        
        if (digitGroups >= units.length) {
            digitGroups = units.length - 1;
        }
        
        return String.format("%.2f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}