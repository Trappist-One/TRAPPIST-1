package com.t1.oauth.controller;

import cn.hutool.core.util.ReUtil;
import com.t1.common.annotation.LoginUser;
import com.t1.common.config.GlobalConfig;
import com.t1.common.constant.CommonConstants;
import com.t1.common.constant.SqlConstants;
import com.t1.common.entity.User;
import com.t1.common.exception.BusinessException;
import com.t1.common.model.R;
import com.t1.common.utils.FileUtil;
import com.t1.common.utils.StrUtil;
import com.t1.common.utils.UploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 通用请求处理
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/common" )
public class CommonController {

    private final JdbcTemplate jdbcTemplate;
    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download" )
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!ReUtil.contains(FileUtil.FILENAME_PATTERN, fileName)) {
                throw new BusinessException(StrUtil.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_" ) + 1);
            String filePath = GlobalConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8" );
            response.setContentType("multipart/form-data" );
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtil.setFileDownloadHeader(request, realFileName));
            FileUtil.writeToStream(filePath, response.getOutputStream());
            if (delete) {
                FileUtil.del(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload" )
    public R uploadFile(@LoginUser User user, MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            String oFileName = file.getOriginalFilename();
            // 获取文件的文件名
            String fileName = oFileName.substring(0, oFileName.lastIndexOf("."));
            // 获取文件的后缀名
            String fileFormat = oFileName.substring(oFileName.lastIndexOf(".") + 1);
            // 获取文件的后缀名
            String type = UploadUtil.getType(fileFormat);
            // 新文件名
            String newFileName = type + System.currentTimeMillis();
            // 获取大小
            String fileSize = FileUtil.fileSize(file.getSize());
            // 上传文件
            String path = "/profile/upload/" + type + "/" + UploadUtil.fileUp(file, GlobalConfig.getUploadPath() + type + "/", newFileName);
            jdbcTemplate.update(SqlConstants.ADD_FILEINFO, fileName, type, fileFormat, fileSize, path, user.getDeptId(), user.getUserName(), new Date());
            return R.success(path);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource" )
    public void resourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 本地资源路径
        String localPath = GlobalConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StrUtil.subAfter(name, CommonConstants.RESOURCE_PREFIX, true);
        // 下载名称
        String downloadName = StrUtil.subAfter(downloadPath, "/", true);
        response.setCharacterEncoding("utf-8" );
        response.setContentType("multipart/form-data" );
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtil.setFileDownloadHeader(request, downloadName));
        FileUtil.writeToStream(downloadPath, response.getOutputStream());
    }
}
