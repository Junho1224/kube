package com.von.api.schedule.model;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"stadium_id"})
@Entity(name = "schedules")
@Builder
public class schedule {

    @Id
    @Column(name ="stadium_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stadium_id;   
    private String sche_date;
    private String gubun;
    private String hometeam_id;
    private String awayteam_id;  
    private Long home_score;
    private Long away_score;
    



    
}
