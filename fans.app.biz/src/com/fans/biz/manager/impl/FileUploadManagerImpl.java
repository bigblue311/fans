package com.fans.biz.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import com.fans.biz.manager.FileUploadManager;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;

public class FileUploadManagerImpl implements FileUploadManager {

	private String apacheRoot;
	private List<String> extWhiteList;
	private String allowedExt;

	@Override
	public Result<String> uploadImg(String fileName, InputStream in) {
		Result<String> result = checkExt(fileName);
	    if (!result.isSuccess()) {
            return result;
        }
		String ext = result.getDataObject();
		String returnUri = "/UploadFiles/temp" + "/" + DateTools.getTodayPath()
				+ "/" + DateTools.getRandomId() + ext;
		String tempFile = apacheRoot + returnUri;

		File file = new File(tempFile);
		try {
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					out.write(buffer[i]);
			}
			in.close();
			out.close();
			return Result.newInstance(returnUri, "文件上传成功", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件上传失败", false);
		}
	}
	
	private Result<String> checkExt(String fileName) {
		int index = fileName.indexOf(".");
		String ext = fileName.substring(index);
		if (StringTools.isAllEmpty(fileName, ext)) {
			return Result.newInstance("", "文件损坏或者文件后缀为空", false);
		}
		if (extWhiteList.contains(ext.toUpperCase()) || extWhiteList.contains("*")) {
			return Result.newInstance(ext, "文件可用", true);
		} else {
			return Result.newInstance("", "文件后缀必须为" + allowedExt, false);
		}
	}

	public void setApacheRoot(String apacheRoot) {
		this.apacheRoot = apacheRoot;
	}

	public void setExtWhiteList(List<String> extWhiteList) {
		this.extWhiteList = extWhiteList;
		for (String item : extWhiteList) {
			allowedExt += item + " ";
		}
	}

	@Override
	public Result<String> copyTemp(String tempPath) {
		String relativePath = tempPath.replace("temp/", "");
		String temp = apacheRoot + tempPath;
		String real = apacheRoot + relativePath;
		try {
			FileInputStream in = new FileInputStream(temp);
			File file = new File(real);
			if (!file.exists()) {
				String path = file.getParent();
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024];
			while ((c = in.read(buffer)) != -1) {
				for (int i = 0; i < c; i++) {
					out.write(buffer[i]);
				}
			}
			in.close();
			out.close();
			return Result.newInstance(relativePath, "文件可用", true);
		} catch (Exception ex) {
			return Result.newInstance("", "文件损坏或者异常发生", false);
		}
	}
	
	@Override
	public void recycleTemp() {
		String todayPath = DateTools.getTodayPath();
		String dir = apacheRoot + "/UploadFiles/temp";
		File tempPath = new File(dir);
		if(tempPath.isDirectory()){
			for(File sub : tempPath.listFiles()){
				if(!sub.getName().contains(todayPath)){
					delFolder(sub.getAbsolutePath());
				}
			}
		}
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	private void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	private boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
}
