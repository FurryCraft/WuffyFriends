package com.fullhousedev.mc.friends.settings;

/**
 * Created by Austin on 8/23/2015.
 */
public abstract class BooleanSetting implements Setting {
    @Override
    public abstract String getName();

    @Override
    public SettingType getType() {
        return SettingType.BOOLEAN;
    }

    @Override
    public abstract Boolean getSettingValue();

    @Override
    public abstract void setSettingValue(Boolean object);
}
