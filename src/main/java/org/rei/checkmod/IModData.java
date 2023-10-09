package org.rei.checkmod;

public interface IModData {
    public String getName();

    public String getVersion();

    default String getCompleteData() {
        return getName() + " " + getVersion();
    }
}
