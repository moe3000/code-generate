<!DOCTYPE html>
<html>
<head>
<title>${functionName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<${r"#"}include "../../../include/resources.html">
<script type="text/javascript">
    $.canEdit = true;
</script>
<script type="text/javascript">
    $.canDelete = true;
</script>
<script type="text/javascript">
    $.canGrant = true;
</script>
<script type="text/javascript">
   $.canAdd = true;
</script>
<script type="text/javascript">
    $("body",parent.document).find('iframe').css({"min-width":640});//设置该页面最小宽度
    var dataGrid;
    var winWidth =  800;
    var winHeight = 260;
    var addTitle = "添加${functionName}";
    var addUrl =  "/modules/${moduleName}${subModuleName}/${className}AddPage.html";
    var editTitle = "编辑${functionName}";
    var editUrl =  "/modules/${moduleName}${subModuleName}/${className}EditPage.html";
    var deleteUrl = "/modules/${moduleName}${subModuleName}/${className}Delete.html";    
    var addWinParmeters = "'{0}','"+addTitle+"',"+winWidth+","+winHeight+",'"+addUrl+"'";
    var editWinParmeters = "'{0}','"+editTitle+"',"+winWidth+","+winHeight+",'"+editUrl+"'";
    var deleteWinParmeters = "'{0}','"+deleteUrl+"'";
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${className}List.html',
            fit : true,
            fitColumns : true,
            border : false,
            pagination : true,
            idField : 'id',
            pageSize : 10,
            pageNums :1,
            pageList : [ 10, 20, 30, 40, 50 ],
            sortName : 'name',
            sortOrder : 'asc',
            checkOnSelect : false,
            selectOnCheck : false,
            nowrap : false,
            frozenColumns : [ [             
              //{ field: 'id', title: '编号', width: 50, checkbox : true},
              <#list list as item>
              <#if item.columnName != "id">
              { field: '${item.columnName}', title: '${item.columnComment}', width: 80, sortable: true },
              </#if>
              </#list>
              { field : 'action',
                title : '操作',
                width :75,
                formatter : function(value, row, index) {
                    var str = '';
                    if ($.canAdd) {
                        str += $.formatString('<img onclick="$.fn.dataGridOptions.addFun('+addWinParmeters+');" src="{1}" title="添加"/>', row.id, '${r"$"}{theme_dir}/css/images/extjs_icons/pencil_add.png');
                    }
                    str += '&nbsp;';
                    if ($.canEdit) {
                        str += $.formatString('<img onclick="$.fn.dataGridOptions.editFun('+editWinParmeters+');" src="{1}" title="编辑"/>', row.id, '${r"$"}{theme_dir}/css/images/extjs_icons/pencil.png');
                    }
                    str += '&nbsp;';                    
                    if ($.canDelete) {
                        str += $.formatString('<img onclick="$.fn.dataGridOptions.deleteFun('+deleteWinParmeters+');" src="{1}" title="删除"/>', row.id, '${r"$"}{theme_dir}/css/images/extjs_icons/cancel.png');
                    }
                    str += '&nbsp;';
                    return str;
                }
            } ] ],
            toolbar : '#toolbar',
            onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');

                $(this).datagrid('tooltip');
            },
            onRowContextMenu : function(e, rowIndex, rowData) {
                e.preventDefault();
                $(this).datagrid('unselectAll').datagrid('uncheckAll');
                $(this).datagrid('selectRow', rowIndex);
                $('#menu').menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
            }
        });
    });
</script>
</head>
<body>
    <div class="easyui-layout" data-options="fit : true,border : false">
        <div data-options="region:'north',title:'查询条件',border:false" style="height: 135px; overflow: hidden;">
            <form id="searchForm">
                <table class="table table-hover table-condensed" style="display: none;">
                    <tr>
                        <th>名称：</th>
                        <td><input type="text" name="name"/></td>
                        <td class="tdBtn">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="$.fn.dataGridOptions.searchFun('#searchForm');">查询</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="$.fn.dataGridOptions.cleanFun('#searchForm');">清空</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div data-options="region:'center',border:false">
            <table id="dataGrid"></table>
        </div>
    </div>
    </div>
    <div id="toolbar" style="display: none;">       
        <a onclick="$.fn.dataGridOptions.addFun(-1,addTitle,winWidth,winHeight,addUrl);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>             
    </div>
    <div id="menu" class="easyui-menu" style="width: 120px; display: none;">        
        <div onclick="$.fn.dataGridOptions.addFun($(this).attr('node-id'),addTitle,winWidth,winHeight,addUrl);" data-options="iconCls:'pencil_add'">增加</div>        
        <div onclick="$.fn.dataGridOptions.deleteFun($(this).attr('node-id'),deleteUrl);" data-options="iconCls:'pencil_delete'">删除</div>       
        <div onclick="$.fn.dataGridOptions.editFun($(this).attr('node-id'),editTitle,winWidth,winHeight,editUrl);" data-options="iconCls:'pencil'">编辑</div>     
    </div>
</body>
</html>
