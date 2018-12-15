package com.example.Model;

//import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="User")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;
    @Column(name = "user_name")
    //@NotNull
    private String name;
    @Column(name = "password")
    //@NotNull
    private String password;
    @Column(name = "emailId")
    //@NotNull
    private String emailId;
    @Column(name = "contact_no")
    //@NotNull
    private long contact_no;

    public User(String name,String password,String emailId,long contact_no)
    {
        this.name=name;
        this.password=password;
        this.emailId=emailId;
        this.contact_no=contact_no;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public long getContact_no() {
        return contact_no;
    }

    public void setContact_no(long contact_no) {
        this.contact_no = contact_no;
    }
}
