package net.eipk.core.mvc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

public class MVCLoader {
	static protected final Logger log = Logger.getLogger("Eipk");
	private Set<Class<?>> classes;
	private static Map<Class<?>,Object> beanMapping=new HashMap<Class<?>,Object>();
	private static Map<Request,RequestHandler> requestMapping=new HashMap<Request,RequestHandler>();

	@PostConstruct
	private void init(){
		this.classes=scanClasses("net.eipk");
	}

	public Set<Class<?>> getClasses(){
		return classes;
	}
	public RequestHandler getRequestHandler(String requestUrl,String requestMethod){
		Request request=new Request(requestUrl,requestMethod);
		return requestMapping.get(request);
	}
	public Object getBean(Class<?> clazz){
		return beanMapping.get(clazz);
	}

	public Map<Request,RequestHandler> testHandler(){
		return requestMapping;
	}

	private static Set<Class<?>> scanClasses(String pack){
		Set<Class<?>> rtn_classes = new LinkedHashSet<Class<?>>();
		boolean recursive = true;
		String packageName = pack;
		Enumeration<URL> urls;
		try {
			urls = Thread.currentThread().getContextClassLoader().getResources(pack.replace('.', '/'));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();
				if (protocol.equals("jar")) {
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							if (name.startsWith(pack.replace('.', '/'))) {
								int idx = name.lastIndexOf('/');
								if (idx != -1) {
									packageName = name.substring(0, idx).replace('/', '.');
								}
								if ((idx != -1) || recursive) {
									if (name.endsWith(".class")&&!entry.isDirectory()) {
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
											if(clazz.isAnnotationPresent(EipkController.class)){
												rtn_classes.add(clazz);
												buildRequestMapping(clazz);
											}
										} catch (ClassNotFoundException e) {
											log.log(Level.INFO, "未找到类文件：["+className+"]");
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.log(Level.INFO, "扫描过程失败。");
					}
				}
				else if(protocol.equals("file")){
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					loadByDir(packageName, filePath, recursive, rtn_classes);
				}
				else{
					log.log(Level.INFO, "在classes目录或jar中未找到对应的包,protocol:["+protocol+"]");
				}
			}
		} catch (IOException e) {
			log.log(Level.INFO, e.getMessage());
		}
		return rtn_classes;
	}

	private static void loadByDir(String packageName,String packagePath, final boolean recursive, Set<Class<?>> classes){
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			log.log(Level.INFO, "在classes目录中未扫描到包["+packageName+"]");
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				loadByDir(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			}
			else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
					if(clazz.isAnnotationPresent(EipkController.class)){
						classes.add(clazz);
						buildRequestMapping(clazz);
					}
                } catch (ClassNotFoundException e) {
                	log.log(Level.INFO, "未找到类文件["+className+"]");
				}
			}
		}
	}

	private static void buildRequestMapping(Class<?> clazz){

		try {
			beanMapping.put(clazz, clazz.newInstance());
		} catch (Exception e) {
			log.log(Level.INFO, "创建控制器对象失败。["+clazz.getName()+"]");
		}

		String url=clazz.getAnnotation(EipkController.class).value();
		Method[] methods=clazz.getDeclaredMethods();
		if(methods!=null&&methods.length>0){
			for(Method method:methods){
				if(method.isAnnotationPresent(EipkRequest.class)){
					String mapping=method.getAnnotation(EipkRequest.class).value();
					String request_method=method.getAnnotation(EipkRequest.class).method().toString();
					boolean isAjax=method.getAnnotation(EipkRequest.class).ajax();
					//if(mapping.matches("/\\w*")){
					Request request=new Request(url+mapping,request_method);
					RequestHandler handler=new RequestHandler(clazz,method,isAjax);
					requestMapping.put(request, handler);
					log.log(Level.INFO, "加载控制器:["+request_method+"]["+url+mapping+"]["+method.getName()+"],isAjax:["+isAjax+"]");
					//}
				}
			}
		}
	}
}