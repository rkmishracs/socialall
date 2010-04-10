package com.mystatusonline.model;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import org.appfuse.model.BaseObject;


/**
 * Created by IntelliJ IDEA. User: pdamani Date: Feb 10, 2010 Time: 12:21:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Person")
public class Person
        extends BaseObject {

    private Long id;
    private String username;                    // required
    private String password;                    // required
    private String confirmPassword;
    private String firstName;                   // required
    private String lastName;                    // required
    private String email;
    private String phoneNumber;
    private Integer version;


    private Set<SocialNetwork> socialNetworks = new HashSet<SocialNetwork>();

    public Person() {
    }

    public Person(String username, String password, String confirmPassword,
                  String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "personId")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    @Column(name = "username", length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phoneNumber", length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null){
            this.phoneNumber = Pattern.compile("\\D").matcher(phoneNumber).replaceAll("");
        }
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "personId")
    public Set<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Set<SocialNetwork> socialNetwork) {
        this.socialNetworks = socialNetwork;
    }

    @Transient
    public void addSocialNetwork(SocialNetwork network) {
        for (SocialNetwork sn : socialNetworks) {
            if (sn.getSocialNetworkType() == network.getSocialNetworkType()) {
                //set facebook values
                //set twitter values
                return;
            }
        }
        socialNetworks.add(network);
    }

    @Transient
    public SocialNetwork getNetwork(int socialNetworkType) {
        for (SocialNetwork sn : socialNetworks) {
            if (sn.getSocialNetworkType() == socialNetworkType) {
                return sn;
            }
        }/*
        SocialNetwork network = new SocialNetwork(socialNetworkType);
        addSocialNetwork(network);
        return network;*/
        return null;
    }


    @Transient
    public boolean isAnySocialNetworkInitialized() {
        boolean init = false;
        for (SocialNetwork network : socialNetworks) {
            switch (network.getSocialNetworkType()) {
                case SocialNetwork.FACEBOOK:
                    init = isFBInit();
                    if (init) {
                        return init;
                    }
                    break;
                case SocialNetwork.TWITTER:
                    init = isTwitterInit();
                    if (init) {
                        return init;
                    }
                    break;
            }
        }
        return init;
    }

    @Transient
    public boolean isFBInit() {
        return getNetwork(SocialNetwork.FACEBOOK) != null;
    }

    @Transient
    public boolean isTwitterInit() {
        return getNetwork(SocialNetwork.TWITTER) != null;
    }

    @Transient
    public boolean isMySpaceInit() {
        return getNetwork(SocialNetwork.MYSPACE) != null;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (confirmPassword != null ?
                !confirmPassword.equals(person.confirmPassword) :
                person.confirmPassword != null) {
            return false;
        }
        if (email != null ? !email.equals(person.email) : person.email != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(person.firstName) :
                person.firstName != null) {
            return false;
        }
        if (id != null ? !id.equals(person.id) : person.id != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(person.lastName) :
                person.lastName != null) {
            return false;
        }
        if (password != null ? !password.equals(person.password) :
                person.password != null) {
            return false;
        }
        if (username != null ? !username.equals(person.username) :
                person.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result =
                31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

}
