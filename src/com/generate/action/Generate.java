package com.generate.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 */
public class Generate {
	
	private static Logger logger = LoggerFactory.getLogger(Generate.class);
	
	private static boolean hasDate = false;

	public static void main(String[] args) {
		try {
	
		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		// 主要提供基本功能模块代码生成。
		// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{subModuleName}/{className}
		
		// packageName 包名
		String packageName = "com.generate.code";
		
		String moduleName = "system";			// 模块名，例：sys
		String subModuleName = "";				// 子模块名（可选） 
		String classAuthor = "lyc1";		// 类作者，例：lhm
		String functionName = "数据交互日志表";			// 功能名，例：用户
		String table_name = "sys_transfer_log";


		Boolean needsDomain = true;  // 是否生成实体类
		Boolean needsAction = false;  // 是否生成Action
		Boolean needsService = true;  // 是否生成Service和ServiceImpl
		Boolean needsDao = true;  // 是否生成Dao和DaoImpl
		Boolean needsManager = false;  // 是否生成Manager页面
		Boolean needsAddPage = false;  // 是否生成添加页面
		Boolean needsEditPage = false;  // 是否生成编辑页面


		// 是否启用生成工具
		Boolean isEnable = true;			
		
		// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================
		
		if (!isEnable){
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}
		
		
		//数据库表操作
		Connection connection = Generate.getDblink();
		Statement stateMent_table = (Statement)connection.createStatement();
		//遍历表
		ResultSet tables = stateMent_table.executeQuery("SHOW TABLES FROM eloan_360yd");//查询数据库所有表 eloan是数据库名
		
		while(tables.next()){
			table_name = tables.getString("Tables_in_eloan_360yd");//表名 Tables_in_eloan是字段名
			
			if(table_name.indexOf("sys_transfer_log") == -1){
				continue;
			}
			
			//数据库表操作
			Connection connection2 = Generate.getDblink();
			Statement stateMent_table1 = (Statement)connection2.createStatement();
			ResultSet table_comment = stateMent_table1.executeQuery("SELECT TABLE_COMMENT as comments FROM INFORMATION_SCHEMA.TABLES where table_name = '"+table_name+"'");//查询数据库所有表 eloan是数据库名
			
			while (table_comment.next()) {
				String name = (String) table_comment.getString("comments");
				functionName = name;
				
			}
			System.out.println("========================="+functionName);
			
			/*if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName) 
					|| StringUtils.isBlank(table_name) || StringUtils.isBlank(functionName)){
				logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
				return;
			}*/
			
			//String cn = table_name.substring(4);
			String cn =  table_name;
			
			String className = toUpper(cn.substring(0));// 类名
			System.out.println("classname="+className);
			// 获取文件分隔符
			String separator = File.separator;
			
			// 获取工程路径
			File projectPath = new DefaultResourceLoader().getResource("").getFile();
			while(!new File(projectPath.getPath()+separator+"src").exists()){
				projectPath = projectPath.getParentFile();
			}
			logger.info("Project Path: {}", projectPath);
			
			// 模板文件路径
			String tplPath = StringUtils.replace(projectPath+"/src/com/generate/action/template", "/", separator);
			logger.info("Template Path: {}", tplPath);
			
			// Java文件路径
			String javaPath = StringUtils.replaceEach(projectPath+"/src/"+StringUtils.lowerCase(packageName), new String[]{"/", "."}, new String[]{separator, separator});
			logger.info("Java Path: {}", javaPath);
			
			// 视图文件路径
			String viewPath = StringUtils.replace(projectPath+"/src/com/generate/action/temp/", "/", separator);
			logger.info("View Path: {}", viewPath);
			
			// 代码模板配置
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(tplPath));
	
			// 定义模板变量
			Map<String, Object> model = Maps.newHashMap();
			model.put("packageName", StringUtils.lowerCase(packageName));
			model.put("moduleName", StringUtils.lowerCase(moduleName));
			model.put("subModuleName", StringUtils.isNotBlank(subModuleName)?"/"+StringUtils.lowerCase(subModuleName):"");
			model.put("className", StringUtils.uncapitalize(className));
			model.put("ClassName", StringUtils.capitalize(className));
			model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Tools");
			model.put("classDate", getDate());
			model.put("functionName", functionName);
			model.put("tableName", table_name);
			model.put("urlPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
					?"/"+StringUtils.lowerCase(subModuleName):"")+"/"+model.get("className"));
			model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
					model.get("urlPrefix"));
			model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
					?":"+StringUtils.lowerCase(subModuleName):"")+":"+model.get("className"));
			
			// 表数据取得
			model.put("list", getList(table_name));
			model.put("hasDate", hasDate);
			
			Template template = null;
			String content = "";
			String filePath = "";
	
			
			// 生成 dbmapping
			/*if (needsDomain) {
				template = cfg.getTemplate("dbmap.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = "e:\\";
				writeFile(content, filePath);
				logger.info("Domain: {}", filePath);
			}*/
			
			// 生成 domain
			if (needsDomain) {
				template = cfg.getTemplate("domain.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"domain"
						+separator+model.get("ClassName")+".java";
				writeFile(content, filePath);
				logger.info("Domain: {}", filePath);
			}
	
			
			// 生成 Dao
			if (needsDao) {
				template = cfg.getTemplate("dao.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"dao"+separator
						+model.get("ClassName")+"Dao.java";
				writeFile(content, filePath);
				logger.info("Dao: {}", filePath);
				// 生成DaoImpl
				template = cfg.getTemplate("daoImpl.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"dao"+separator+"impl"+separator
						+model.get("ClassName")+"DaoImpl.java";
				writeFile(content, filePath);
				logger.info("daoImpl: {}", filePath);
			}
	
			
			// 生成 Service
			if (needsService) {
				template = cfg.getTemplate("service.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"service"+separator
						+model.get("ClassName")+"Service.java";
				writeFile(content, filePath);
				logger.info("Service: {}", filePath);
				// 生成 ServiceImpl
				template = cfg.getTemplate("serviceImpl.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"service"+separator+"impl"+separator
						+model.get("ClassName")+"ServiceImpl.java";
				writeFile(content, filePath);
				logger.info("serviceImpl: {}", filePath);
			}
			
			// 生成 Action
			if (needsAction) {
				template = cfg.getTemplate("action.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = javaPath+separator+model.get("moduleName")+separator+"web"+separator+"action"+separator
						+model.get("ClassName")+"Action.java";
				writeFile(content, filePath);
				logger.info("Action: {}", filePath);
			}
	
			// 生成 manager
			if (needsManager) {
				template = cfg.getTemplate("manager.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName").toString(),".")
						+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
						+separator+model.get("className")+"Manager.ftl";
				writeFile(content, filePath);
				logger.info("manager: {}", filePath);		
			}
	
			// 生成 addPage
			/*if (needsAddPage) {
				template = cfg.getTemplate("addPage.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName").toString(),".")
						+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
						+separator+model.get("className")+"AddPage.ftl";
				writeFile(content, filePath);
				logger.info("addPage: {}", filePath);
			}*/
	
			// 生成 editPage
			/*if (needsEditPage) {
				template = cfg.getTemplate("editPage.ftl");
				content = FreeMarkers.renderTemplate(template, model);
				filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName").toString(),".")
						+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
						+separator+model.get("className")+"EditPage.ftl";
				writeFile(content, filePath);
				logger.info("editPage: {}", filePath);
			}*/
		

			logger.info("Generate Success.");
		}
		tables.close();
		stateMent_table.close();
		connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取表信息
	 * @param table_name
	 * @return 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年9月26日
	 */
	public static List<Table> getList(String table_name){
		// 数据库连接
		String table_schema = "eloan_360yd";	
		Connection conn = null;
		Statement stmt = null;
		
		List<Table> list = new ArrayList<Table>();
		conn = Generate.getDblink();
		
		try {
				stmt = (Statement)conn.createStatement();		
				String sql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM INFORMATION_SCHEMA. `COLUMNS` WHERE table_name = '"
					+ table_name + "'  AND table_schema = '" + table_schema + "'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Table table = new Table();
					table.setColumnName(toUpper(rs.getString("COLUMN_NAME").replace("rd_", "")));//如果没有前辍的，这里需要修改。需要前辍的也需要修改
					table.setColumnNameUpper( StringUtils.capitalize(table.getColumnName()));
					table.setDataType(sqlType2JavaType(rs.getString("DATA_TYPE")));
					if (table.getDataType().equals("Date")) {
						hasDate = true;
					}
					table.setColumnComment(rs.getString("COLUMN_COMMENT"));
					list.add(table);
				}	
				rs.close();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return list;
	}
	
	/**
	 * 数据库连接
	 * @return 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年9月26日
	 */
	public static Connection getDblink(){
		Connection conn = null;
		// 数据库连接
		String user = "root";
		String password = "erongdu.com";
		String url = "jdbc:mysql://10.0.5.141:3306/eloan_JD?useUnicode=true&characterEncoding=utf8";
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载mysq驱动
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * “_”+小写 转成大写字母
	 * 
	 * @param str
	 * @return
	 */
	private static String toUpper(String str) {
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '_') {
				sb.append(String.valueOf(charArr[i + 1]).toUpperCase());
				i = i + 1;
			} else {
				sb.append(charArr[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private static String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Long";
		}else if(sqlType.equalsIgnoreCase("Integer")){ 
		    return "Long";
		}else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("double")){
			return "Double";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return "String";
	}
	
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (createFile(filePath)){
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			logger.debug("文件 " + descFileName + " 已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			logger.debug(descFileName + " 为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				logger.debug("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				logger.debug(descFileName + " 文件创建成功!");
				return true;
			} else {
				logger.debug(descFileName + " 文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(descFileName + " 文件创建失败!");
			return false;
		}
	}
	
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
}
