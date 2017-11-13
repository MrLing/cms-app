/**
 * 系统公共，js。理论上只存放公共业务代码。
 */
$(function () {

  //全局ajax设置
  $.ajaxSetup({
    statusCode: {
      401: function () {
        $.messager.alert("权限提醒", "您没有权限访问此资源！");
      },
      402: function () {
        $.messager.alert("登录提醒", "登录超时，请重新登录！", 'info', function () {
          location.replace("/login");
        });
      }
    }
  });

  var center = $("body").layout("panel", "center");

  center.panel('options').onLoad = function () {
    require([center.panel('options').href.substring(1)], function (model) {
      model && model(center);
    });
  };

  // 绑定菜单事件
  $(".crm-menu").on('click', 'li', function () {
    if (!$(this).hasClass('selected')) {
      // 获取center对应的panel对象

      // 刷新center区域
      center.panel("refresh", this.dataset.url);
      // 选中状态
      $(this).siblings('.selected').toggleClass().end().addClass('selected');
    }
  });


  $(".panel-title").on('click', function () {
      // 选中状态
      $(".crm-menu li").siblings('.selected').toggleClass().end().removeClass('selected');
  });

  //查看和修改用户信息
  $("#public_change_info").on('click', function () {
    var form;
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
      title: "我的资料",
      iconCls: 'fa fa-user',
      height: 320,
      width: 420,
      href: '/change/info',
      modal: true,
      onClose: function () {
        $(this).dialog("destroy");
      },
      onLoad: function () {
        //窗口表单加载成功时执行
        form = $("#public_change_info_form", this);
      },
      buttons: [{
        iconCls: 'fa fa-save',
        text: '保存',
        handler: function () {
          if (form.form('validate')) {
            $.post("/change/info", form.serialize(), function (res) {
              if (res.success) {
                dialog.dialog('close');
                location.replace('/');
              } else {
                $.messager.alert("系统提示", res.message);
              }
            })
          }
        }
      }]
    });

    /**
     * 上传头像
     */
    dialog.on('click', '#avatar-file', function () {
      //清除上一轮已经选择的文件
      this.value = "";
    }).on('change', '#avatar-file', function () {
      //上传头像，h5的搞法
      var formData = new FormData();
      formData.append('file', this.files.item(0));
      formData.append('type', "AVATAR");
      $.ajax("/attachment/upload", {
        type: 'post',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        complete: function (res) {
          var result = res.responseJSON;
          if (result.success) {
            $(".avatar-set", dialog).attr('src', result.data.filePath);
            $("#member-avatar", dialog).val(result.data.filePath);
          } else {
            $.messager.alert("系统提示", result.message);
          }
        }
      })
    });
  });

  //修改密码
  $("#public_change_password").on('click', function () {
    var form;
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
      title: "修改密码",
      iconCls: 'fa fa-lock',
      height: 220,
      width: 420,
      href: '/change/password',
      modal: true,
      onClose: function () {
        $(this).dialog("destroy");
      },
      onLoad: function () {
        //窗口表单加载成功时执行
        form = $("#public_change_password_form", this);
      },
      buttons: [{
        iconCls: 'fa fa-repeat',
        text: '确认修改',
        handler: function () {
          if (form.form('validate')) {
            $.post("/change/password", form.serialize(), function (res) {
              if (res.success) {
                dialog.dialog('close');
                location.replace('/logout');
              } else {
                $.messager.alert("系统提示", res.message);
              }
            })
          }
        }
      }]
    });
  });

  /**
   * 聊天部分
   */
  (function () {
    var vm = new Vue({
      el: '#footer',
      data: {
        onlineUser: []
      },
      computed: {
        online: function () {
          return this.onlineUser.length;
        }
      },
      methods: {
        pushUser: function (user) {
          this.onlineUser.push(user);
        },
        removeUser: function (user) {
          var index = $("#user" + user.uid).attr('index');
          this.onlineUser.splice(index, 1);
        },
        sendMsg: function (user, uid) {
          if (user.uid != uid) {
            $.messager.prompt(filterXSS(user.realName), '请输入消息内容：', function (r) {
              if (r) {
                $.post('/im/send', {uid: user.uid, content: r});
              }
            });
          }
        }
      }
    });

    var websocket = null;
    if ('WebSocket' in window) {
      websocket = new WebSocket("ws://" + location.host + "/ws/server");
    }
    else if ('MozWebSocket' in window) {
      websocket = new MozWebSocket("ws://" + location.host + "/ws/server");
    }
    else {
      websocket = new SockJS("http://" + location.host + "/sockjs/server");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;

    function onOpen(openEvt) {
      $.get("/im/user/list").success(function (rsp) {
        Vue.set(vm, 'onlineUser', rsp.data);
      });
    }

    function onMessage(evt) {
      var data = JSON.parse(evt.data);
      switch (data.tag) {
        case 'online':
          vm.pushUser(data.data);
          if (MEMBER.id != data.data.uid) {
            $.messager.show({
              title: '上线提示',
              msg: filterXSS(data.data.realName) + '已上线',
              timeout: 2000,
              showType: 'slide'
            });
          }
          break;
        case 'offline':
          vm.removeUser(data.data);
          $.messager.show({
            title: '下线提示',
            msg: filterXSS(data.data.realName) + '已下线',
            timeout: 2000,
            showType: 'slide'
          });
          break;
        case 'logout':
          websocket.close();
          $.messager.alert("系统提醒", "您的账号已经在其他地方登陆！", "info", function () {
            location.href = '/logout';
          });
          break;
        case 'message':
          $.messager.show({
            title: filterXSS(data.data.realName) + '给你发来消息',
            msg: filterXSS(data.data.message),
            timeout: 4000,
            showType: 'slide'
          });
          break;
      }
    }

    function onError() {
      alert("网络异常！请刷新！");
    }

    window.close = function () {
      if (!websocket.CLOSED)
        websocket.close();
    };

    $("#online").on('click', function () {
      $("#online_list").toggle()
    });

    $("#online_list").on('click', ".online-list-header .fa-close", function () {
      $("#online_list").hide();
    });
  })();

  /**
   * 扩展一个jq组件，获取一个json格式的表单值
   * @param ignoreNull 是否排除空值
   * @returns {*}
   */
  (function ($) {
    $.fn.formToJson = function (ignoreNull) {
      //默认剔除空值
      if (typeof ignoreNull === 'undefined') {
        ignoreNull = true
      }

      if (this.length <= 1) {
        return buildJson(this[0]);
      } else {
        //多表单的情况
        var forms = {};
        this.forEach(function (form, index) {
          var fName = $(form).attr('name');
          var key = fName ? fName : 'form' + index;
          forms[key] = buildJson(form);
        });
        return forms;
      }

      function buildJson(form) {
        var formData = new FormData(form);
        var json = {};
        formData.forEach(function (val, key) {
          if (!val && ignoreNull) {
            return
          }
          if (json[key]) {
            if (!$.isArray(json[key])) {
              json[key] = [json[key]]
            }
            json[key].push(val);
          } else {
            json[key] = val;
          }
        });
        return json;
      }
    };
  })(jQuery);
});