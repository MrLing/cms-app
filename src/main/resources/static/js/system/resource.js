define(function () {
  return function () {
    var dg = $("#resource_dg");
    var form;//表单对象，当表单窗口打开的时，被赋值

    dg.treegrid({
      fit: true,
      idField: 'id',
      treeField: 'resName',
      fitColumns: true,
      border: false,
      columns: [[
        {
          field: 'resName',
          title: '资源名称',
          width: 80
        },
        {
          field: 'resKey',
          title: '资源标识',
          width: 50
        },
        {
          field: 'resType',
          title: '资源类型',
          width: 40
        },
        {
          field: 'menuUrl',
          title: '菜单URL',
          width: 50
        },
        {
          field: 'funUrls',
          title: '功能URLS',
          width: 50
        },
        {
          field: 'weight',
          title: '权重',
          align: 'center',
          width: 30
        },
        {
          field: 'status',
          title: '状态',
          width: 30,
          align: 'center',
          formatter: function (val, row) {
            return val ? "可用" : "禁用";
          }
        },
        {
          field: 'id',
          title: '操作',
          width: 50,
          align: 'center',
          formatter: function (value, row, index) {
            return authToolBar({
              "resource-edit": '<a data-id="' + value + '" class="ctr ctr-edit">编辑</a>',
              "resource-delete": '<a data-id="' + value + '" class="ctr ctr-delete">删除</a>'
            }).join("");
          }
        }]],
      toolbar: authToolBar({
        "resource-create": {
          iconCls: 'fa fa-plus-square',
          text: "创建资源",
          handler: function () {
            createForm();
          }
        }
      }),
      url: '/system/resource/list'
    });


    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {// 编辑按钮事件
      createForm.call(this, this.dataset.id)
    }).on('click', "a.ctr-delete", function () {// 删除按钮事件
      var id = this.dataset.id;
      $.messager.confirm("删除提醒", "确认删除此资源?", function (r) {
        if (r) {
          $.get("/system/resource/delete", {id: id}, function () {
            // 数据操作成功后，对列表数据，进行刷新
            dg.treegrid("reload");
          });
        }
      });
    });

    /**
     * 创建表单窗口
     *
     * @returns
     */
    function createForm(id) {
      var dialog = $("<div/>", {class: 'noflow'}).dialog({
        title: (id ? "编辑" : "创建") + "资源",
        iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
        height: 480,
        width: 420,
        href: '/system/resource/form',
        queryParams: {
          id: id
        },
        modal: true,
        onClose: function () {
          $(this).dialog("destroy");
        },
        onLoad: function () {
          //窗口表单加载成功时执行
          form = $("#resource-form");

        },
        buttons: [{
          iconCls: 'fa fa-save',
          text: '保存',
          handler: function () {

            if (form.form('validate')) {
              $.post("/system/resource/save", form.serialize(), function (res) {
                dg.treegrid('reload');
                dialog.dialog('close');
              })
            }
          }
        }]
      });
    }
  }
});