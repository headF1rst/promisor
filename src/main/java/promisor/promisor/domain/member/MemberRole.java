package promisor.promisor.domain.member;

public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN")
    ;

    String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String role() { return role; }
}
