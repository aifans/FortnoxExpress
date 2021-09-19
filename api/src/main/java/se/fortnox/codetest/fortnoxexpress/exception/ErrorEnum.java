package se.fortnox.codetest.fortnoxexpress.exception;

public enum ErrorEnum implements IErrorInfo {

	SUCCESS("200", "SUCCESS."),
	BODY_NOT_MATCH("400","The data format required not matched."),
	NOT_FOUND("404", "Not found."),
	INTERNAL_SERVER_ERROR("500", "Interal server error."),
	SERVER_BUSY("503","Server is busy."),

	SELECT_FAILURE("50001", "Failed to fetch data from DB."),
	INSERT_FAILURE("50002", "Failed to insert into DB."),
	DELETE_FAILURE("50003", "Failed to delete from DB."),
	UPDATE_FAILURE("50004", "Failed to update DB."),

	INVALID_ORDER_NAME("60001", "Name is required."),
	INVALID_ORDER_WEIGHT("60002", "Weight must be passive."),
	INVALID_ORDER_COUNTRY("60003", "Country is invalid."),
	INVALID_ORDER_COLOR("60004", "Color must not contain any shades of blue."),
	INVALID_ORDER_COLOR_INVALID("60005", "Color format is invalid."),
	INVALID_ORDER_COLOR_NULL("60006", "Color is not exist."),
	INVALID_ORDER_COLOR_EXIST("60007", "Color is required.")
	;

	private String code;
	private String msg;

	ErrorEnum(String resultCode, String resultMsg) {
		this.code = resultCode;
		this.msg = resultMsg;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

}
