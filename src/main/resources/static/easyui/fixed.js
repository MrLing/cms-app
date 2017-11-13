/**
 * 修复 Easyui DataGrid的editors的checkbox不能判断boolean类型值bug
 */
$.extend($.fn.datagrid.defaults.editors.checkbox,{
	setValue:function(target,val){
		var checked = $(target).val() == val;
		if(typeof val == 'boolean'){
			checked = val;
		}
        $(target)._propAttr("checked", checked);
	}
});

/**
 * 校验扩展
 */
$.extend($.fn.validatebox.defaults.rules, {
    userName: {
        validator: function(value, param){
        		var reg  = /^[a-z]+$/i;
            return reg.test(value);
        },
        message: '用户名只能为小写字母。'
    }
});