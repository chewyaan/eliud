package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.time.Duration;

@Entity
public class RaceGoal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double distance;
    private Duration time;

    public RaceGoal() {}
    public RaceGoal(String name, Double distance, Duration time) {
        this.name = name;
        this.distance = distance;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Duration getTime() {
        return time;
    }

    public void setTime(Duration time) {
        this.time = time;
    }

//    Temp. limiting fetch/cascade interaction
//    @ManyToOne
//    private User user;
}
