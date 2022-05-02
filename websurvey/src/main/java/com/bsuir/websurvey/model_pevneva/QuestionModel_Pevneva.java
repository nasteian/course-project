package com.websurvey.websurvey_pevneva.model_pevneva;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.json.JSONArray;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Questions")
@Table(name = "questions")
public class QuestionModel_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private int type;
    @Getter @Setter private String wording;
    @Nullable
    @Getter @Setter private String variants;
    @Getter @Setter private String answer;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "survey_id")
    @Getter @Setter private SurveyModel_Pevneva survey;
}
