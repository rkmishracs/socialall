package com.mystatusonline.dao.hibernate;

import java.util.List;

import javax.persistence.Table;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.mystatusonline.dao.PersonDao;
import com.mystatusonline.model.Person;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 2:06:25 PM
 * To change this template use File | Settings | File Templates.
 */

@Repository("personDao")
public class PersonDaoHibernate
        extends GenericDaoHibernate<Person, Long>
        implements PersonDao {

    public PersonDaoHibernate() {
        super(Person.class);
    }

    @Override
    public Person get(Long id) {
        Person person = super.get(id);
        person.setConfirmPassword(person.getPassword());
        return person;
    }

    @SuppressWarnings("unchecked")
    public List<Person> findByLastName(String lastName) {
        return getHibernateTemplate()
                .find("from Person where lastName=?", lastName);
    }

    public List<Person> findByPhoneNumber(String phoneNumber) {
        String toSearchFor = phoneNumber;
        if (phoneNumber.length() > 10) {
            toSearchFor = phoneNumber.substring(phoneNumber.length() - 10);
        }
        return getHibernateTemplate()
                .find("from Person where phoneNumber=?", toSearchFor);
    }

    @SuppressWarnings("unchecked")
    public List<Person> findByUserName(String username) {
        return getHibernateTemplate()
                .find("from Person where username=?", username);
    }

    @SuppressWarnings("unchecked")
    public List<Person> findByEmail(String email) {
        return getHibernateTemplate()
                .find("from Person where email=?", email);
    }

    /**
     * {@inheritDoc}
     */
    public String getUserPassword(String username) {
        SimpleJdbcTemplate jdbcTemplate =
                new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(Person.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where username=?", String.class, username);

    }
}