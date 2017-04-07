package com.upanddown.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UpandDownController {
    
    private String uploadPath = "C:/Users/gskim/workspace/UpandDownTestPrj/WebContent/upload/";
    
    public UpandDownController() {
	//default constructor
    }
    
    @RequestMapping(value = "/")
    public ModelAndView mainPage() {
	ModelAndView mav = new ModelAndView("index");
	
	return mav;
    }
    
    @RequestMapping(value = "/updownPage")
    public ModelAndView updownPage() {
	ModelAndView mav = new ModelAndView("/upanddown/upanddown");
	
	return mav;
    }
    
    @RequestMapping(value = "/upFile")
    public ModelAndView upFile(Object requestMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
	Map<String, Object> pMap = (Map<String, Object>) requestMap;
	
	MultipartHttpServletRequest mReq = (MultipartHttpServletRequest) req;
	Iterator<String> iterator = mReq.getFileNames();
	
	MultipartFile mFile = null;
	String originFileName = null;
	
	List<Map<String, Object>> fileInfo = new ArrayList<Map<String, Object>>();
	Map<String, Object> fileMap = null;
	
	System.out.println("File Upload Start ----------------");
	while(iterator.hasNext()) {
	    mFile = mReq.getFile(iterator.next());
	    if(mFile.isEmpty() == false) {
		originFileName = mFile.getOriginalFilename();
		System.out.println("1 : " + originFileName);
		
		long fileSize = mFile.getSize();
		System.out.println("2 : " + fileSize);
		
		File file = new File(uploadPath, originFileName);
		mFile.transferTo(file);
		
		fileMap = new HashMap<String, Object>();
		fileMap.put("fileName", originFileName);
		fileMap.put("fileSize", fileSize);
		fileMap.put("uploadPath", uploadPath);
		
		fileInfo.add(fileMap);
	    }
	}
	
	/**
	if(!mFile.isEmpty()) {
	    String upPath = "/upload";
	    
	    if (mFile.length != names.length) {
		System.out.println("Mandatory information missing");
	    }

        	String message = "";
        	for (int i = 0; i < mFile.; i++) {
        		MultipartFile file = mFile[i];
        		String name = names[i];
        		try {
        			byte[] bytes = file.getBytes();
        
        			// Creating the directory to store file
        			String rootPath = System.getProperty("catalina.home");
        			File dir = new File(rootPath + File.separator + "tmpFiles");
        			if (!dir.exists())
        				dir.mkdirs();
        
        			// Create the file on server
        			File serverFile = new File(dir.getAbsolutePath()
        					+ File.separator + name);
        			BufferedOutputStream stream = new BufferedOutputStream(
        					new FileOutputStream(serverFile));
        			stream.write(bytes);
        			stream.close();
        
        			System.out.println("Server File Location=" + serverFile.getAbsolutePath());
        
        			message = message + "You successfully uploaded file=" + name;
        		} catch (Exception e) {
        			message = "You failed to upload " + name + " => " + e.getMessage();
        		}
        	}
	}
	**/
	
	System.out.println(fileInfo);
	ModelAndView mav = new ModelAndView("jsonView");
	
	mav.addObject("fileInfo", fileInfo);
	
	return mav;
    }
    @RequestMapping(value = "/downFile")
    public ModelAndView downFile(Object requestMap, HttpServletRequest req, HttpServletResponse res) throws Exception {
	Map<String, Object> pMap = (Map<String, Object>) requestMap;
	System.out.println("File Download Start ----------------");
	
	String fileName = (String) pMap.get("fileName");
	String filePath = (String) pMap.get("filePath");
	
	Map<String, Object> fileInfo = new HashMap<String, Object>();
	
	fileInfo.put("fileName", fileName);
	fileInfo.put("filePath", filePath);
	
	return new ModelAndView("downloadView", "downloadFile", fileInfo);
    }
}