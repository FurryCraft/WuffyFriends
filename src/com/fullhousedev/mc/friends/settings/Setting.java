package com.fullhousedev.mc.friends.settings;

/**
 * Created by Austin on 8/23/2015.
 */
public interface Setting {

    public String getName();

    public SettingType getType();

    public Object getSettingValue();

    public void setSettingValue(Object object);

}
