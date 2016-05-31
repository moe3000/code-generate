<script type="text/javascript">
    $(function() {
        $.fn.treeGridOptions.formSubFun("#form",'/modules/${moduleName}${subModuleName}/${className}Add.html');
    });
</script>
<div class="g-win740 popWin">
    <form id="form"  method="post">
        <#list list as item>
        <#if item.columnName != "id">
        <div class="g-list">
            <span class="j-label">${item.columnComment}：</span>
            <#if item.dataType == "Date">
            <input type="text" name="${className}.${item.columnName}" class="easyui-validatebox" data-options="required: true,validType:'shortDate'" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            <#else>
            <input type="text" name="${className}.${item.columnName}" class="easyui-validatebox" data-options="required: true" />
            </#if>
        </div>
        </#if>
        </#list>
        <div class="g-btn">
            <input name="${className}AddToken" value="${r'$'}{${className}AddToken}" type="hidden">
        </div>    
    </form>
</div>
<#if hasDate>
<script src="${r'$'}{theme_dir}/js/My97DatePicker4.8b3/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</#if>