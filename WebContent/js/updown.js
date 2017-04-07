/**
 * http://usejsdoc.org/
 */

var totalFileLength, totalUploaded, fileCount, filesUploaded;

$(function(){
	console.log("updown.js");
	
	$(document).ready(function(){
		$('#files').bind('change', function(e){
			onFileSelect(e);
		});
		$('form[name=fileInfo]').submit(function(event){
			startUpload(event);
		});
	});
	
	function onFileSelect(e){
		var files = e.target.files;
		var output = [];
		fileCount = files.length;
		
		var html = '';
		for(var i=0; i < fileCount; i++){
			var file = files[i];
			
			html += '<p>';
			html += file.name + ', (' + file.size + ' bytes, ' + file.lastModifiedDate.toLocaleDateString() + ')';
			html += '</p>';
		}
		$('#selectedFiles').html(html);
	}
	
	function startUpload(event){
		event.preventDefault();
		
		var fd = new FormData();
		
		$.each($('#files'), function(i, obj){
			$.each(obj.files, function(j, file){
				fd.append('files['+j+']', file);
				console.log(j + " : " + file.name);
			});
		});
		$.ajax({
			url: '/upFile',
			processData: false,
			contentType: false,
			data: fd,
			type: 'POST',
			success: function(res){
				var fileInfo = res.fileInfo;
				var resHtml = '';
				
				for(var i=0; i < fileInfo.length; i++){
					var fileName = fileInfo[i].fileName;
					var fileSize = fileInfo[i].fileSize;
					var linkVal = fileInfo[i].linkVal;
					var uploadPath = fileInfo[i].uploadPath;
					
					resHtml += '<div name="upFileList_'+ i +'">';
					resHtml += '	<a href="#">'+ fileName +'</a>';
					resHtml += '	<span> ('+ fileSize +') </span>';
					resHtml += '	<div name="hiddenDiv">';
					resHtml += '		<input type="hidden" name="filePath" value="'+ uploadPath +'"/>';
					resHtml += '		<input type="hidden" name="fileName" value="'+ fileName +'"/>';
					resHtml += '	</div>';
					resHtml += '</div>';
				}
				
				$('#successUpFileList_area').html(resHtml);
			},
			error: function(e){
				onFailed(e);
			}
		});
	}
	
	function onFailed(e) {
        alert(e);
    }
	
	$(document).on('click', 'a', function(){
		console.log("click a tag");
		var path = $(this).siblings('div').find(':first').val();
		var name = $(this).siblings('div').find(':last').val();
		console.log(path + ", " + name);
		
		var params = {
				fileName: name,
				filePath: path
		};
		
		submitFORM("/downFile", params, "POST", $(this));
	});
		
	function submitFORM(path, params, method, tagLoc){
		method = method || "post";
		
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", path);
		console.log(form);
		
		for(var key in params){
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);
			
			form.appendChild(hiddenField);
		}
		tagLoc.parent().append(form);
		form.submit();
	}
});