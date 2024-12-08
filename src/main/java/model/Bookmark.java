package model;

public class Bookmark {
    private int id;
    private String groupName;
    private String wifiName;
    private String createdAt;
    private String remarks;
    
    public Bookmark() {
    }

    public Bookmark(String groupName, String wifiName, String createdAt, String remarks) {
		this.groupName = groupName;
		this.wifiName = wifiName;
		this.createdAt = createdAt;
		this.remarks = remarks;
	}
    
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getWifiName() {
        return wifiName;
    }
    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
