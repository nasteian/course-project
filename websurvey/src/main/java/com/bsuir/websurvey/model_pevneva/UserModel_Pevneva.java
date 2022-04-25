package com.websurvey.websurvey_pevneva.model_pevneva;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Users")
@Table(name = "users")
public class UserModel_Pevneva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private int id;
    @Getter @Setter private String login;
    @Getter @Setter private String passwordHash;
    @Getter @Setter private int role;
    @Getter @Setter private String codePhraseHash;
    @Nullable
    @Getter @Setter private String sessionIdHash;

    @ManyToMany(cascade = CascadeType.DETACH)
    Set<SurveyModel_Pevneva> completedSurveys;
}
