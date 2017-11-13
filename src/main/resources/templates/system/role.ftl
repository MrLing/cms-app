<div class="easyui-layout" fit="true" >
	<div data-options="region:'center'" border="false">
		<table id="role_dg" title="角色管理"></table>
	</div>
	<div id="role-resource-panel" style="width:200px;border-bottom: none;" data-options="headerCls:'noborder',region:'east',collapsible:false,tools:'#role-resource-tools'" title="请选择角色">
		<!-- 资源树 -->
		<ul id="role-resource-tree"></ul>
	</div>
	<div id="role-resource-tools">
		<#if resourceKey?seq_contains('reole-resource-save')>
		<a class="fa fa-save" id="role-resource-save"></a>
		</#if>
	</div>
</div>