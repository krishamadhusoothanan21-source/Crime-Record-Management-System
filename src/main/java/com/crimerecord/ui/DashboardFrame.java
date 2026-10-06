package com.crimerecord.ui;

import com.crimerecord.dao.CrimeDAO;
import com.crimerecord.model.Crime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DashboardFrame extends JFrame {
    private final CrimeDAO dao = new CrimeDAO();
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField search = new JTextField();

    private final JLabel total = new JLabel("Total: 0");
    private final JLabel open = new JLabel("Open: 0");
    private final JLabel closed = new JLabel("Closed: 0");

    private final JTextField caseNo = new JTextField();
    private final JTextField type = new JTextField();
    private final JTextField victim = new JTextField();
    private final JTextField suspect = new JTextField();
    private final JTextField location = new JTextField();
    private final JTextField date = new JTextField(LocalDate.now().toString());
    private final JTextField officer = new JTextField();
    private final JComboBox<String> status =
            new JComboBox<>(new String[]{"Open","Under Investigation","Closed"});
    private final JTextArea description = new JTextArea(4, 25);

    public DashboardFrame() {
        setTitle("Crime Record Management System");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel top = new JPanel(new BorderLayout(10,10));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("CRIME RECORD MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        top.add(title, BorderLayout.WEST);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        stats.add(total); stats.add(open); stats.add(closed);
        top.add(stats, BorderLayout.EAST);

        JPanel searchPanel = new JPanel(new BorderLayout(8,8));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0,10,8,10));
        searchPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        searchPanel.add(search, BorderLayout.CENTER);

        JButton searchBtn = new JButton("Search");
        JButton allBtn = new JButton("Show All");
        JPanel searchButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,0));
        searchButtons.add(searchBtn);
        searchButtons.add(allBtn);
        searchPanel.add(searchButtons, BorderLayout.EAST);

        model = new DefaultTableModel(new Object[]{
                "ID","Case No.","Crime Type","Victim","Suspect",
                "Location","Date","Status","Officer"
        }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> loadSelected());

        JPanel center = new JPanel(new BorderLayout());
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, center, buildForm());
        split.setDividerLocation(780);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(logout);

        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> refresh(search.getText().trim()));
        allBtn.addActionListener(e -> {
            search.setText("");
            refresh("");
        });

        refresh("");
    }

    private JPanel buildForm() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Crime Record"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5,5,5,5);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        addRow(p,g,0,"Case Number:",caseNo);
        addRow(p,g,1,"Crime Type:",type);
        addRow(p,g,2,"Victim Name:",victim);
        addRow(p,g,3,"Suspect Name:",suspect);
        addRow(p,g,4,"Location:",location);
        addRow(p,g,5,"Crime Date:",date);
        addRow(p,g,6,"Status:",status);
        addRow(p,g,7,"Officer Name:",officer);

        g.gridx=0; g.gridy=8; p.add(new JLabel("Description:"),g);
        g.gridx=0; g.gridy=9; g.gridwidth=2;
        p.add(new JScrollPane(description),g);

        JPanel buttons = new JPanel(new GridLayout(1,3,5,5));
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        buttons.add(add); buttons.add(update); buttons.add(delete);

        g.gridy=10; g.gridwidth=2;
        p.add(buttons,g);

        add.addActionListener(e -> addRecord());
        update.addActionListener(e -> updateRecord());
        delete.addActionListener(e -> deleteRecord());
        return p;
    }

    private void addRow(JPanel p, GridBagConstraints g, int y, String label, JComponent field) {
        g.gridwidth=1; g.gridx=0; g.gridy=y;
        p.add(new JLabel(label),g);
        g.gridx=1; p.add(field,g);
    }

    private Crime readForm() {
        if (caseNo.getText().trim().isEmpty() ||
            type.getText().trim().isEmpty() ||
            victim.getText().trim().isEmpty() ||
            location.getText().trim().isEmpty() ||
            officer.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Fill all required fields.");
        }

        return new Crime(
            caseNo.getText().trim(),
            type.getText().trim(),
            victim.getText().trim(),
            suspect.getText().trim(),
            location.getText().trim(),
            LocalDate.parse(date.getText().trim()),
            description.getText().trim(),
            (String)status.getSelectedItem(),
            officer.getText().trim()
        );
    }

    private void addRecord() {
        try {
            dao.add(readForm());
            JOptionPane.showMessageDialog(this, "Crime record added successfully.");
            clearForm();
            refresh(search.getText().trim());
        } catch (Exception ex) { showError(ex); }
    }

    private void updateRecord() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a record first.");
            return;
        }
        try {
            Crime c = readForm();
            c.setId((int)model.getValueAt(row,0));
            dao.update(c);
            JOptionPane.showMessageDialog(this, "Record updated successfully.");
            clearForm();
            refresh(search.getText().trim());
        } catch (Exception ex) { showError(ex); }
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a record first.");
            return;
        }
        if (JOptionPane.showConfirmDialog(this,
                "Delete the selected record?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        try {
            dao.delete((int)model.getValueAt(row,0));
            clearForm();
            refresh(search.getText().trim());
        } catch (Exception ex) { showError(ex); }
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        try {
            List<Crime> list = dao.search(String.valueOf(model.getValueAt(row,1)));
            if (list.isEmpty()) return;
            Crime c = list.get(0);
            caseNo.setText(c.getCaseNumber());
            type.setText(c.getCrimeType());
            victim.setText(c.getVictimName());
            suspect.setText(c.getSuspectName());
            location.setText(c.getLocation());
            date.setText(c.getCrimeDate().toString());
            status.setSelectedItem(c.getStatus());
            officer.setText(c.getOfficerName());
            description.setText(c.getDescription());
        } catch (Exception ex) { showError(ex); }
    }

    private void refresh(String keyword) {
        try {
            model.setRowCount(0);
            for (Crime c : dao.search(keyword)) {
                model.addRow(new Object[]{
                    c.getId(), c.getCaseNumber(), c.getCrimeType(),
                    c.getVictimName(), c.getSuspectName(), c.getLocation(),
                    c.getCrimeDate(), c.getStatus(), c.getOfficerName()
                });
            }
            total.setText("Total: " + dao.count(null));
            open.setText("Open: " + dao.count("Open"));
            closed.setText("Closed: " + dao.count("Closed"));
        } catch (Exception ex) { showError(ex); }
    }

    private void clearForm() {
        caseNo.setText("");
        type.setText("");
        victim.setText("");
        suspect.setText("");
        location.setText("");
        date.setText(LocalDate.now().toString());
        officer.setText("");
        status.setSelectedIndex(0);
        description.setText("");
        table.clearSelection();
    }

    private void showError(Exception ex) {
        String msg = ex.getMessage() == null ? ex.toString() : ex.getMessage();
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
