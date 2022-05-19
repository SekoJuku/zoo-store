package com.example.oauth2.model;

import com.example.oauth2.model.parent.DictionaryParentClass;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends DictionaryParentClass {
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;
}
