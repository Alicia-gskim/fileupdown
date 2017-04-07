<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="js/updown.js"></script>
</head>
<body>
	<center>
		<h2> File Uploadn & Download Page</h2>
	</center>
	
	<div id="upload_area">
		<h3>you can file upload function this hear.</h3>
		<button id="startUpload"> startUpload </button>
	</div>
	
	<div style="width:55%">  
        <h1>HTML5 Ajax Multi-file Upload With Progress Bar</h1>
<!--         <div id='progressBar' style='height: 20px; border: 2px solid green; margin-bottom: 20px'> -->
<!--             <div id='bar' style='height: 100%; background: #33dd33; width: 0%'> -->
<!--             </div> -->
<!--         </div> -->
        <form method="post" enctype="multipart/form-data" name="fileInfo" style="margin-bottom: 20px">
            <input type="file" id="files" name="files[]" multiple style="margin-bottom: 20px"/><br/>	
            <div id="selectedFiles"></div>
            <input type="submit" value="Upload" style="margin-top: 20px"/>
        </form>
<!--         <div id='debug' style='height: 100px; border: 2px solid #ccc; overflow: auto'></div> -->
    </div>
	
	<div>
		<h3>success upload file list & download can doing area.</h3>
		<div id="successUpFileList_area"></div>
		
	</div>
</body>
</html>