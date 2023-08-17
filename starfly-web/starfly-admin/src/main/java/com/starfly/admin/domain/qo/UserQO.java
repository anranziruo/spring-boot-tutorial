package com.starfly.admin.domain.qo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserQO {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    /**
     *
     */
    @Email(message = "非法的邮箱格式")
    @NotNull(message = "邮箱不能为空")
    private String email;

    /**
     *
     */
    @Valid
    @NotNull(message = "年龄不能为空")
    private Integer age;

    /**
     *
     */
    @NotNull(message = "全名不能为空")
    private String fullName;

    /**
     *
     */

    private Object gender;

    /**
     *
     */
    private String address;

    /**
     *
     */
    private String salt;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private Integer isDelete;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;
}
