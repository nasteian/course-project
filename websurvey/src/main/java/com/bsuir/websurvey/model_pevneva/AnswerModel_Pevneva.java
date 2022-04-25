package com.websurvey.websurvey_pevneva.model_pevneva;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Answers")
@Table(name = "answers")
public class AnswerModel_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String answer;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "owner_id")
    @Getter @Setter private UserModel_Pevneva owner;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "question_id")
    @Getter @Setter private QuestionModel_Pevneva question;
}
