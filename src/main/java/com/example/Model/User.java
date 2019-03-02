package com.example.Model;

//import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
*/
@Component
@Entity(name="User")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;
    @Column(name = "user_name")
    @NotNull
    @Length(min=2, max=12, message = "username should be between 2 and 10 characters")
    private String name;
    @Column(name = "password")
    @Length(min=6, max=12, message = "password should be between 6 and 12 characters")
    @NotNull
    private String password;
    @Column(name = "emailId")
    @NotNull
    @Email
    private String emailId;
    @Column(name = "contact_no")
    private String contact_no;

    public User()
    {

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

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contact_no=" + contact_no +
                '}';
    }
}
