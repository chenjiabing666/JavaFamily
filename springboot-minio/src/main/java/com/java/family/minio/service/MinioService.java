package com.java.family.minio.service;

import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;

/**
 * @author 公众号：码猿技术专栏
 */
public interface MinioService {

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    boolean bucketExists(String bucketName);

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    void makeBucket(String bucketName);

    /**
     * 列出所有存储桶名称
     * @return
     */
    List<String> listBucketName();

    /**
     * 列出所有存储桶 信息
     *
     * @return
     */
    List<Bucket> listBuckets();

    /**
     * 根据桶名删除桶
     * @param bucketName
     */
    boolean removeBucket(String bucketName);

    /**
     * 列出存储桶中的所有对象名称
     * @param bucketName
     * @return
     */
    List<String> listObjectNames(String bucketName);

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param bucketName
     */
    String putObject( MultipartFile multipartFile, String bucketName,String fileType);

    /**
     * 文件流下载
     * @param bucketName
     * @param objectName
     * @return
     */
    InputStream downloadObject(String bucketName, String objectName);


    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     */
    boolean removeObject(String bucketName, String objectName);



    /**
     * 批量删除文件
     * @param bucketName
     * @param objectNameList
     * @return
     */
    boolean removeListObject(String bucketName, List<String> objectNameList);

    /**
     * 获取文件路径
     * @param bucketName
     * @param objectName
     * @return
     */
    String getObjectUrl(String bucketName,String objectName);
}
