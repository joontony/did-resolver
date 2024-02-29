package org.snubi.did.resolver.exception;

import org.snubi.did.resolver.common.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	ErrorCode errorCode;
}
