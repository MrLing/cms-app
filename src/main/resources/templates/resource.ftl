(function(w){
	var resourceKey = ${resourceKey};
	/**
	 * 工具栏的权限过滤
	 * @param tools
	 * @returns
	 */
	function authToolBar(tools) {
		var toolbars = [];

		$.each(tools, function(key, btn) {
			if (resourceKey.indexOf(key) != -1) {
				toolbars.push(btn);
			}
		});
		if(arguments.length > 1){
			for(var i = 1;i < arguments.length; i++){
				toolbars.push(arguments[i])
			}
		}
		return toolbars;
	}
	w.authToolBar = authToolBar;
})(window);