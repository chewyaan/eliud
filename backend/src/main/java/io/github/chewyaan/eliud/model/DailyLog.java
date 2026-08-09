package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DailyLog {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //    Temp. limiting fetch/cascade interaction
//    @ManyToOne
//    private User user;

    //    Temp. limiting fetch/cascade interaction

//    @OneToMany(mappedBy = "sourceLog", cascade = CascadeType.ALL)
//    private List<AdjustmentSignal> adjustmentSignals;
}
