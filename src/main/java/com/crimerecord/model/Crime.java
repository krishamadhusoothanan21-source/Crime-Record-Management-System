package com.crimerecord.model;

import java.time.LocalDate;

public class Crime {
    private int id;
    private String caseNumber, crimeType, victimName, suspectName, location;
    private LocalDate crimeDate;
    private String description, status, officerName;

    public Crime() {}

    public Crime(String caseNumber, String crimeType, String victimName,
                 String suspectName, String location, LocalDate crimeDate,
                 String description, String status, String officerName) {
        this.caseNumber = caseNumber;
        this.crimeType = crimeType;
        this.victimName = victimName;
        this.suspectName = suspectName;
        this.location = location;
        this.crimeDate = crimeDate;
        this.description = description;
        this.status = status;
        this.officerName = officerName;
    }

    public int getId() { return id; }
    public void setId(int v) { id = v; }
    public String getCaseNumber() { return caseNumber; }
    public void setCaseNumber(String v) { caseNumber = v; }
    public String getCrimeType() { return crimeType; }
    public void setCrimeType(String v) { crimeType = v; }
    public String getVictimName() { return victimName; }
    public void setVictimName(String v) { victimName = v; }
    public String getSuspectName() { return suspectName; }
    public void setSuspectName(String v) { suspectName = v; }
    public String getLocation() { return location; }
    public void setLocation(String v) { location = v; }
    public LocalDate getCrimeDate() { return crimeDate; }
    public void setCrimeDate(LocalDate v) { crimeDate = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { description = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { status = v; }
    public String getOfficerName() { return officerName; }
    public void setOfficerName(String v) { officerName = v; }
}
