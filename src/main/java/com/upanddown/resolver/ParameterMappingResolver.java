package com.upanddown.resolver;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ParameterMappingResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mvcContainer, NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
	System.out.println("param resolver argument ~~~~~");
	Map requestMap = new HashMap();
	
	HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
	for( Enumeration<?> enumeration = request.getParameterNames(); enumeration.hasMoreElements(); ){
	    String key = (String)enumeration.nextElement();
	    String[] values = webRequest.getParameterValues(key);
	    if(values != null){
//		requestMap.put(key, (values.length <= 1) ? ((Object)(values[0])) : ((Object)(values)));
//		requestMap.put(key, (values.length <= 1) ? values : values[0]);
		requestMap.put(key, (values.length > 1) ? values: values[0] );
	    }
	}
	return requestMap;
	
    }

    @Override
    public boolean supportsParameter(MethodParameter arg0) {
	System.out.println("support param resolver -----");
	return true;
    }
}