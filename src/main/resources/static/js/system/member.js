define(function () {
  return function () {
    var dg = $("#member_dg");
    var searchFrom = $("#member_search_from");
    var form;

    // 使用edatagrid，需要而外导入edatagrid扩展
    dg.datagrid({
      url: '/system/member/list',
      emptyMsg: "还未创建用户",
      idField: "id",
      fit: true,
      rownumbers: true,
      fitColumns: true,
      border: false,
      pagination: true,
      singleSelect: true,
      ignore: ['roles'],
      pageSize: 30,
      columns: [[{
        field: 'realName',
        title: '姓名',
        width: 30,
        editor: {
          type: 'validatebox',
          options: {
            required: true
          }
        },
        formatter: function (val) {
          return filterXSS(val);
        }
      }, {
        field: 'gender',
        title: '性别',
        align: 'center',
        width: 20,
        editor: {
          type: 'combobox',
          options: {
            data: [{
              text: '男',
              value: "BOY"
            }, {
              text: '女',
              value: "GIRL"
            }],
            editable: false,
            required: true,
            panelHeight: 'auto'
          }
        },
        formatter: function (val) {
          return {
            "BOY": "男",
            "GIRL": "女"
          }[val];
        }
      }, {
        field: 'userName',
        title: '账号',
        width: 30,
        editor: {
          type: 'validatebox',
          options: {
            required: true,
            validType: 'userName'
          }
        },
        formatter: function (val) {
          return filterXSS(val);
        }
      }, {
        field: 'telephone',
        title: '电话',
        width: 50,
        editor: {
          type: 'validatebox',
          options: {
            required: true
          }
        }
      }, {
        field: 'email',
        title: '邮箱',
        width: 50,
        editor: {
          type: 'validatebox',
          options: {
            required: true,
            validType: 'email'
          }
        }
      }, {
        field: 'hiredate',
        title: '入职日期',
        width: 50,
        editor: {
          type: 'datebox',
          options: {
            editable: false
          }
        }
      }, {
        field: 'status',
        title: '状态',
        width: 20,
        align: 'center',
        editor: {
          type: 'checkbox',
          options: {
            on: true,
            off: false
          }
        },
        formatter: function (val, row) {
          return val ? "可用" : "禁用";
        }
      }, {
        field: 'roles',
        title: '角色',
        width: 80,
        formatter: function (val, row, index) {
          var roleList = [];
          $.each(val, function () {
            roleList.push(this.roleName);
          });
          return roleList.join(',');
        }
      },
        {
          field: 'test',
          title: '操作',
          width: 50,
          align: 'center',
          formatter: function (value, row, index) {
            return authToolBar({
              "member-edit": '<a data-id="' + row.id + '" class="ctr ctr-edit">编辑</a>',
              "member-delete": '<a data-id="' + row.id + '" class="ctr ctr-delete">删除</a>'
            }).join("");
          }
        }
      ]],
      toolbar: authToolBar({
        "member-create": {
          iconCls: 'fa fa-plus-square',
          text: "创建",
          handler: function () {
            createForm()
          }
        },
        "member-reset-password": {
          iconCls: 'fa fa-repeat',
          text: "重置密码",
          handler: function () {
            var row = dg.datagrid('getSelected');
            if (row) {
              $.messager.confirm('系统提示', '确定将【' + row.realName + "】的密码重置为：0000", function (r) {
                if (r) {
                  $.get("/system/member/password/reset", {id: row.id}, function (rsp) {
                    $.messager.alert("系统提示", "密码重置成功！");
                  })
                }
              })
            }
          }
        }
      })
    });


    /**
     * 操作按钮绑定事件
     */
    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {// 编辑按钮事件
      createForm.call(this, this.dataset.id)
    }).on('click', "a.ctr-delete", function () {// 删除按钮事件
      var id = this.dataset.id;
      $.messager.confirm("删除提醒", "确认删除此用户?", function (r) {
        if (r) {
          $.get("/system/member/delete", {id: id}, function () {
            // 数据操作成功后，对列表数据，进行刷新
            dg.datagrid("reload");
          });
        }
      });
    });

    /**
     * 搜索区域事件
     */
    searchFrom.on('click', 'a.searcher', function () {//检索
      dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {//重置
      searchFrom.form('reset');
      dg.datagrid('load', {});
    });


    /**
     * 创建表单窗口
     *
     * @returns
     */
    function createForm(id) {
      var dialog = $("<div/>", {class: 'noflow'}).dialog({
        title: (id ? "编辑" : "创建") + "用户",
        iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
        height: id ? 380 : 420,
        width: 420,
        href: '/system/member/form',
        queryParams: {
          id: id
        },
        modal: true,
        onClose: function () {
          $(this).dialog("destroy");
        },
        onLoad: function () {
          //窗口表单加载成功时执行
          form = $("#member-form");

          //这个字段比较特殊，有比较多的校验，所以单独拿出来实例化
          $("#member_userName").textbox({
            label: '账号：',
            required: true,
            validType: ['userName', 'length[6, 10]', "remote['/system/member/check','userName']"]
          })
        },
        buttons: [{
          iconCls: 'fa fa-save',
          text: '保存',
          handler: function () {
            if (form.form('validate')) {
              $.post("/system/member/save", form.serialize(), function (res) {
                dg.datagrid('reload');
                dialog.dialog('close');
              })
            }
          }
        }]
      });
    }
  }
});