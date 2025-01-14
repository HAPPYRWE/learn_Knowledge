package com.iweb.xt.sso.dao.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@TableName(value = "user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String nickname;
    @TableField("u_sex")
    private Integer sex;

    @TableField("u_city")
    private String city;

    @TableField("u_province")
    private String province;

    @TableField("u_country")
    private String country;

    @TableField("u_head_image_url")
    private String headImageUrl;

    @TableField("u_open_id")
    private String openid;

    @TableField("u_login_type")
    private Integer loginType;

    @TableField("u_register_time")
    private Long registerTime;

    @TableField("u_last_login_time")
    private Long lastLoginTime;

    @TableField("u_union_id")
    private String unionId;

    @TableField("u_mobile")
    private String mobile;

    @TableField("u_area")
    private String area;

    @TableField("u_grade")
    private String grade;

    @TableField("u_school")
    private String school;

    @TableField("u_name")
    private String name;
}
