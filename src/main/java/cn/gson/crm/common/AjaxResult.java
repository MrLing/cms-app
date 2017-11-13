package cn.gson.crm.common;

public class AjaxResult {

	private Boolean success = true;
	
	private Boolean isError = false;

	private String message;
	
	private Object data;
	
	public AjaxResult() {}
	
	public AjaxResult(Boolean success) {
		this.setSuccess(success);
	}
	
	public AjaxResult(String message) {
		this.message = message;
	}

	public AjaxResult(Boolean success, String message) {
		this.setSuccess(success);
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
		if(!success){
			isError = true;
		}
	}

	public String getMessage() {
		return message;
	}

	public AjaxResult setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getData() {
		return data;
	}

	public AjaxResult setData(Object data) {
		this.data = data;
		return this;
	}
	
	public Boolean getIsError() {
		return isError;
	}
}
