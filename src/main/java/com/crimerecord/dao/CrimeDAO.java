package com.crimerecord.dao;

import com.crimerecord.DBConnection;
import com.crimerecord.model.Crime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrimeDAO {

    public void add(Crime c) throws SQLException {
        String sql = "INSERT INTO crimes " +
                "(case_number, crime_type, victim_name, suspect_name, location, " +
                "crime_date, description, status, officer_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            fill(ps, c);
            ps.executeUpdate();
        }
    }

    public void update(Crime c) throws SQLException {
        String sql = "UPDATE crimes SET case_number=?, crime_type=?, victim_name=?, " +
                "suspect_name=?, location=?, crime_date=?, description=?, status=?, " +
                "officer_name=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            fill(ps, c);
            ps.setInt(10, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM crimes WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Crime> search(String keyword) throws SQLException {
        List<Crime> list = new ArrayList<>();
        String sql = "SELECT * FROM crimes WHERE case_number LIKE ? OR crime_type LIKE ? " +
                "OR victim_name LIKE ? OR suspect_name LIKE ? OR location LIKE ? ORDER BY id DESC";
        String k = "%" + keyword + "%";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) ps.setString(i, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Crime c = new Crime();
                    c.setId(rs.getInt("id"));
                    c.setCaseNumber(rs.getString("case_number"));
                    c.setCrimeType(rs.getString("crime_type"));
                    c.setVictimName(rs.getString("victim_name"));
                    c.setSuspectName(rs.getString("suspect_name"));
                    c.setLocation(rs.getString("location"));
                    c.setCrimeDate(rs.getDate("crime_date").toLocalDate());
                    c.setDescription(rs.getString("description"));
                    c.setStatus(rs.getString("status"));
                    c.setOfficerName(rs.getString("officer_name"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    public int count(String status) throws SQLException {
        String sql = status == null ? "SELECT COUNT(*) FROM crimes"
                                    : "SELECT COUNT(*) FROM crimes WHERE status=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (status != null) ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private void fill(PreparedStatement ps, Crime c) throws SQLException {
        ps.setString(1, c.getCaseNumber());
        ps.setString(2, c.getCrimeType());
        ps.setString(3, c.getVictimName());
        ps.setString(4, c.getSuspectName());
        ps.setString(5, c.getLocation());
        ps.setDate(6, Date.valueOf(c.getCrimeDate()));
        ps.setString(7, c.getDescription());
        ps.setString(8, c.getStatus());
        ps.setString(9, c.getOfficerName());
    }
}
