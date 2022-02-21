package com.sicau.devicemanagement.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sicau.devicemanagement.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 student
 * 
 * @author ruoyi
 * @date 2022-01-15
 */
@Data
public class Student extends User
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String uid;

    /** 学号 */
    @Excel(name = "学号")
    private String stuNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 账号密码 */
    @Excel(name = "账号密码")
    private String password;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String tel;

    /** 指导老师id */
    @Excel(name = "指导老师id")
    private String teacherId;

    /** 所属学院 */
    @Excel(name = "所属学院")
    private String collegeId;

    /** 专业班级 */
    @Excel(name = "专业班级")
    private String majorClass;

    /** 角色id */
    @Excel(name = "角色id")
    private String roleId;

    private Integer isDel;

    @TableField(value = "expiration_date")
    private String expirationDate;

    public void setUid(String uid) 
    {
        this.uid = uid;
    }

    public String getUid() 
    {
        return uid;
    }
    public void setStuNumber(String stuNumber) 
    {
        this.stuNumber = stuNumber;
    }

    public String getStuNumber() 
    {
        return stuNumber;
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
    public void setTeacherId(String teacherId) 
    {
        this.teacherId = teacherId;
    }

    public String getTeacherId() 
    {
        return teacherId;
    }
    public void setCollegeId(String collegeId) 
    {
        this.collegeId = collegeId;
    }

    public String getCollegeId() 
    {
        return collegeId;
    }
    public void setMajorClass(String majorClass) 
    {
        this.majorClass = majorClass;
    }

    public String getMajorClass() 
    {
        return majorClass;
    }
    public void setRoleId(String roleId) 
    {
        this.roleId = roleId;
    }

    public String getRoleId() 
    {
        return roleId;
    }

    public String getExpirationDate() {return expirationDate;}
    public void setExpirationDate(String expirationDate) {this.expirationDate = expirationDate;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("stuNumber", getStuNumber())
            .append("name", getName())
            .append("password", getPassword())
            .append("tel", getTel())
            .append("teacherId", getTeacherId())
            .append("collegeId", getCollegeId())
            .append("majorClass", getMajorClass())
            .append("roleId", getRoleId())
            .toString();
    }
}
