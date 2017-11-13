<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>CRM学员管理系统注册</title>
  <link rel="stylesheet" href="/css/login.css">
  <link rel="stylesheet" href="/font-awesome/css/font-awesome.min.css">
</head>
<body>
<div class="logo_box">
  <h1>注册账号</h1>
  <form action="/reg" method="post" autocomplete="off">
    <div class="input_outer">
      <i class="fa fa-address-card-o u_user"></i>
      <input required="required" name="realName" class="text" placeholder="输入姓名" type="text">
    </div>
    <div class="input_outer">
      <i class="fa fa-user-o u_user"></i>
      <input required="required" name="userName" class="text" placeholder="输入账号" type="text">
    </div>
    <div class="input_outer">
      <i class="fa fa-eye u_user"></i>
      <input required="required" placeholder="请输入密码" name="password" class="text" type="password">
    </div>
    <div style="text-align: center;margin: 10px;"><img onclick="this.src=this.src+'?r='+Date.now()" src="/verify/code"></div>
    <div class="input_outer">
      <i class="fa fa-key u_user"></i>
      <input maxlength="4" required="required" placeholder="请输入校验码" name="code" class="text" type="text">
    </div>
    <div class="mb2">
      <button class="act-but submit" style="color: #FFFFFF">注册</button>
    </div>
  <#if error??>
    <div style="text-align:center;padding: 10px;">${error}</div>
  </#if>

  </form>
</div>
</body>
</html>