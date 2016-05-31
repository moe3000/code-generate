package ${packageName}.${moduleName}.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.eloan.common.domain.query.Pagination;

import ${packageName}.${moduleName}.dao.${ClassName}Dao;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

/**
 * ${functionName}Service
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
@Service(value = "${className}Service")
public class ${ClassName}ServiceImpl extends BaseServiceImpl implements ${ClassName}Service {
    private static final Logger log = LoggerFactory.getLogger(${ClassName}ServiceImpl.class);
    
    @Autowired
    private ${ClassName}Dao ${className}Dao;
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean add${ClassName}(${ClassName} ${className})  throws ServiceException{
    	boolean isAdd = false;
    	try{
    		isAdd = ${className}Dao.insert(${className}) > 0 ? true : false;
    	}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
       return isAdd;
    }
    
    public ${ClassName} get${ClassName}ById(Long id) throws ServiceException{
    	${ClassName} retClass;
    	try{
    		retClass = ${className}Dao.getItem(id, "${ClassName}");
    	}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
        return retClass;
    }
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean ${className}Update(${ClassName} ${className}) throws ServiceException{
    	boolean isUpdate = false;
    	try{
    		isUpdate = ${className}Dao.update(${className});
    	}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
        return  isUpdate;

    }
    
    @Transactional(rollbackFor=Exception.class)
    public Boolean delete${ClassName}(Long id) throws ServiceException{
    	boolean isDelete = false;
    	try{
    		isDelete = ${className}Dao.delete(id, "${ClassName}");
    	}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
       return  isDelete;
    }
    
    
    public Pagination get${ClassName}PageList(Pagination pagination, Map<String , Object> map) throws ServiceException{
    	Pagination page;
    	try{
    		page = ${className}Dao.getPageListByMap(pagination, map, "${ClassName}");
    	}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
       return page;
    }
    
   public List<${ClassName}> get${ClassName}List(Map<String , Object> map) throws ServiceException{
   		List<${ClassName}> list;
   		try{
   			list = ${className}Dao.getItemListByMap(map, "${ClassName}");
   		}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
        return list;
   }
   
   @Transactional(rollbackFor=Exception.class)
   public Boolean update${ClassName}ById(Map<String , Object> map) throws ServiceException{
   		boolean isUpdate = false;
   		try{
   			isUpdate = ${className}Dao.update${ClassName}ById(map);
   		}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
        return  isUpdate;
   }
   
   	@Override
	public List<? extends ${ClassName}> get${ClassName}PageList(Map<String, Object> mapdata) throws ServiceException {
		List<? extends ${ClassName}> list;
		try{
		    list = ${className}Dao.get${ClassName}PageList(mapdata);
		}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
		return list; 
	}

	@Override
	public int get${ClassName}Count(Map<String, Object> data) throws ServiceException{
		int retNum = 0;
		try{
			retNum = ${className}Dao.get${ClassName}Count(data);
		}catch (PersistentDataException e) {
			e.printStackTrace();
			throw new ServiceException(400, e.getMessage());
		}
		return retNum;
	}
    
}