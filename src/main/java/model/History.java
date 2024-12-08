package model;

public class History {
    private int id;
    private double lat;
    private double lnt;
    private String searchDttm;
    private String remarks;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLnt() {
		return lnt;
	}
	public void setLnt(double lnt) {
		this.lnt = lnt;
	}
	public String getSearchDttm() {
		return searchDttm;
	}
	public void setSearchDttm(String searchDttm) {
		this.searchDttm = searchDttm;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
	
}
