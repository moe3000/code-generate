package ${packageName}.${moduleName}.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ${functionName}实体
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
public class ${ClassName} implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	<#list list as item>
    /**
     * ${item.columnComment}
     */
    <#if item.columnName == "id">
    private Long ${item.columnName};
    <#else>
    private ${item.dataType} ${item.columnName};
    </#if>
    </#list>
    
    <#list list as item>
    /**
     * 获取${item.columnComment}
     * 
     * @return ${item.columnComment}
     */
    <#if item.columnName == "id">
    public Long get${item.columnNameUpper}(){
        return ${item.columnName};
    }
    
    /**
     * 设置${item.columnComment}
     * 
     * @param ${item.columnName} 要设置的${item.columnComment}
     */
    public void set${item.columnNameUpper}(Long ${item.columnName}){
        this.${item.columnName} = ${item.columnName};
    }
    <#else>
    public ${item.dataType} get${item.columnNameUpper}(){
        return ${item.columnName};
    }
    
    /**
     * 设置${item.columnComment}
     * 
     * @param ${item.columnName} 要设置的${item.columnComment}
     */
    public void set${item.columnNameUpper}(${item.dataType} ${item.columnName}){
        this.${item.columnName} = ${item.columnName};
    }
    </#if>
    </#list>
}


