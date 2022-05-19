package com.example.oauth2.model;


import com.example.oauth2.model.parent.DictionaryParentClass;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auth_providers")
public class AuthProvider extends DictionaryParentClass {
}
