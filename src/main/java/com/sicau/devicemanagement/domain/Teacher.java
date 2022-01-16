package com.sicau.devicemanagement.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sicau.devicemanagement.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 【请填写功能名称】对象 teacher
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@TableName("teacher")
public class Teacher
{
    private static final long serialVersionUID = 1L;

    /** uuid主键 */
    private String uid;

    /** 工号 */
    @Excel(name = "工号")
    private String employeeId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 账号密码 */
    @Excel(name = "账号密码")
    private String password;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String tel;

    /** 所属学院 */
    @Excel(name = "所属学院")
    private String college;

    /** 所属专业名称 */
    @Excel(name = "所属专业名称")
    private String major;

    /** 角色id */
    @Excel(name = "角色id")
    private String roleId;

    /** 是否删除，0为删除 */
    @Excel(name = "是否删除，0为删除")
    private Integer isDel;

    public void setUid(String uid) 
    {
        this.uid = uid;
    }

    public String getUid() 
    {
        return uid;
    }
    public void setEmployeeId(String employeeId) 
    {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() 
    {
        return employeeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setCollege(String college) 
    {
        this.college = college;
    }

    public String getCollege() 
    {
        return college;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setRoleId(String roleId) 
    {
        this.roleId = roleId;
    }

    public String getRoleId() 
    {
        return roleId;
    }
    public void setIsDel(Integer isDel) 
    {
        this.isDel = isDel;
    }

    public Integer getIsDel() 
    {
        return isDel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("employeeId", getEmployeeId())
            .append("name", getName())
            .append("password", getPassword())
            .append("tel", getTel())
            .append("college", getCollege())
            .append("major", getMajor())
            .append("roleId", getRoleId())
            .append("isDel", getIsDel())
            .toString();
    }
}
