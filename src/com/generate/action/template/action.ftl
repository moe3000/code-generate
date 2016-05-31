package ${packageName}.${moduleName}.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rongdu.eloan.common.model.PageDataList;
import com.rongdu.eloan.common.model.SearchParam;
import com.rongdu.eloan.common.model.SearchFilter.Operators;
import com.rongdu.eloan.common.utils.MessageUtil;
import com.rongdu.eloan.common.utils.StringUtil;
import com.rongdu.eloan.common.web.action.BaseAction;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

/**
 * ${functionName}Action
 * @author ${classAuthor}
 * @version 1.0
 * @since ${classDate}
 */
 
 @Controller
public class ${ClassName}Action extends BaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    
        /**
     * 转换JSON字符串用map
     */
    private Map<String, Object> map = new HashMap<String, Object>();

	@Autowired
	private ${ClassName}Service ${className}Service;
	
    /**
     * 添加${functionName}页面
     * 
     * @return 添加页面
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}AddPage")
    public String ${className}AddPage() throws Exception {
        saveToken("${className}AddToken");
        return "${className}AddPage";
    }

    /**
     * 添加${functionName}
     * 
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}Add")
    public void ${className}Add() throws Exception {
        String msg = checkToken("${className}AddToken");
        if (!"".equals(msg)) {
            this.message(msg);
            return;
        }
        ${ClassName} ${className} = (${ClassName}) this.paramModel(${ClassName}.class, "${className}");
        ${className}Service.add${ClassName}(${className});
        this.message(MessageUtil.getMessage("I10001"));
    }

    /**
     * 更新${functionName}页面
     * 
     * @return 返回页面
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}EditPage")
    public String ${className}EditPage() throws Exception {
        long id = paramLong("id");
        ${ClassName} ${className} = ${className}Service.get${ClassName}ById(id);
        request.setAttribute("${className}", ${className});
        saveToken("${className}EditToken");
        return "${className}EditPage";
    }

    /**
     * 更新${functionName}
     * 
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}Edit")
    public void ${className}Edit() throws Exception {
        String msg = checkToken("${className}EditToken");
        if (!"".equals(msg)) {
            this.message(msg);
            return;
        }
        ${ClassName} ${className} = (${ClassName}) this.paramModel(${ClassName}.class,"${className}");
        ${className}Service.${className}Update(${className});
        this.message(MessageUtil.getMessage("I10002"));
    }

    /**
     * 删除${functionName}
     * 
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}Delete")
    public void ${className}Delete() throws Exception {
        ${className}Service.delete${ClassName}(paramLong("id"));
        this.message(MessageUtil.getMessage("I10003"));
    }

    /**
     * 获取${functionName}集合
     * 
     * @return 返回页面
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}Manager")
    public String ${className}Manager() throws Exception {
        return "${className}Manager";
    }

    /**
     * 获取${functionName}集合
     * 
     * @throws Exception 异常
     */
    @RequestMapping("/modules/${moduleName}${subModuleName}/${className}List")
    public void ${className}List() throws Exception {
        int pageNumber = paramInt("page"); // 当前页数
        int pageSize = paramInt("rows"); // 每页每页总数
        SearchParam param = SearchParam.getInstance().addPage(pageNumber, pageSize);
        PageDataList<${ClassName}> pageList = ${className}Service.get${ClassName}PageList(param);
        map.put("total", pageList.getPage().getTotal());
        map.put("rows", pageList.getList());
        this.printJson(map);
    }
}
