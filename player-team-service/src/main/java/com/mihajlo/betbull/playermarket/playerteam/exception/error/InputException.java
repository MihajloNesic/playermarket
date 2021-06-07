package com.mihajlo.betbull.playermarket.playerteam.exception.error;

import com.mihajlo.betbull.playermarket.playerteam.exception.BaseException;
import com.mihajlo.betbull.playermarket.playerteam.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InputException extends BaseException {

    public InputException(String message) {
        super(ErrorCode.INPUT_EXCEPTION, message);
    }
}
