package ca.airmiles.travel.core.beans;

public class Cluster {
    private String clusterName;
    private String packageName;
    private String packageID;
    private String redemption;
    private String tripType;


    public Cluster(String clusterName, String packageName, String redemption, String packageID, String tripType) {
        this.clusterName = clusterName;
        this.packageName = packageName;
        this.redemption = redemption;
        this.tripType = tripType;
        this.packageID = packageID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRedemption() {
        return redemption;
    }

    public void setRedemption(String redemption) {
        this.redemption = redemption;
    }

    public String getTripType() {
        return tripType;
    }
    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
}
