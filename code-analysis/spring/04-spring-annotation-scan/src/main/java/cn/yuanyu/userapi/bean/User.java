package cn.yuanyu.userapi.bean;

import lombok.Data;

/**
 * @author yuanyu
 */
@Data
public class User {
    private String name;
    private Integer age;
    public User() {
    }
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
