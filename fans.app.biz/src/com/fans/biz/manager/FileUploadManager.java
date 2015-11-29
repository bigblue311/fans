package com.fans.biz.manager;

import java.io.InputStream;

import com.victor.framework.common.shared.Result;

public interface FileUploadManager {
	
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
}
