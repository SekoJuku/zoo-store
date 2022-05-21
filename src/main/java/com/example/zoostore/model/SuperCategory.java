package com.example.zoostore.model;

import com.example.oauth2.model.parent.DictionaryParentClass;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "super_categories")
@Getter
@Setter
public class SuperCategory extends DictionaryParentClass {

}
