package com.yummyerp.cloud.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.enums.BusinessStatus;
import com.yummyerp.cloud.modules.system.entity.SysOperLog;
import com.yummyerp.cloud.modules.system.service.SysOperLogService;
import com.yummyerp.cloud.utils.IpUtils;
import com.yummyerp.cloud.utils.SecurityUtils;
import com.yummyerp.cloud.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作日志记录处理
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword", "token", "secret", "sign", "key"};
    
    /**
     * 参数最大长度限制
     */
    private static final int MAX_PARAM_LENGTH = 9000;

    private final SysOperLogService operLogService;

    private final ObjectMapper objectMapper;

    /**
     * 计时器
     */
    private final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    public LogAspect(SysOperLogService operLogService, ObjectMapper objectMapper) {
        this.operLogService = operLogService;
        this.objectMapper = objectMapper;
    }

    /**
     * 处理请求前执行
     */
    @Before("@annotation(com.yummyerp.cloud.annotation.Log)")
    public void doBeforeLog() {
        startTimeThreadLocal.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param result    返回结果
     */
    @AfterReturning(pointcut = "@annotation(com.yummyerp.cloud.annotation.Log)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(com.yummyerp.cloud.annotation.Log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 处理日志详细信息
     */
    private void handleLog(final JoinPoint joinPoint, final Exception e, Object result) {
        try {
            // 获取注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            String username = SecurityUtils.getUsername();
            String deptName = SecurityUtils.getDeptName();

            if (StringUtils.isEmpty(username)) {
                username = "未登录用户";
            }
            if (StringUtils.isEmpty(deptName)) {
                deptName = "未知部门";
            }

            // 获取请求相关信息
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal()); // 状态默认为正常
            // 请求的地址
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperName(username);
            operLog.setDeptName(deptName);

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal()); // 状态设置为异常
                String errorMsg = e.getMessage();
                if (errorMsg != null && errorMsg.length() > MAX_PARAM_LENGTH) {
                    errorMsg = errorMsg.substring(0, MAX_PARAM_LENGTH);
                }
                operLog.setErrorMsg(errorMsg);
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");

            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());

            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, request);

            // 设置消耗时间
            Long startTime = startTimeThreadLocal.get();
            if (startTime != null) {
                operLog.setCostTime(System.currentTimeMillis() - startTime);
            } else {
                operLog.setCostTime(0L);
            }

            // 设置操作时间
            operLog.setOperTime(new Date());

            // 记录操作地点
            operLog.setOperLocation(IpUtils.getRegion(request));
            // 保存响应结果
            if (controllerLog.isSaveResponseData() && operLog.getStatus() == BusinessStatus.SUCCESS.ordinal() && result != null) {
                setJsonResult(operLog, result);
            }

            // 保存数据库
            operLogService.asyncInsertOperLog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==记录操作日志异常==");
            log.error("异常信息:{}", exp.getMessage());
        } finally {
            startTimeThreadLocal.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, HttpServletRequest request) {
        // 设置action动作
        operLog.setBusinessType(log.businessType());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType());

        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog, request);
        }
    }

    /**
     * 获取请求的参数，放到log中
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog, HttpServletRequest request) {
        String requestMethod = operLog.getRequestMethod();
        Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            if (params.length() > MAX_PARAM_LENGTH) {
                params = params.substring(0, MAX_PARAM_LENGTH);
            }
            operLog.setOperParam(params);
        } else {
            // 其他类型操作的参数处理
            Map<String, String[]> paramsMap2 = request.getParameterMap();
            if (paramsMap2 != null && !paramsMap2.isEmpty()) {
                try {
                    String params = objectMapper.writeValueAsString(paramsMap2);
                    // 过滤敏感信息
                    params = filterSensitiveInfo(params);
                    if (params != null && params.length() > MAX_PARAM_LENGTH) {
                        params = params.substring(0, MAX_PARAM_LENGTH);
                    }
                    operLog.setOperParam(params);
                } catch (JsonProcessingException e) {
                    log.error("处理请求参数JSON转换异常", e);
                }
            } else if (paramsMap != null && !paramsMap.isEmpty()) {
                try {
                    String params = objectMapper.writeValueAsString(paramsMap);
                    // 过滤敏感信息
                    params = filterSensitiveInfo(params);
                    if (params != null && params.length() > MAX_PARAM_LENGTH) {
                        params = params.substring(0, MAX_PARAM_LENGTH);
                    }
                    operLog.setOperParam(params);
                } catch (JsonProcessingException e) {
                    log.error("处理请求参数JSON转换异常", e);
                }
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (o != null && !isFilterObject(o)) {
                    try {
                        String jsonObj = objectMapper.writeValueAsString(o);
                        // 过滤敏感信息
                        jsonObj = filterSensitiveInfo(jsonObj);
                        params.append(jsonObj).append(" ");
                    } catch (JsonProcessingException e) {
                        // 忽略JSON转换异常
                        log.error("JSON转换异常", e);
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    private boolean isFilterObject(final Object o) {
        if (o == null) {
            return true;
        }
        
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                if (value instanceof MultipartFile) {
                    return true;
                }
            }
            return false;
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object entry : map.entrySet()) {
                Map.Entry mapEntry = (Map.Entry) entry;
                if (mapEntry.getValue() instanceof MultipartFile) {
                    return true;
                }
            }
            return false;
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

    /**
     * 设置返回值
     */
    private void setJsonResult(SysOperLog operLog, Object result) {
        try {
            if (result != null) {
                String jsonResult = objectMapper.writeValueAsString(result);
                // 过滤敏感信息
                jsonResult = filterSensitiveInfo(jsonResult);
                if (jsonResult != null && jsonResult.length() > MAX_PARAM_LENGTH) {
                    jsonResult = jsonResult.substring(0, MAX_PARAM_LENGTH);
                }
                operLog.setJsonResult(jsonResult);
            }
        } catch (JsonProcessingException e) {
            log.error("设置json返回值异常：{}", e.getMessage());
        }
    }

    /**
     * 过滤敏感信息
     */
    private String filterSensitiveInfo(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        String result = content;
        for (String excludeProperty : EXCLUDE_PROPERTIES) {
            // 构建正则表达式 "password":"123456" -> "password":"******"
            Pattern pattern = Pattern.compile("(\"" + excludeProperty + "\":)([^,}\\]]+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                result = matcher.replaceAll("$1\"******\"");
            }
        }
        return result;
    }
} 