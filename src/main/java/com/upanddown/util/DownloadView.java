package com.upanddown.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {
    
    public DownloadView() {
        setContentType("application/download; charset=utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
	Map<String, Object> fileInfo = (Map<String, Object>) model.get("downloadFile");
	String filePhysicName = (String) fileInfo.get("fileName");
	String path = (String) fileInfo.get("filePath");
	
	File file = new File(path+filePhysicName);
	
	res.setContentType(getContentType());
	res.setContentLength((int)file.length());
	
	String fileName = null;
	String userAgent = req.getHeader("User-Agent");
	if(userAgent.indexOf("MSIE") > -1) {
	    fileName = URLEncoder.encode(filePhysicName, "utf-8");
	} else {
	    fileName = new String(filePhysicName.getBytes("utf-8"), "iso-8859-1");
	}
	
	res.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(), "ISO8859_1"));
	res.setHeader("Content-Transfer-Encoding", "binary");
	
	OutputStream out = res.getOutputStream();
	FileInputStream fis = null;
	
	try{
	    fis = new FileInputStream(file);
	    FileCopyUtils.copy(fis, out);
	} finally {
	    if(fis != null) {
		try {
		    fis.close();
		} catch(IOException ex) {
		    ex.getMessage();
		}
	    }
	}
	out.flush();
    }
}
