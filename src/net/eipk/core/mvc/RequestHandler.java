package net.eipk.core.mvc;

import java.lang.reflect.Method;

public class RequestHandler {

	private Class<?> eipkControllerClass;
	private Method method;
	boolean isAjax;

	public RequestHandler(Class<?> eipkControllerClass, Method method,boolean isAjax) {
		super();
		this.eipkControllerClass = eipkControllerClass;
		this.method = method;
		this.isAjax=isAjax;
	}
	public Class<?> getEipkControllerClass() {
		return eipkControllerClass;
	}
	public Method getMethod() {
		return method;
	}
	public boolean isAjax() {
		return isAjax;
	}

}
