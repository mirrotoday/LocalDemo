package com.example.localdemo.controller;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.example.localdemo.result.ApiResult;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * @author xieteng
 * @date 2023/11/16 ❤ 9:14
 * @description TODO
 */
@RestController
@RequestMapping("/download")
public class DownloadStyleController {
    public static final ThreadFactory factory = new ThreadFactoryBuilder().setNamePrefix("compressFileList-pool-").build();
    public static final ExecutorService executor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20), factory);
    @SneakyThrows
    @GetMapping("/urlzip")
    public void urlZip(HttpServletResponse response) {
        List<String> urls = Arrays.asList
                ("http://127.0.0.1:9000/file/107010f41c91f09bd5f1c696459c34b6.mp4",
                        "http://127.0.0.1:9000/file/4cea52ad8ec5004abced19489631d5f.jpg",
                        "http://127.0.0.1:9000/file/8c3072cb01a5bd74!400x400.jpg");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=ImagesFiles.zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.setLevel(0);
            urls.forEach(f -> {
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(StringUtils.getFilename(f)));
                    HttpUtil.download(f, zipOutputStream, false);
                    zipOutputStream.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            zipOutputStream.flush();
            zipOutputStream.finish();
        }
    }
    @GetMapping("/parallelZip")
    public void excelZipThread(HttpServletResponse response) throws IOException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=demo.zip");
        List<String> urls = Arrays.asList
                ("http://127.0.0.1:9000/file/107010f41c91f09bd5f1c696459c34b6.mp4",
                        "http://127.0.0.1:9000/file/4cea52ad8ec5004abced19489631d5f.jpg",
                        "http://127.0.0.1:9000/file/8c3072cb01a5bd74!400x400.jpg");

        ParallelScatterZipCreator parallelScatterZipCreator = new ParallelScatterZipCreator(executor);
        try (ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(response.getOutputStream())) {
            zipArchiveOutputStream.setLevel(0);
            urls.forEach(x -> {
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(StringUtils.getFilename(x));
                zipArchiveEntry.setMethod(ZipArchiveEntry.STORED);
                InputStreamSupplier inputStreamSupplier = () -> URLUtil.getStream(URLUtil.url(x));
                parallelScatterZipCreator.addArchiveEntry(zipArchiveEntry, inputStreamSupplier);
            });
            parallelScatterZipCreator.writeTo(zipArchiveOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
