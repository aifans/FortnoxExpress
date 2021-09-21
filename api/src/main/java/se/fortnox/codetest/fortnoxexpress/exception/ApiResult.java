package se.fortnox.codetest.fortnoxexpress.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ApiResult {

	private String code;
	private String message;
	private Object result;

	public ApiResult() {
	}

	public ApiResult(IErrorInfo errorInfo) {
		this.code = errorInfo.getCode();
		this.message = errorInfo.getMsg();
	}

	public static ApiResult success() {
		return success(null);
	}

	public static ApiResult success(Object data) {
		ApiResult ar = new ApiResult();
		ar.setCode(ErrorEnum.SUCCESS.getCode());
		ar.setMessage(ErrorEnum.SUCCESS.getMsg());
		ar.setResult(data);
		return ar;
	}

	public static ApiResult error(IErrorInfo errorInfo) {
		ApiResult ar = new ApiResult();
		ar.setCode(errorInfo.getCode());
		ar.setMessage(errorInfo.getMsg());
		ar.setResult(null);
		return ar;
	}

	public static ApiResult error(IErrorInfo errorInfo, Object object) {
		ApiResult ar = new ApiResult();
		ar.setCode(errorInfo.getCode());
		ar.setMessage(errorInfo.getMsg());
		ar.setResult(object);
		return ar;
	}

	public static ApiResult error(String code, String message) {
		ApiResult ar = new ApiResult();
		ar.setCode(code);
		ar.setMessage(message);
		ar.setResult(null);
		return ar;
	}

	public static ApiResult error(String code, String message, Object data) {
		ApiResult ar = new ApiResult();
		ar.setCode(code);
		ar.setMessage(message);
		ar.setResult(data);
		return ar;
	}

	public static ApiResult error(String message) {
		ApiResult ar = new ApiResult();
		ar.setCode("-1");
		ar.setMessage(message);
		ar.setResult(null);
		return ar;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
