package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DailyLog {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "sourceLog", cascade = CascadeType.ALL)
    private List<AdjustmentSignal> adjustmentSignals;
}
