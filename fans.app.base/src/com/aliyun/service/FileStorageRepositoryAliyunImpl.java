package com.aliyun.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.FileOperateTools;
import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.StringTools;


public class FileStorageRepositoryAliyunImpl implements FileStorageRepository {

    private boolean inited = false;
    private String accessId = "";
    private String accessKey = "";
    private String endPoint = "http://oss.aliyuncs.com/";
    private String bucketName = "";
    private String prefix = "";
    private String httpHead = "";
    
    private static final String TEMP_PATH = "temp/";

    private OSSClient client = null;


    public void init() {
        if (inited) {
            return;
        }
        // 可以使用ClientConfiguration对象设置代理服务器、最大重试次数等参数。
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(100000);
        client = new OSSClient(endPoint, accessId, accessKey, config);
        inited = true;
    }
    
    @Override
	public Result<String> uploadImg(String fileName, InputStream in) {
    	if(StringTools.isEmpty(fileName)){
    		return Result.newInstance("", "文件不存在", false);
    	}
    	fileName = TEMP_PATH+generateRelativePath(fileName);
    	try {
			put(fileName, in);
		} catch (IOException e) {
			e.printStackTrace();
			return Result.newInstance("", "文件上传失败", false);
		}
		return Result.newInstance(httpHead+fileName, "文件上传成功", true);
	}


	@Override
	public Result<String> copyTemp(String tempPath) {
		init();
		if(StringTools.isEmpty(tempPath)){
			return Result.newInstance("", "文件不存在", false);
		}
		
		String sourceKey = tempPath;
		String destinationKey = "";
		if(tempPath.startsWith(httpHead)){
			sourceKey = tempPath.replace(httpHead, "");
		}
		destinationKey = sourceKey.replace(TEMP_PATH, "");
		
		try {
			client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
			client.deleteObject(bucketName, sourceKey);
		} catch (OSSException | ClientException e) {
			e.printStackTrace();
			return Result.newInstance("", "文件上传失败", false);
		}
		return Result.newInstance(httpHead+destinationKey, "文件上传成功", true);
	}


	@Override
	public void recycleTemp() {
//		try {
//			client.deleteObject(bucketName, TEMP_PATH);
//		} catch (OSSException e) {
//			e.printStackTrace();
//		} catch (ClientException e) {
//			e.printStackTrace();
//		}
		//先不删除了.
		return;
	}


    @Override
    public void put(String fileName, byte[] bytes) throws IOException {
        if (bytes == null) {
            throw new NullPointerException("bytes to store is null");
        }
        init();
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(bytes.length);
        
        if (fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("gif")) {
            meta.setContentType("image/jpeg");
        } else if (fileName.endsWith("ico")) {
            meta.setContentType("image/x-icon");
        } else if (fileName.endsWith("css")) {
            meta.setContentType("text/css");
        } else if (fileName.endsWith("js")) {
            meta.setContentType("application/x-javascript");
        } else if (fileName.endsWith("xml")) {
            meta.setContentType("text/xml");
        }
        meta.setCacheControl("max-age=2592000");
//      meta.setHeader("CONTENT-MD5", HashUtil.getMd5Base64(bytes));

        try {
            client.putObject(bucketName, fileName, new ByteArrayInputStream(bytes), meta);
        } catch (Exception e) {
            throw new IOException(e);
        }

    }


    @Override
    public void put(String fileName, InputStream input) throws IOException {
        init();
        ObjectMetadata meta = new ObjectMetadata();
        long size = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int len = 0;
        try {
            while ((len = input.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
                if (outputStream.size() >= 20 * 1024 * 1024) {
                    break;
                }
            }
        } catch (IOException e1) {
        }
        size = outputStream.size();
        input = new ByteArrayInputStream(outputStream.toByteArray());
        meta.setContentLength(size);
        
        if (fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("jpeg")
                || fileName.endsWith("gif")) {
            meta.setContentType("image/jpeg");
        } else if (fileName.endsWith("ico")) {
            meta.setContentType("image/x-icon");
        } else if (fileName.endsWith("css")) {
            meta.setContentType("text/css");
        } else if (fileName.endsWith("js")) {
            meta.setContentType("application/x-javascript");
        }
        meta.setCacheControl("max-age=2592000");
        try {
            client.putObject(bucketName, fileName, input, meta);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public String getFileEtag(String fileName) {
        init();
        try {
            OSSObject object = client.getObject(bucketName, fileName);
            if (object == null) {
                return null;
            }
            return object.getObjectMetadata().getETag();
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(String fileName) {
        init();
        try {
            OSSObject object = client.getObject(bucketName, fileName);
            if (object == null) {
                return false;
            }
            object.getObjectContent().close();
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }


    @Override
    public byte[] readBytes(String fileName) {
        init();
        try {
            OSSObject object = client.getObject(bucketName, fileName);
            return FileOperateTools.readBytes(object.getObjectContent());
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void delete(String fileName) {
        init();
        try {
            client.deleteObject(bucketName, fileName);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    
    private String generateRelativePath(String originalFileName){
    	String extName = StringTools.getFileExtName(originalFileName);
		String storeName = MD5.getMD5(originalFileName + System.currentTimeMillis()) + "." + extName; 
		String timestamp = DateTools.DateToStringCompact(DateTools.today());
		if(StringTools.isEmpty(extName)){
			extName = "file";
		}
		return prefix+"/"+timestamp+"/"+extName+"/"+storeName;
	}

    public boolean isInited() {
        return inited;
    }

    public void setInited(boolean inited) {
        this.inited = inited;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    
    public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public void setHttpHead(String httpHead) {
		if(!httpHead.endsWith("/")){
			httpHead = httpHead+"/";
		}
		this.httpHead = httpHead;
	}

	public String getHttpHead() {
		return httpHead;
	}
}
