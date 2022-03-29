package com.bsuir.websurvey.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SurveyEntity_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}

