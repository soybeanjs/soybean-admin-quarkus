package com.soybean.framework.boot;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 根据ip查询地址
 *
 * @author wenxina
 * @since 2019/10/30
 */
@Slf4j
public class RegionUtils {

    private static final String JAVA_TEMP_DIR = "java.io.tmpdir";

    /**
     * 搜索
     */
    private static Searcher searcher = null;

    static {
        try {
            String dbPath = Objects.requireNonNull(RegionUtils.class.getResource("/ip2region/ip2region.xdb")).getPath();
            File file = new File(dbPath);
            if (!file.exists()) {
                String tmpDir = System.getProperties().getProperty(JAVA_TEMP_DIR);
                dbPath = tmpDir + "ip2region/ip2region.xdb";
                file = new File(dbPath);
                String classPath = "classpath:ip2region/ip2region.xdb";
                InputStream resourceAsStream = ResourceUtil.getStreamSafe(classPath);
                if (resourceAsStream != null) {
                    FileUtils.copyInputStreamToFile(resourceAsStream, file);
                }
            }
            // 从 dbPath 加载整个 xdb 到内存。
            byte[] cBuff = Searcher.loadContentFromFile(dbPath);

            // 使用上述的 cBuff 创建一个完全基于内存的查询对象。
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("init ip region error", e);
        }
    }

    public static void main(String[] args) {
        RegionUtils.getRegion("1.2.3.4");
    }

    /**
     * 解析IP
     *
     * @param ip ip
     * @return 查询结果
     */
    public static String getRegion(String ip) {
        try {
            if (searcher != null && StrUtil.isEmpty(ip)) {
                log.error("Searcher is null");
                return StrUtil.EMPTY;
            }
            long startTime = System.nanoTime();
            String result = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startTime);
            log.debug("region use time[{}] result[{}]", cost, result);
            return result;
        } catch (Exception e) {
            log.error("error:", e);
        }
        return StrUtil.EMPTY;
    }

}
