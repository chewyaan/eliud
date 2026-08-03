package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

@Entity
public class AdjustmentSignal {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private DailyLog sourceLog;
}
