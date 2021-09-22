package se.fortnox.codetest.fortnoxexpress.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import se.fortnox.codetest.fortnoxexpress.aspect.WebLog;
import se.fortnox.codetest.fortnoxexpress.exception.BizException;
import se.fortnox.codetest.fortnoxexpress.exception.ErrorEnum;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    @WebLog(description = "custom error controller")
    @RequestMapping
    public void error() throws Exception {
        throw new BizException(ErrorEnum.NOT_FOUND);
    }
}

