package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.Shop;
import model.Product;

public class InventoryView extends JFrame {

    private static final long serialVersionUID = 1L;

    public InventoryView(Shop shop) {
        String[] columnNames = {"ID", "Nombre", "Precio Público", "Precio Mayorista", "Disponible", "Stock"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Product p : shop.getInventory()) {
            Object[] row = {
                p.getId(),
                p.getName(),
                p.getPublicPrice().toString(),
                p.getWholesalerPrice().toString(),
                p.isAvailable(),
                p.getStock()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane);
        setTitle("Inventario de Productos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
