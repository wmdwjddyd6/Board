package com.min.board.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
*
* 예외 처리를 관리하기 위해 모든 Exception을 Controller로 throw (일부 제외)
* Controller로 throw된 예외를 본 클래스에서 처리
*
* */
@ControllerAdvice
public class BoardExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public void exceptionHandler(Exception e) {
        logger.info(e.getClass().getName());
        logger.error("error : " + e.getMessage());
    }
}
