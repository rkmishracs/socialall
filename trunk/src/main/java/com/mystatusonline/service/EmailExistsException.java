package com.mystatusonline.service;

/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 11, 2010 Time: 6:22:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailExistsException extends TransactionRollbackException {
  public EmailExistsException(final String message) {
        super(message);
    }

}