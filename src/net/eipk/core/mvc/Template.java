package net.eipk.core.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.eipk.core.SysUtil;

/*
 * 
 * 模板
 */
public class Template {
	static protected final Logger log = Logger.getLogger("Eipk");
	String pageStr = "";
	String filePath = "";
	HttpServletRequest request;

	public Template(HttpServletRequest request) throws IOException {
		this.request = request;
	}

	public void getTemplate() {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		try {
			pageStr = convertStreamToString(is);
		} catch (IOException e) {
			log.log(Level.INFO, "未找到对应的模板文件:[" + filePath + "]");
		}
	}

	public void getTemplate(String view) throws IOException {
		filePath = "/net/eipk/t/kjjr/" + view + ".x";
		getTemplate();
	}

	@SuppressWarnings("unchecked")
	public void write(Model m) {
		String beginRegex = "\\#Begin\\{[^\\{\\}]+\\}";
		String endRegex = "\\#End\\{[^\\{\\}]+\\}";

		String par_regex = "\\#\\{[^\\{\\}]+\\}";
		String date_par_regex = "\\#Date\\{[^\\{\\}]+\\}";

		Pattern p_end = Pattern.compile(endRegex);
		Pattern p_par = Pattern.compile(par_regex);
		Pattern date_p_par = Pattern.compile(date_par_regex);

		Matcher m_end = p_end.matcher(pageStr);

		while (m_end.find()) {
			String c_end = m_end.group();
			String circulate = c_end.substring(5, c_end.length() - 1);
			if (m.getAttr(circulate) instanceof List<?>) {
				String head = pageStr.split("\\#Begin\\{" + circulate + "\\}")[0];
				String tail = pageStr.split("\\#End\\{" + circulate + "\\}")[1];
				List<Map<String, Object>> circulate_attrs = (List<Map<String, Object>>) m.getAttr(circulate);
				String circulate_content = "";
				for (Map<String, Object> circulate_attr : circulate_attrs) {
					String circulate_body = pageStr.split(beginRegex)[1].split(endRegex)[0];
					Matcher m_par = p_par.matcher(circulate_body);
					while (m_par.find()) {
						String s_par = m_par.group();
						s_par = s_par.substring(2, s_par.length() - 1);
						if (circulate_attr.containsKey(s_par)) {
							Object c_p_value = circulate_attr.get(s_par);
							circulate_body = setPar(circulate_body, s_par, c_p_value == null ? "" : c_p_value);
							m_par = p_par.matcher(circulate_body);
						}
					}
					Matcher date_m_par = date_p_par.matcher(circulate_body);
					while (date_m_par.find()) {
						String date_s_par = date_m_par.group();
						date_s_par = date_s_par.substring(6, date_s_par.indexOf(","));
						if (circulate_attr.containsKey(date_s_par)) {
							Object c_p_value = circulate_attr.get(date_s_par);
							circulate_body = setDatePar(circulate_body, date_s_par, c_p_value == null ? "" : c_p_value);
							date_m_par = date_p_par.matcher(circulate_body);
						}
					}
					circulate_content += circulate_body;
				}
				pageStr = head + circulate_content + tail;
			} else {
				pageStr = pageStr.split("\\#Begin\\{" + circulate + "\\}")[0]
						+ pageStr.split("\\#End\\{" + circulate + "\\}")[1];
			}
		}
		Matcher single_m_par = p_par.matcher(pageStr);
		while (single_m_par.find()) {
			String par = single_m_par.group();
			par = par.substring(2, par.length() - 1);
			if (m.getAttr(par) != null) {
				pageStr = setPar(pageStr, par, m.getAttr(par));
				single_m_par = p_par.matcher(pageStr);
			} else {
				Enumeration<String> mAttrs = m.getAttrs();
				while (mAttrs.hasMoreElements()) {
					String key = mAttrs.nextElement();
					if (m.getAttr(key) instanceof Map<?, ?>) {
						Map<String, Object> map_par = (Map<String, Object>) m.getAttr(key);
						if (map_par.containsKey(par)) {
							Object m_p_value = map_par.get(par);
							pageStr = setPar(pageStr, par, m_p_value == null ? "" : m_p_value);
							single_m_par = p_par.matcher(pageStr);
							break;
						}
					}
				}
			}
		}
		
		Matcher single_date_m_par = date_p_par.matcher(pageStr);
		while (single_date_m_par.find()) {
			String par = single_date_m_par.group();
			par = par.substring(6, par.indexOf(","));
			if (m.getAttr(par) != null) {
				pageStr = setDatePar(pageStr, par, m.getAttr(par));
				single_date_m_par = date_p_par.matcher(pageStr);
			} else {
				Enumeration<String> mAttrs = m.getAttrs();
				while (mAttrs.hasMoreElements()) {
					String key = mAttrs.nextElement();
					if (m.getAttr(key) instanceof Map<?, ?>) {
						Map<String, Object> map_par = (Map<String, Object>) m.getAttr(key);
						if (map_par.containsKey(par)) {
							Object m_p_value = map_par.get(par);
							pageStr = setDatePar(pageStr, par, m_p_value == null ? "" : m_p_value);
							single_date_m_par = date_p_par.matcher(pageStr);
							break;
						}
					}
				}
			}
		}
		pageStr = setPar(pageStr, "[^\\{\\}]+", "");
		pageStr = setDatePar(pageStr, "", "");
	}

	private String setPar(String src_str, String par_name, Object par_value) {
		String regex = "\\#\\{" + par_name + "\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src_str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "" + par_value.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private String setDatePar(String src_str, String par_name, Object par_value) {
		String regex = "\\#Date\\{" + par_name + ",[^\\{\\}]+\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src_str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String regex_value = m.group();
			String format = regex_value.substring(regex_value.indexOf(',') + 1, regex_value.length() - 1);
			if (par_value != null && !par_value.equals(""))
				m.appendReplacement(sb, SysUtil.formatDate((Date) par_value, format));
			else
				m.appendReplacement(sb, SysUtil.formatDate(new Date(), format));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public void out(HttpServletResponse response) {
		try {
			byte[] buff = pageStr.getBytes();
			response.setContentLength(buff.length);
			response.getOutputStream().write(buff);
		} catch (IOException e) {
			log.log(Level.INFO, "net.eipk.core.mvc.Template.out():" + e.getMessage());
		}
	}
	//把数据流转换为字符串
	private String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} catch (Exception e) {
				log.log(Level.INFO, "net.eipk.core.mvc.Template.convertStreamToString():" + e.getMessage());
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}