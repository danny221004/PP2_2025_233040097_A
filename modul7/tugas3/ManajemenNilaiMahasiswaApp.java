package modul7.tugas3;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManajemenNilaiMahasiswaApp extends JFrame {

    private JTextField txtNama;
    private JTextField txtNilai;
    private JComboBox<String> cmbMatkul;
    private JTable tableData;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    public ManajemenNilaiMahasiswaApp() {

        setTitle("Aplikasi Manajemen Nilai Siswa");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        JPanel panelInput = createInputPanel();
        JPanel panelTabel = createTablePanel();

        tabbedPane.addTab("Input Data", panelInput);
        tabbedPane.addTab("Daftar Nilai", panelTabel);

        add(tabbedPane);
    }

    private JPanel createInputPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nama
        panel.add(new JLabel("Nama Siswa"));
        txtNama = new JTextField();
        panel.add(txtNama);

        // Mata Pelajaran
        panel.add(new JLabel("Mata Pelajaran"));
        String[] matkul = {
                "Matematika Dasar",
                "Bahasa Indonesia",
                "Algoritma dan Pemrograman I",
                "Praktikum Pemrograman II"
        };
        cmbMatkul = new JComboBox<>(matkul);
        panel.add(cmbMatkul);

        // Nilai
        panel.add(new JLabel("Nilai (0-100):"));
        txtNilai = new JTextField();
        panel.add(txtNilai);

        // Tombol Simpan
        JButton btnSimpan = new JButton("Simpan Data");
        panel.add(new JLabel()); // placeholder
        panel.add(btnSimpan);

        btnSimpan.addActionListener(e -> prosesSimpan());

        return panel;
    }

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {"Nama Siswa", "Mata Pelajaran", "Nilai", "Grade"};
        tableModel = new DefaultTableModel(kolom, 0);
        tableData = new JTable(tableModel);

        JScrollPane scroll = new JScrollPane(tableData);
        panel.add(scroll, BorderLayout.CENTER);

        // Tombol hapus
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnHapus = new JButton("Hapus Data");
        bottomPanel.add(btnHapus);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        btnHapus.addActionListener(e -> {
            int row = tableData.getSelectedRow();

            if (row > -1) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Yakin ingin menghapus data?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Pilih baris yang akan dihapus!",
                        "Tidak ada data dipilih",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        return panel;
    }

    private void prosesSimpan() {

        String nama = txtNama.getText();
        String matkul = (String) cmbMatkul.getSelectedItem();
        String strNilai = txtNilai.getText();

        // Validasi nama kosong
        if (nama.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ⭐ Validasi minimal 3 karakter
        if (nama.trim().length() < 3) {
            JOptionPane.showMessageDialog(this,
                    "Nama harus terdiri dari minimal 3 karakter!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi nilai kosong
        if (strNilai.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nilai tidak boleh kosong!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nilai;
        try {
            nilai = Integer.parseInt(strNilai);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nilai harus angka!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi range nilai
        if (nilai < 0 || nilai > 100) {
            JOptionPane.showMessageDialog(this, "Nilai harus 0 - 100!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Penentuan grade
        String grade;
        if (nilai >= 80) grade = "A";
        else if (nilai >= 70) grade = "AB";
        else if (nilai >= 60) grade = "B";
        else if (nilai >= 50) grade = "BC";
        else if (nilai >= 40) grade = "C";
        else if (nilai >= 30) grade = "D";
        else grade = "E";

        // Input ke tabel
        Object[] data = {nama, matkul, nilai, grade};
        tableModel.addRow(data);

        // Reset form
        txtNama.setText("");
        txtNilai.setText("");
        cmbMatkul.setSelectedIndex(0);

        JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");

        tabbedPane.setSelectedIndex(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManajemenNilaiMahasiswaApp().setVisible(true);
        });
    }
}
