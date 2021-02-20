package com.moment.the.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SaltDomain {
    @Id
    @JsonIgnore
    @GeneratedValue
    private int id;

    @JsonIgnore
    @NotNull()
    private String salt;
    public SaltDomain(){
    }
    public SaltDomain(String salt){
        this.salt = salt;
    }
}
