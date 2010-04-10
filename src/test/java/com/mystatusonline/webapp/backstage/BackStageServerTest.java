package com.mystatusonline.webapp.backstage;

import com.mystatusonline.backstageservices.SmsChecker;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;


/**
 * Created by IntelliJ IDEA.
 * User: pritesh
 * Date: Feb 26, 2010
 * Time: 10:36:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class BackStageServerTest
  extends BackStageBaseTestCase {

  private SmsChecker smsChecker = null;


  public SmsChecker getSmsChecker() {
    return smsChecker;
  }

  @Autowired
  public void setSmsChecker(SmsChecker smsChecker) {
    this.smsChecker = smsChecker;
  }

  @Test
  public void testBackStageServer()
    throws Exception {
      //checker.start();
      getSmsChecker().work();
      Assert.assertTrue(false);
  }

}
