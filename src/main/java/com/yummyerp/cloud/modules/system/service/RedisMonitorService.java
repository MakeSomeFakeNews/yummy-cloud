package com.yummyerp.cloud.modules.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Redis监控服务
 * @author 周欢
 * @since 2025-07-28
 */
@Service
public class RedisMonitorService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取Redis基本信息
     */
    public Map<String, Object> getRedisInfo() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Map<String, Object> result = new HashMap<>();
        
        if (info != null) {
            // 服务器信息
            result.put("version", info.getProperty("redis_version"));
            result.put("os", info.getProperty("os"));
            result.put("arch", info.getProperty("arch_bits"));
            result.put("process_id", info.getProperty("process_id"));
            result.put("uptime_in_seconds", info.getProperty("uptime_in_seconds"));
            result.put("uptime_in_days", info.getProperty("uptime_in_days"));
            
            // 客户端信息
            result.put("connected_clients", info.getProperty("connected_clients"));
            result.put("client_recent_max_input_buffer", info.getProperty("client_recent_max_input_buffer"));
            result.put("client_recent_max_output_buffer", info.getProperty("client_recent_max_output_buffer"));
            
            // 内存信息
            result.put("used_memory", info.getProperty("used_memory"));
            result.put("used_memory_human", info.getProperty("used_memory_human"));
            result.put("used_memory_rss", info.getProperty("used_memory_rss"));
            result.put("used_memory_rss_human", info.getProperty("used_memory_rss_human"));
            result.put("used_memory_peak", info.getProperty("used_memory_peak"));
            result.put("used_memory_peak_human", info.getProperty("used_memory_peak_human"));
            result.put("maxmemory", info.getProperty("maxmemory"));
            result.put("maxmemory_human", info.getProperty("maxmemory_human"));
            result.put("maxmemory_policy", info.getProperty("maxmemory_policy"));
            
            // 统计信息  
            result.put("total_connections_received", info.getProperty("total_connections_received"));
            result.put("total_commands_processed", info.getProperty("total_commands_processed"));
            result.put("instantaneous_ops_per_sec", info.getProperty("instantaneous_ops_per_sec"));
            result.put("keyspace_hits", info.getProperty("keyspace_hits"));
            result.put("keyspace_misses", info.getProperty("keyspace_misses"));
            
            // 持久化信息
            result.put("rdb_changes_since_last_save", info.getProperty("rdb_changes_since_last_save"));
            result.put("rdb_last_save_time", info.getProperty("rdb_last_save_time"));
            result.put("aof_enabled", info.getProperty("aof_enabled"));
        }
        
        return result;
    }

    /**
     * 获取Redis内存信息
     */
    public Map<String, Object> getMemoryInfo() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection -> 
            connection.info("memory"));
        
        Map<String, Object> result = new HashMap<>();
        if (info != null) {
            result.put("used_memory", info.getProperty("used_memory"));
            result.put("used_memory_human", info.getProperty("used_memory_human"));
            result.put("used_memory_rss", info.getProperty("used_memory_rss"));
            result.put("used_memory_rss_human", info.getProperty("used_memory_rss_human"));
            result.put("used_memory_peak", info.getProperty("used_memory_peak"));
            result.put("used_memory_peak_human", info.getProperty("used_memory_peak_human"));
            result.put("used_memory_overhead", info.getProperty("used_memory_overhead"));
            result.put("used_memory_dataset", info.getProperty("used_memory_dataset"));
            result.put("total_system_memory", info.getProperty("total_system_memory"));
            result.put("total_system_memory_human", info.getProperty("total_system_memory_human"));
            result.put("maxmemory", info.getProperty("maxmemory"));
            result.put("maxmemory_human", info.getProperty("maxmemory_human"));
            result.put("maxmemory_policy", info.getProperty("maxmemory_policy"));
            result.put("mem_fragmentation_ratio", info.getProperty("mem_fragmentation_ratio"));
        }
        return result;
    }

    /**
     * 获取Redis键空间信息
     */
    public Map<String, Object> getKeyspaceInfo() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection -> 
            connection.info("keyspace"));
        
        Map<String, Object> result = new HashMap<>();
        if (info != null) {
            // 解析keyspace信息
            for (String key : info.stringPropertyNames()) {
                if (key.startsWith("db")) {
                    String value = info.getProperty(key);
                    // 解析 keys=xxx,expires=xxx,avg_ttl=xxx 格式
                    Map<String, String> dbInfo = new HashMap<>();
                    String[] parts = value.split(",");
                    for (String part : parts) {
                        String[] kv = part.split("=");
                        if (kv.length == 2) {
                            dbInfo.put(kv[0], kv[1]);
                        }
                    }
                    result.put(key, dbInfo);
                }
            }
        }
        
        // 添加当前数据库键数量
        Long dbSize = redisTemplate.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        result.put("current_db_keys", dbSize);
        
        return result;
    }

    /**
     * 获取Redis命令统计信息
     */
    public Map<String, Object> getCommandStats() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) connection -> 
            connection.info("commandstats"));
        
        Map<String, Object> result = new HashMap<>();
        if (info != null) {
            for (String key : info.stringPropertyNames()) {
                if (key.startsWith("cmdstat_")) {
                    String cmdName = key.substring(8); // 去掉 "cmdstat_" 前缀
                    String value = info.getProperty(key);
                    
                    // 解析 calls=xxx,usec=xxx,usec_per_call=xxx 格式
                    Map<String, String> cmdInfo = new HashMap<>();
                    String[] parts = value.split(",");
                    for (String part : parts) {
                        String[] kv = part.split("=");
                        if (kv.length == 2) {
                            cmdInfo.put(kv[0], kv[1]);
                        }
                    }
                    result.put(cmdName, cmdInfo);
                }
            }
        }
        return result;
    }

    /**
     * 清空Redis缓存
     */
    public String flushDB() {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "OK";
        });
        return "Redis缓存清空成功";
    }
}