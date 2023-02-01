package promisor.promisor.domain.team.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Team 도메인 테스트")
class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        this.team = new Team(1L, "test");
    }

    @DisplayName("")
    @Test
    void calculate_mid_point() {
        team.calculateMidPoint();
    }

    @DisplayName("")
    @Test
    void member_not_belongs_to_team() {

    }
}
