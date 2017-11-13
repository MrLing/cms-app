define(function () {
  return function () {
    var dg = $("#role_dg");
    var resourceTree = $("#role-resource-tree");
    var resourcePanel = $("#role-resource-panel");
    var currentRoelId;

    // 使用edatagrid，需要而外导入edatagrid扩展
    dg.edatagrid({
      url: '/system/role/list',
      saveUrl: '/system/role/save',
      updateUrl: '/system/role/update',
      destroyUrl: '/system/role/delete',
      emptyMsg: "还未创建角色",
      idField: "id",
      fit: true,
      rownumbers: true,
      ignore: ['resource'],
      fitColumns: true,
      border: false,
      columns: [[{
        field: 'roleName',
        title: '角色名称',
        width: 50,
        editor: {
          type: 'validatebox',
          options: {
            required: true
          }
        }
      }, {
        field: 'description',
        title: '描述',
        width: 100,
        editor: {
          type: 'textbox',
          options: {
            multiline: true,
            height: 50
          }
        }
      }, {
        field: 'status',
        title: '状态',
        width: 50,
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
      }]],
      toolbar: authToolBar({
        "role-create": {
          iconCls: 'fa fa-plus-square',
          text: "创建",
          handler: function () {
            dg.edatagrid('addRow');
          }
        },
        "role-save": {
          iconCls: 'fa fa-save',
          text: "保存",
          handler: function () {
            dg.edatagrid('saveRow');
          }
        },
        "role-delete": {
          iconCls: 'fa fa-trash',
          text: "删除",
          handler: function () {
            dg.edatagrid('destroyRow');
          }
        }
      }, {
        iconCls: 'fa fa-mail-reply',
        text: "取消",
        handler: function () {
          dg.edatagrid('cancelRow');
        }
      }, {
        iconCls: 'fa fa-refresh',
        text: "刷新",
        handler: function () {
          dg.edatagrid('reload');
        }
      }),
      onError: function (index, data) {
        // 操作请求发送错误
        console.error(data);
      },
      onSelect: function (index, row) {
        if (row.roleName) {
          resourcePanel.panel("setTitle", "为[" + row.roleName + "]分配资源")
          currentRoelId = row.id;

          //清除上一个已经选中的
          var checked = resourceTree.tree('getChecked');
          $.each(checked, function () {
            resourceTree.tree('uncheck', this.target);
          });

          // 选中已有节点
          $.each(row.resource, function () {
            var node = resourceTree.tree('find', this.id);

            // 判断叶子节点才执行check方法
            resourceTree.tree('check', node.target);
          });
        }
      }
    });

    //实例化权限树
    resourceTree.tree({
      url: "/system/role/resource/tree",
      checkbox: true,
      cascadeCheck: false
    });

    $("#role-resource-save").on("click", function () {
      if (currentRoelId) {
        // 获取需要关联的资源节点
        var nodes = resourceTree.tree('getChecked', ['checked', 'indeterminate']);
        // 获取节点的ID列表
        var resourceId = [];
        $.each(nodes, function () {
          resourceId.push(this.id);
        });
        var prams = "roleId=" + currentRoelId + "&resourceId=" + resourceId.join("&resourceId=")
        // 发送请求保存关系
        $.post("/system/role/resource/save", prams, function (rsp) {
          if (rsp.success) {
            dg.datagrid("reload");
            $.messager.alert("系统提醒", "保存成功！");
          }
        });
      } else {
        $.messager.alert("系统提醒", "请先选择角色");
      }
    });
  }
});