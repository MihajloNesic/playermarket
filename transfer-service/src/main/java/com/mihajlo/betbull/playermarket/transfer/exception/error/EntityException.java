package com.mihajlo.betbull.playermarket.transfer.exception.error;

import com.mihajlo.betbull.playermarket.transfer.exception.BaseException;
import com.mihajlo.betbull.playermarket.transfer.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityException extends BaseException {

    public EntityException(String message) {
        super(ErrorCode.ENTITY_EXCEPTION, message);
    }
}
