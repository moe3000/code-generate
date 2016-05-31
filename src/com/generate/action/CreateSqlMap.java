package com.generate.action;

import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * sqlmap文件生成类
 * 改造成同时生成某数据库所有表的mapping文件
 */
public class CreateSqlMap {
	
	public static Configuration CONFIG;
	
	public static String tableName = "sys_transfer_log";
	
	private static String dbMapPackage = "config.mapping.collateral";
	
	private static String domainPackage = "com.rongdu.eloan.modules.system.domain";
	
	public static String srcPath = "src";
		
	private static String dbHost = "10.0.5.141";
	
	private static String dbPort = "3306";
	
	private static String dbName = "eloan_360yd";
	
	private static String dbUser = "root";
	
	private static String dbPassword = "erongdu.com";
		
	public static void main(String[] args) throws Exception {
		//数据库连接
		String connectionUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?user=" + dbUser  + "&password=" + dbPassword;
		Connection conn = (Connection) DriverManager.getConnection(connectionUrl);
		Statement stateMent_table = (Statement)conn.createStatement();
		
		//遍历表
		ResultSet tables = stateMent_table.executeQuery("SHOW TABLES FROM eloan_360yd");//查询数据库所有表 eloan是数据库名
	    while(tables.next()){
	    	    tableName = tables.getString("Tables_in_eloan_360yd");//表名 Tables_in_eloan是字段名
				if(tableName.indexOf("sys_transfer_log") == -1){
					continue;
				}
	    	    
	    	    
				Map<String, Object> data = new HashMap<String, Object>();
				String className = toHeadUpper(dbTableNameToPropertyName(tableName));
				data.put("className", className);
				data.put("smallClassName", toHeadLow(dbTableNameToPropertyName(tableName)));
				data.put("tableName", tableName);
				data.put("dbMapPath", dbMapPackage);
				data.put("domainPath", domainPackage);
				
				Statement stateMent_field = (Statement)conn.createStatement();
				ResultSet colSet = stateMent_field.executeQuery("select * from information_schema.COLUMNS where TABLE_NAME='" + tableName + "' and TABLE_SCHEMA='" + dbName + "'");//表有哪些字段
				List<HashMap<String, String>> columnList= new ArrayList<HashMap<String, String>>();
				List<HashMap<String, String>> addColumnList= new ArrayList<HashMap<String, String>>();
				
				while(colSet.next()) {			
					HashMap<String, String> column = new HashMap<String, String>();			
					String columnName = colSet.getString("COLUMN_NAME");
					String columnType = colSet.getString("DATA_TYPE");
					String columnComment = colSet.getString("COLUMN_COMMENT");
					
					String propertyName = dbNameToPropertyName(columnName);
					column.put("propertyName", columnName);
					column.put("columnName", columnName);
					column.put("bigPropertyName", toHeadLow(propertyName));
					//column.put("bigPropertyName", columnName.replace("rd_", ""));//为了去掉RD这样做。一般不这样做。
					column.put("columnComment", columnComment);
					if (columnType.equals("bigint")) {
						column.put("propertyType", "Long");
						data.put("hasLong", true);
					}
					else if (columnType.equals("datetime") || columnType.equals("date")) {
						column.put("propertyType", "Date");
						data.put("hasDate", true);
					}
					else if (columnType.equals("decimal")) {
						column.put("propertyType", "BigDecimal");
						data.put("hasDecimal", true);				
					}
					else if (columnType.equals("int")) {
						column.put("propertyType", "Integer");
						data.put("hasInteger", true);					
					}
					else if (columnType.equals("tinyint")) {
						String type = colSet.getString("COLUMN_TYPE");
						if (type.equals("tinyint(1)")) {
							column.put("propertyType", "Boolean");
							data.put("hasBoolean", true);	
						}
						else {
							column.put("propertyType", "Integer");
							data.put("hasInteger", true);						
						}
					}
					else if (columnType.equals("varchar") || columnType.equals("char") || columnType.equals("text")) {
						column.put("propertyType", "String");
						data.put("hasString", true);					
					}else if (columnType.equals("float")){
						column.put("propertyType", "Float");
						data.put("hasFloat", true);
					}else if (columnType.equals("time")){
						column.put("propertyType", "Time");
						data.put("hasTime", true);
					}else {
						throw new RuntimeException("未知数据类型:" + propertyName);
					}
					columnList.add(column);
					if (!columnName.equals("id") && !columnName.equals("gmt_modify") && !columnName.equals("gmt_create")) {
						addColumnList.add(column);
					}
				}
				
				stateMent_field.close();
				colSet.close();
				
				if (columnList.size() < 1) {
					throw new RuntimeException("数据表不存在");
				}
				
				data.put("columnList", columnList);
				data.put("addColumnList", addColumnList);
				
				data.put("columnListSize", columnList.size());
				data.put("addColumnListSize", addColumnList.size());
				
				// 代码模板配置
				Configuration cfg = new Configuration();
				// 获取文件分隔符
				String separator = File.separator;
				// 获取工程路径
				File projectPath = new DefaultResourceLoader().getResource("").getFile();
				//while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
				while(!new File(projectPath.getPath()+separator+"src").exists()){
					projectPath = projectPath.getParentFile();
				}
				
				// 模板文件路径
				String tplPath = StringUtils.replace(projectPath+"/src/com/generate/action/template", "/", separator);
				cfg.setDirectoryForTemplateLoading(new File(tplPath));
				Template template = cfg.getTemplate("dbmap.ftl");
				String content = FreeMarkers.renderTemplate(template, data);
				
				//Generate.writeFile(content, "F:\\temp\\mapping\\"+toHeadLow(dbTableNameToPropertyName(tableName))+"_mapping.xml");
				Generate.writeFile(content, projectPath+"/src/com/generate/mapping/"+toHeadLow(dbTableNameToPropertyName(tableName))+"_mapping.xml");  //注，这里是映射文件生成的目录。
				System.out.printf(content);
				
				System.out.printf("OK");
		
	    }
	    tables.close();
	    stateMent_table.close();
	    conn.close();
	}
	
	
	public static String dbNameToPropertyName(String dbName) {
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < dbName.length(); i ++) {
			char c = dbName.charAt(i);
			if (c == '_') {
				i ++;
				if (i == dbName.length())
					break;
				c = dbName.charAt(i);
				builder.append(Character.toUpperCase(c));
			}
			else {
				builder.append(c);
			}
		}
		return builder.toString();
	}
	
	public static String dbTableNameToPropertyName(String dbName) {
		dbName = dbName.substring(0, dbName.length());
		return CreateSqlMap.dbNameToPropertyName(dbName);
	}
	
	public static String toHeadUpper(String data) {
		return String.valueOf(Character.toUpperCase(data.charAt(0))) + data.substring(1);
	}
	
	public static String toHeadLow(String data) {
		return String.valueOf(Character.toLowerCase(data.charAt(0))) + data.substring(1);
	}
	

}
