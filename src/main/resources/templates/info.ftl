<form class="app-form" id="public_change_info_form">
  <div style="float: left;width: 250px">
    <div class="field">
      <input readonly="readonly" class="easyui-textbox" label="账号：" name="userName" style="width:100%" value="${s_member.userName}">
    </div>
    <div class="field">
      <input class="easyui-textbox" name="realName" style="width:80%" data-options="label:'姓名：',required:true" value="${s_member.realName}">
      <select class="easyui-combobox" editable="false" data-options="panelHeight:'auto',value:'${s_member.gender}'" name="gender" style="width:18%">
        <option value="BOY">男</option>
        <option value="GIRL">女</option>
      </select>
    </div>
    <div class="field">
      <input class="easyui-textbox" name="telephone" style="width:100%" data-options="label:'电话：',required:true" value="${s_member.telephone}">
    </div>
    <div class="field">
      <input class="easyui-textbox" name="email" style="width:100%" data-options="label:'邮箱：',required:true,validType:'email'" value="${s_member.email}">
    </div>
    <div class="field">
      <input class="easyui-datebox" readonly="readonly" name="hiredate" style="width:100%" data-options="label:'入职日期：',editable:false" value="${s_member.hiredate}">
    </div>
  </div>
  <div style="margin-left: 250px;text-align: center;margin-top: 30px;">
    <div>
      <input id="member-avatar" type="hidden" name="avatar">
    <#if s_member.avatar??>
      <img class="avatar-set" src="${s_member.avatar}" alt="头像" width="80px" height="80px">
    <#else>
      <#if s_member.gender =="GIRL">
          <img class="avatar-set" src="/images/default.gif" alt="头像" width="80px" height="80px">
      <#else > <img class="avatar-set" src="/images/default.jpg" alt="头像" width="80px" height="80px">
      </#if>
    </#if>
    </div>
    <div>
      <a class="easyui-linkbutton">上传头像<input id="avatar-file" style="position: absolute;left: 0;opacity: 0;height: 100%;" type="file"></a>
    </div>
  </div>
</form>