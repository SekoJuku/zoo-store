package com.example.oauth2.model;

import com.example.oauth2.model.parent.DictionaryParentClass;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority extends DictionaryParentClass {
}
