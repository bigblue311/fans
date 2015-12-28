package com.aliyun.service;

import java.io.IOException;
import java.io.InputStream;

import com.victor.framework.common.shared.Result;


/**
 * 文件存储服务，用于往本地或远程(如aliyun、又拍等)存储文件信息
 * 
 * @author hongwm
 * @since 2013-6-25
 */
public interface FileStorageRepository {
	
	/**
	 * 上传一个文件
	 * @param in
	 * @return
	 */
	Result<String> uploadImg(String fileName, InputStream in);
	
	/**
	 * 将零时目录的文件升级成正式目录
	 * @param tempPath
	 * @return
	 */
	Result<String> copyTemp(String tempPath);
	
	/**
	 * 回收零时目录
	 */
	void recycleTemp();
    /**
     * 把input流放入存储中
     * 
     * @param fileName
     *            　存储的文件名
     * @param input
     *            　input流
     * @param bucketName
     *            　bucket名称
     * @param size
     *            文件的大小
     * @throws IOException
     */
    void put(String fileName, InputStream input) throws IOException;


    /**
     * 把bytes放入存储中
     * 
     * @param fileName
     *            存储的文件名
     * @param bytes
     * @param bucketName
     *            bucket名称
     * @throws IOException
     */
    void put(String fileName, byte[] bytes) throws IOException;


    /**
     * 获取文件的bytes
     * 
     * @param fileName
     *            文件名
     * @param bucketName
     *            bucket名称
     * @return
     */
    byte[] readBytes(String fileName);


    /**
     * 判断文件是否存在
     * 
     * @param fileName
     *            文件名
     * @param bucketName
     *            bucket名称
     * @return
     */
    boolean exists(String fileName);

    /**
     * 获取文件的hash签名
     * 
     * @param fileName
     *            文件名
     * @param bucketName
     *            bucket名称
     * @return
     */
    String getFileEtag(String fileName);


    /**
     * 删除文件
     * 
     * @param fileName
     *            文件名
     * @param bucketName
     *            bucket名称
     */
    void delete(String fileName);
}
