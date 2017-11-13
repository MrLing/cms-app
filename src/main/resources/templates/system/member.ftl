<div class="easyui-layout" fit="true">
  <div data-options="region:'north',border:false" style="height: 80px;padding: 10px;overflow: hidden;" title="用户管理">
    <form id="member_search_from" class="searcher-form">
      <input name="userName" class="easyui-textbox field" label="账号：" labelWidth="45">
      <input name="realName" class="easyui-textbox field" label="姓名：" labelWidth="45">
      <input name="telephone" class="easyui-textbox field" label="电话：" labelWidth="45">
      <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
      <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
    </form>
  </div>
  <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
    <table id="member_dg"></table>
  </div>
</div>