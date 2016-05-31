<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd" >
<!--感谢ibatis持久层框架 2015-05 by wu-->
<sqlMap namespace="${className}">
    <!--xml内部引用别名-->
	<typeAlias alias="${smallClassName}" type="${domainPath}.${className}" />
	
	<!--返回MAP-->		
	<resultMap id="${smallClassName}Result" class="${smallClassName}">
	<#list columnList as column>		
		<result column="${column.columnName}" property="${column.bigPropertyName}" />
	</#list>
	</resultMap>
	
	<!--基本的sql查询字段 公共引用...-->
	<sql id="base${className}SelectSql">
		<#list columnList as column>
			${column.columnName}<#if column_index+1 != columnListSize>,
			<#else>
			
			</#if>
		</#list>
	</sql>
	
	<!--基本的sql查询条件公共引用...-->
	<sql id="base${className}WhereSql">
		<dynamic>
	    	<isNotEmpty prepend="and" property="id">
			      id = #id#
			</isNotEmpty>
			<#list addColumnList as column>
			<isNotEmpty prepend="and" property="${column.bigPropertyName}">
			      ${column.columnName} = #${column.bigPropertyName}#
			</isNotEmpty>
			</#list>
		</dynamic>
		<isNotEmpty prepend="and" property="definex">
			$definex$
		</isNotEmpty>
	</sql>
	
	<!--增加-->
	<insert id="insert" parameterClass="${smallClassName}">
		insert into ${tableName}(
		<#list addColumnList as column>
			${column.columnName}<#if column_index+1 != addColumnListSize>,
			<#else>
				
			</#if>
		</#list>
		)
		values (
		<#list addColumnList as column>
			#${column.bigPropertyName}#<#if column_index+1 != addColumnListSize>,
			<#else>
				
			</#if>
		</#list>
		)
		
		<selectKey resultClass="long" keyProperty="id" >
			SELECT LAST_INSERT_ID() AS ID
		</selectKey> 	
	</insert>
 	
 	<!--根据条件获取对象记录-->
	<select id="getItemInfoByMap" resultMap="${smallClassName}Result">
	    select 
		<include refid="base${className}SelectSql" />			
	    from ${tableName} 
	    where 1=1  
		<include refid="base${className}WhereSql"/>	
		order by id desc
		<isNotNull property="limit">
			limit #limit# 
		</isNotNull>
	</select>
 	
 	<!--根据条件获取列表记录-->
	<select id="getItemListByMap" resultMap="${smallClassName}Result">
	    select 
		<include refid="base${className}SelectSql" />			
	    from ${tableName} 
	    where 1=1  
		<include refid="base${className}WhereSql"/>	
		order by id desc
		<isNotNull property="limit">
			limit #limit# 
		</isNotNull>
	</select>
 	
 	<!--根据条件获取记录-->
	<select id="get${className}List" resultMap="${smallClassName}Result">
	    select 
		<include refid="base${className}SelectSql" />			
	    from ${tableName} 
	    where 1=1  
		<include refid="base${className}WhereSql"/>	
		order by id desc
		<isNotNull property="limit">
			limit #limit# 
		</isNotNull>
	</select>
 	
 	<!--分页列表查询-->
	<select id="getPageList" resultMap="${smallClassName}Result" parameterClass="java.util.HashMap" >
		select 
		<include refid="base${className}SelectSql" />		
	    from ${tableName}
	    where 1=1
		<include refid="base${className}WhereSql"/>	
		order by id desc
	 	limit #startRow#, #pageSize#
	</select>
  
    <!--分页记录数-->
	<select id="getPageCount" resultClass="java.lang.Long" parameterClass="java.util.HashMap" >
		select 
			count(*)		
	    from ${tableName}
	    where 1=1
		<include refid="base${className}WhereSql"/>
	</select>
	
	<!--根据ID查询单条记录-->
 	<select id="getItemById" parameterClass="long" resultMap="${smallClassName}Result">
		select
        <include refid="base${className}SelectSql" />
		from ${tableName}
		where
			id = #id#
	</select>
	
	<!--根据多个IDS查询多条记录-->
	<select id="getItemListByIds" parameterClass="java.util.HashMap" resultMap="${smallClassName}Result">
		select
		<include refid="base${className}SelectSql" />
		from ${tableName}
		where
		<iterate open="(" close=")" conjunction="," property="ids">
			#id[]#
		</iterate>
	</select>
	
	<!--更新所有字段-->
	<update id="update" parameterClass="${smallClassName}">
		update ${tableName}
		set
		<#list addColumnList as column>
			${column.columnName}=#${column.bigPropertyName}#<#if column_index+1 != addColumnListSize>,
			<#else>
				
			</#if>
		</#list>
		where
			id = #id#
	</update>
	
	<!--通过ID更新会员，更新不影响其它字段值 -->
	<update id="update${className}ById" parameterClass="java.util.HashMap">
		update ${tableName}
		<dynamic prepend="set">
		<#list addColumnList as column>
		    <isNotNull property="${column.bigPropertyName}" prepend="," removeFirstPrepend="true">  
		         ${column.columnName} = #${column.bigPropertyName}#
		    </isNotNull>
		</#list>
		</dynamic>
		where
			id = #id#
	</update>
	
	<!--根据ID删除-->
	<delete id="deleteById" parameterClass="long">
		delete from ${tableName}
		where
		id  = #id#
 	</delete>
 	
 	<!--批量删除-->
 	<delete id="deleteByIds" parameterClass="java.util.HashMap">
		delete from ${tableName}
		where
		<iterate open="(" close=")" conjunction="," property="ids">
			#id[]#
		</iterate>
 	</delete> 
		
</sqlMap>