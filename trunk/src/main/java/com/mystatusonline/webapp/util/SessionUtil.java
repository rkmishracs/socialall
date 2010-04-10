package com.mystatusonline.webapp.util;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mystatusonline.model.Person;
import com.mystatusonline.service.PersonNotLoggedInException;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 15, 2010 Time: 2:47:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionUtil {

    private static final String LOGGED_IN_PERSON = "loggedInPerson";
    private static final String NAME = "loggedInPersonName";

    public static void saveLoggedInPerson(Person loggedInPerson,
                                          HttpSession session) {
        session.setAttribute(LOGGED_IN_PERSON, loggedInPerson.getId());
        session.setAttribute(NAME, loggedInPerson.getFirstName() + " " + loggedInPerson.getLastName());
    }

    public static boolean isPersonLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(LOGGED_IN_PERSON) != null;
    }

    public static boolean isPersonLoggedIn(Map session) {
        return session.get(LOGGED_IN_PERSON) != null;
    }

    public static Long personLoggedInId(HttpSession session) {
        Long personId = (Long) session.getAttribute(LOGGED_IN_PERSON);
        if (personId == null) {
            throw new PersonNotLoggedInException();
        }
        return personId;
    }

    public static String personLoggedInName(HttpSession session) {
        if (isPersonLoggedIn(session)) {
            String name = (String) session.getAttribute(NAME);
            if (name == null) {
                throw new PersonNotLoggedInException();
            }
            return name;
        } else {
            return null;
        }
    }
}


