package edu.demian.model.entity;

public enum Role {
    READER, LIBRARIAN;

    public static Role getRole(final Account account) {
        final int roleId = account.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
