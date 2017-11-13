package cn.gson.crm.model.domain;

import cn.gson.crm.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 员工
 *
 * @author ____′↘夏悸
 */
@Entity
@Table(name = "crm_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false, unique = true, updatable = false)
    private String userName;

    @Column(length = 128, nullable = false)
    @JsonIgnore//json序列化的时候，忽略密码字段
    private String password;

    @Column(length = 64, nullable = false)
    private String realName;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 64)
    private String telephone;

    @Column(length = 128)
    private String email;

    @Column(length = 256)
    private String avatar;

    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hiredate;

    private Boolean status = false;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "crm_member_role",
            joinColumns = {
                    @JoinColumn(name = "member_id")
            }, inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    })
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "Member [id=" + id + ", userName=" + userName + ", password=" + password + ", realName=" + realName
                + ", gender=" + gender + ", telephone=" + telephone + ", email=" + email + ", hiredate=" + hiredate
                + ", status=" + status + "]";
    }

}
