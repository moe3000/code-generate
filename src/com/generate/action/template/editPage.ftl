<script type="text/javascript">
    $(function() {
        $.fn.dataGridOptions.formSubFun("#form",'/modules/${moduleName}${subModuleName}/${className}Edit.html');     
    });
</script>
<form id="form"  method="post">
<div class="g-officeAdd">
        <#list list as item>
        <#if item.columnName != "id">
        <div class="g-list">
            <span class="j-label">${item.columnComment}：</span>
            <#if item.dataType == "Date">
            <input type="text" name="${className}.${item.columnName}" value="${r'$'}{${className}.${item.columnName}!}" class="easyui-validatebox" data-options="required: true,validType:'shortDate'" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            <#else>
            <input type="text" name="${className}.${item.columnName}" value="${r'$'}{${className}.${item.columnName}!}" class="easyui-validatebox" data-options="required: true" />
            </#if>
        </div>
        </#if>
        </#list>
        <div class="g-btn">
            <input name="${className}EditToken" value="${r'$'}{${className}EditToken!}" type="hidden">
            <input name="${className}.addTime" value="${r'$'}{${className}.addTime!}" type="hidden">
            <input name="${className}.id" value="${r'$'}{${className}.id}" type="hidden"/>
        </div>  
</div>
</form>
<#if hasDate>
<script src="${r'$'}{theme_dir}/js/My97DatePicker4.8b3/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</#if>