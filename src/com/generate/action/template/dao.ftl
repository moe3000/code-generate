package ${packageName}.${moduleName}.dao;

import com.rongdu.eloan.common.dao.BaseDao;
import ${packageName}.${moduleName}.domain.${ClassName};

/**
 * ${functionName}DAO接口
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
@Repository("${className}Dao") 
public interface ${ClassName}Dao extends BaseDao<${ClassName}> {

    /**
     * 根据ID更新  通用更新
     * @param map
     * @return Boolean
     * @throws PersistentDataException
     */ 
    Boolean update${ClassName}ById(Map<String , Object> data) throws PersistentDataException;
    
    /**
     * 分页查询出的列表
     * @param mapdata
     * @return 分页列表
     * @throws PersistentDataException
     */
    List<? extends ${ClassName}> get${ClassName}PageList(Map<String, Object> data) throws PersistentDataException;
        
    /**
     * 获取总记录数
     * @param param
     * @return 记录数
     * @throws PersistentDataException
     */
    int get${ClassName}Count(Map<String, Object> data) throws PersistentDataException;

}
