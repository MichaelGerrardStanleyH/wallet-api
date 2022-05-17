package com.michael.walletapi.exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String str){
        super(str);
    }
}
