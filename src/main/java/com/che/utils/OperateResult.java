package com.che.utils;

import java.io.Serializable;

public class OperateResult implements Serializable {

	private static final long serialVersionUID = 3069442418120706152L;

	private int result = 1; // 结果 1成功，0失败；20000：表示存款返回失败
	private String errInfo; // 错误信息
	private Object resultObject;

	public OperateResult() {

	}

	public OperateResult(Object resultObject) {
		this.resultObject = resultObject;
	}

	public OperateResult(int result, String errInfo) {
		this.result = result;
		this.errInfo = errInfo;
	}

	public OperateResult(int result, String errInfo, String resultObject) {
		this.result = result;
		this.errInfo = errInfo;
		this.resultObject = resultObject;
	}

	public boolean isSuccess() {
		return result == 1;
	}

	public OperateResult(int result, String errInfo, Object Object) {
		this.result = result;
		this.errInfo = errInfo;
		this.resultObject = Object;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public static OperateResult getOperateResult(ResultBean res) {
		OperateResult r = new OperateResult(res.getCode(), res.getMsg(), res.getData());
		return r;
	}

}
