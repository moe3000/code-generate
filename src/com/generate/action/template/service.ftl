package ${packageName}.${moduleName}.service;

import java.util.List;
import java.util.Map;

import com.rongdu.eloan.common.domain.query.Pagination;

import  ${packageName}.${moduleName}.domain.${ClassName};

/**
 * ${functionName}Service
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
public interface ${ClassName}Service {

    /**
     * 添加${functionName}
     * 
     * @param ${className} ${functionName}实体
     */
    Boolean add${ClassName}(${ClassName} ${className}) throws ServiceException;

    /**
     * 查询${functionName}
     * 
     * @param id 主键ID
     * @return ${functionName}
     */
    ${ClassName} get${ClassName}ById(Long id) throws ServiceException;

    /**
     * ${functionName}修改
     * @param ${className} ${functionName}实体
     */
    Boolean ${className}Update(${ClassName} ${className}) throws ServiceException;

    /**
     * ${functionName}删除
     * 
     * @param id 主键ID
     */
    Boolean delete${ClassName}(Long id) throws ServiceException;
    
    /**
     * ${functionName}分页
     * @param page 分页参数
     * @param map 查询参数封装
     * @return 分页信息
     */
    Pagination get${ClassName}PageList(Pagination page, Map<String , Object> data) throws ServiceException;
    
    /**
     * 获取${functionName}集合
     * @param map 查询参数
     * @return ${functionName}信息
     */
    List<${ClassName}> get${ClassName}List(Map<String , Object> data) throws ServiceException;
    
    /**
     *根据ID更新  通用更新
     *@param map
     *@return Boolean
     */ 
    Boolean update${ClassName}ById(Map<String , Object> data) throws ServiceException;
    
    
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return
     */
    List<? extends ${ClassName}> get${ClassName}PageList(Map<String, Object> data) throws ServiceException;
    
    /**
     * 获取总记录数
     * @return 记录数
     */
    int get${ClassName}Count(Map<String, Object> data) throws ServiceException;
    
	
}
