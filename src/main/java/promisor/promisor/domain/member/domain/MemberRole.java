package promisor.promisor.domain.member.domain;

public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN"),
    LEADER("LEADER")
    ;

    final String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String role() { return role; }
}
