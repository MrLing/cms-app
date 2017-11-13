<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>考勤管理系统</title>
  <!-- 导入主题样式文件 -->
  <link rel="stylesheet" href="/easyui/themes/gray/easyui.css">
  <!-- 图标 -->
  <link rel="stylesheet" href="/easyui/themes/icon.css">
  <link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
  <!-- 项目公共样式 -->
  <link rel="stylesheet" href="/css/app.css">
  <!-- 第一脚本：jquery，必须是在第一位 -->
  <script src="/easyui/jquery.min.js" charset="utf-8"></script>
  <!-- easyui的核心 -->
  <script src="/easyui/jquery.easyui.min.js" charset="utf-8"></script>
  <!-- Easyui的扩展 -->
  <script src="/easyui/jquery.edatagrid.js" charset="utf-8"></script>
  <!-- Easyui的国际化 -->
  <script src="/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
  <!-- Easyui的bug修复包 -->
  <script src="/easyui/fixed.js" charset="utf-8"></script>
  <script src="/js/lib/xss.js" charset="utf-8"></script>

  <!--权限资源-->
  <script src="/resource" charset="utf-8"></script>

  <!--webSocket的支持-->
  <script src="/js/lib/sockjs.min.js" charset="utf-8"></script>

  <script src="/js/lib/vue.js" charset="utf-8"></script>

  <script>
    var MEMBER = {
      id:${s_member.id},
      realName: '${s_member.realName}',
      userName: '${s_member.userName}'
    };
  </script>
  <script src="/js/require.js" charset="utf-8" data-main="js/app" defer async="true"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height: 70px;overflow: hidden;padding: 0 10px;">
  <div class="user-info">
    <span class="item" id="public_change_info"><i class="fa fa-user"></i>欢迎您，${s_member.realName}</span>
    <span class="item" id="public_change_password"><i class="fa fa-user"></i> 修改密码</span>
    <a class="item" href="/logout"><i class="fa fa-sign-out"></i> 退出</a>
  </div>
  <h1>考勤管理系统</h1>
</div>
<div title="菜单" data-options="region:'west',iconCls:'fa fa-list'" style="width: 200px">
  <div class="easyui-accordion" data-options="fit:true,border:false">
  <#list menus as menu>
	  <#if !menu.parent??>
        <div title="${menu.resName}" data-options="iconCls:'fa fa-cogs'">
          <ul class="crm-menu">
			  <#list menus as child>
				  <#if child.parent?? && child.parent.id == menu.id>
                    <li data-url="${child.menuUrl}">${child.resName}</li>
				  </#if>
			  </#list>
          </ul>
        </div>
	  </#if>
  </#list>
  </div>
</div>
<div data-options="region:'center',href:'/desktop'">

</div>
<div id="footer" data-options="region:'south'" style="height:20px;text-align: center;line-height: 20px;overflow: hidden;">
  <div id="online" class="online">
    当前在线人数：<span v-text="online"></span>
  </div>
  Copyright © 2017 考勤管理系统 v1.0 Powered by <a href="http://www.nut.edu.cn/">peng wang</a>

  <div id="online_list" class="online-list">
    <div class="online-list-header">
      <i class="fa fa-close"></i>
      <span>系统在线用户</span>
    </div>
    <div class="online-list-users">
      <ul>
        <li v-for="(user,i) in onlineUser" :id="'user'+user.uid" :key="user" :index="i" @click="sendMsg(user,${s_member.id})">
          <span class="online-user-avator">
            <i class="fa fa-user"></i>
          </span>
          <span v-text="user.realName"></span>
        </li>
      </ul>
    </div>
  </div>
</div>
</body>
</html>