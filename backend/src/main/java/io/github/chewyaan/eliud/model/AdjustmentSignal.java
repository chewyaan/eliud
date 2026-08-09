package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

@Entity
public class AdjustmentSignal {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    Temp. limiting fetch/cascade interaction

//    @ManyToOne
//    private DailyLog sourceLog;
}
