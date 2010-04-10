package com.mystatusonline.webapp.util;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 24, 2010 Time: 11:57:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RecaptchaHelper {
  
  private static String publickey = "6Ld4bAsAAAAAAOofTXTk0DBx9QHvbABgZR9R4Pg1";
	private static String privatekey = "6Ld4bAsAAAAAACtoUUz2gbw6s1l2cMgrzw1sfgCF";

	private static ReCaptcha instance = ReCaptchaFactory.newReCaptcha(publickey, privatekey, false);

	public static ReCaptcha getReCaptchaInstance() {
		return instance;
	}

  public static ReCaptchaResponse validateResponse(HttpServletRequest request) {
    ReCaptcha captcha = getReCaptchaInstance();
    String challengeField = request.getParameter("recaptcha_challenge_field");
    String responseField = request.getParameter("recaptcha_response_field");
    return captcha.checkAnswer(request.getRemoteAddr(), challengeField, responseField);    
  }
}
