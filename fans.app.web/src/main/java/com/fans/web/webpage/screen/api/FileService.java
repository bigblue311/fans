package com.fans.web.webpage.screen.api;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.aliyun.service.FileStorageRepository;
import com.fans.biz.manager.UserManager;
import com.fans.dal.model.UserDO;
import com.fans.web.webpage.RequestSessionBase;
import com.victor.framework.common.shared.Result;

public class FileService extends RequestSessionBase{
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private FileStorageRepository fileStorageRepository;
	
	@Autowired
	private UserManager userManager;
	
	public Result<String> execute(@Param("imgType")String imgType) throws IOException {
		TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
		ParameterParser parser = rundata.getParameters();
		FileItem uploadFile = parser.getFileItem("Filedata");
		if(uploadFile == null) {
			return Result.newInstance("", "文件未找到", false);
		}
		InputStream in = uploadFile.getInputStream();
		Result<String> result = fileStorageRepository.uploadImg(uploadFile.getName(), in);
		UserDO userDO = super.getUserDO(request);
		if(userDO != null){
		    if(imgType.equals("headImg")){
	            userDO.setHeadImg(result.getDataObject());
	        }
	        if(imgType.equals("qrcode")){
	            userDO.setQrcode(result.getDataObject());
	        }
	        if(imgType.equals("groupQrcode")){
	            userDO.setGroupQrcode(result.getDataObject());
	        }
	        userManager.update(userDO);
		}
		return result;
	}
}
