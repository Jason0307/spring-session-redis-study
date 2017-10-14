package org.zhubao.entity;

import java.io.Serializable;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -3619520908385560057L;

    private long id;
    
    private String name;
    
    private int age;
    
    private String email;
    
    
}
