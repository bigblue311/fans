package com.victor.framework.common.tools;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileOperateTools {
    private final static Logger logger = LoggerFactory.getLogger(FileOperateTools.class);

    /**
     * 把srcFile中的内容，添加到destFile中去
     * @param srcFile
     * @param destFile
     * @throws IOException 
     */
    public static void appendToFile(File srcFile, File destFile) throws IOException {
    	FileInputStream srcInputStream = null;
    	FileOutputStream destOutputStream = null;
    	
    	try {
    		srcInputStream = new FileInputStream(srcFile);
    		destOutputStream = new FileOutputStream(destFile, true);
    		IOUtils.copy(srcInputStream, destOutputStream);
    	} finally {
    		if(destOutputStream != null) {
    			destOutputStream.close();
    		}
    		if(srcInputStream != null) {
    			srcInputStream.close();
    		}
    	}
    }
    
    public static byte[] readBytes(String path) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[2048];
        int len = input.read(buffer);
        while(len > 0) {
            output.write(buffer, 0, len);
            len = input.read(buffer);
        }
        input.close();
        
        return output.toByteArray();
    }
    
    public static byte[] readBytes(InputStream input) throws IOException {
    	if(input == null) {
    		return null;
    	}
    	InputStream input1 = new BufferedInputStream(input);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = input1.read(buffer);
        while(len > 0) {
            output.write(buffer, 0, len);
            len = input1.read(buffer);
        }
        input1.close();
        
        return output.toByteArray();
    }
    
    public static List<String> getAllFiles(String rootPath) {
        List<String> files= new ArrayList<String>();
        if (StringTools.isEmpty(rootPath)) {
            logger.error("The root path is null");
            return files;
        }
        
        if(!isDirectory(rootPath)) {
            files.add(rootPath);
        } else {
            if(!rootPath.endsWith("/")) {
                rootPath += "/";
            }
            String[] subFiles = listFiles(rootPath);
            if(subFiles != null) {
                for(String subFile : subFiles) {
                    if(subFile.startsWith(".")) {
                        continue;
                    }
                    String path = rootPath + subFile;
                    files.addAll(getAllFiles(path));
                }
            }
        }
        return files;
    }

    public static String[] listFiles(String dir) {
        File file=new File(dir);
        return file.list();
    }

    public static boolean isDirectory(String fileName) {
        File file=new File(fileName);
        return file.isDirectory();
    }

    /**
     * 获得文件的后缀
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
    	if(fileName == null) {
    		return null;
    	}
    	
		int index = fileName.lastIndexOf(".");
		if(index < 0 ) {
			return null;
		}
		
		return fileName.substring(index+1);
    }
}
