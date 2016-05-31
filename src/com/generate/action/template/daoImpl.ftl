package ${packageName}.${moduleName}.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rongdu.eloan.common.dao.impl.BaseDaoImpl;
import ${packageName}.${moduleName}.dao.${ClassName}Dao;
import ${packageName}.${moduleName}.domain.${ClassName};

/**
 * ${functionName}DAO接口
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
@Repository("${className}Dao")
public class ${ClassName}DaoImpl extends BaseDaoImpl<${ClassName}> implements ${ClassName}Dao {
    private static final Logger log = LoggerFactory.getLogger(${ClassName}DaoImpl.class);
             
    public Boolean update${ClassName}ById(Map<String , Object> data) throws PersistentDataException {
    	boolean isUpdate = false;
    	try{
	    	int num = this.getSqlMapClientTemplate().update("${ClassName}.update${ClassName}ById", data);
			if (num <= 0) {
				isUpdate = false;
			}else{
				isUpdate = true;
			}
    	}catch (Exception e) {
			e.printStackTrace();
			throw new PersistentDataException(400,e.getMessage());
		}
    	return isUpdate;

    }
	   
	@SuppressWarnings("deprecation")
	@Override
	public List<? extends ${ClassName}> get${ClassName}PageList(Map<String, Object> data) throws PersistentDataException {
		List<? extends ${ClassName}> list;
		try{
			list = this.getSqlMapClientTemplate().queryForList("${ClassName}.getPageList", data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistentDataException(400,e.getMessage());
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int get${ClassName}Count(Map<String, Object> data) throws PersistentDataException{
		int retNum;
		try{
			retNum = (Integer) this.getSqlMapClientTemplate().queryForObject("${ClassName}.getPageCount",data);
		}catch (Exception e) {
			e.printStackTrace();
			throw new PersistentDataException(400,e.getMessage());
		}
		return retNum;
	}
    
}
