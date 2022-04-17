package com.websurvey.websurvey_pevneva.model_pevneva;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class QuestionModel_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private int type;
    @Getter @Setter private String wording;
    @Getter @Setter private String answer;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    @Getter @Setter private SurveyModel_Pevneva survey;
}
