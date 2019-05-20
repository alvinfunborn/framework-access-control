package com.alvin.framework.access.control.group;

/**
 * datetime 2019/5/20 10:00
 *
 * @author sin5
 */
public class Group {

    /**
     * group name
     */
    private String group;
    /**
     * member name
     */
    private String member;

    public Group(String group, String member) {
        this.group = group;
        this.member = member;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
