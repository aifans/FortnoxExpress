package se.fortnox.codetest.fortnoxexpress.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
    @ExceptionHandler(value = BizException.class)
    @ResponseBody  
	public ApiResult bizExceptionHandler(BizException e){
    	logger.error("process biz error：{}", e.getMsg());
    	return ApiResult.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ApiResult exceptionHandler(Exception e){
    	logger.error("unknown error: {}", e);
       	return ApiResult.error(ErrorEnum.INTERNAL_SERVER_ERROR);
    }



}
