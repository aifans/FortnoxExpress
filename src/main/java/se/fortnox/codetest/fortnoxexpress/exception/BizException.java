package se.fortnox.codetest.fortnoxexpress.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String code;
	protected String msg;

	public BizException() {
		super();
	}

	public BizException(IErrorInfo errorInfoInterface) {
		super(errorInfoInterface.getCode());
		this.code = errorInfoInterface.getCode();
		this.msg = errorInfoInterface.getMsg();
	}
	
	public BizException(IErrorInfo errorInfoInterface, Throwable cause) {
		super(errorInfoInterface.getCode(), cause);
		this.code = errorInfoInterface.getCode();
		this.msg = errorInfoInterface.getMsg();
	}
	
	public BizException(String errorMsg) {
		super(errorMsg);
		this.msg = errorMsg;
	}
	
	public BizException(String errorCode, String errorMsg) {
		super(errorCode);
		this.code = errorCode;
		this.msg = errorMsg;
	}

	public BizException(String errorCode, String errorMsg, Throwable cause) {
		super(errorCode, cause);
		this.code = errorCode;
		this.msg = errorMsg;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
