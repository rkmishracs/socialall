package com.mystatusonline.service;

/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 25, 2010 Time: 11:37:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionRollbackException extends Exception{

  public TransactionRollbackException() {
  }

  public TransactionRollbackException(String message) {
    super(message);
  }
}
