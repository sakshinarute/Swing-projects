package com.swingdemo.jtable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JTableExample {

	public static void main(String[] args) {
		JFrame f = new JFrame("Table Example");  
        
		String data[][]={ {"101","Amit","670000"},    
                          {"102","Jai","780000"},    
                          {"101","Sachin","700000"}
                        };    
        String column[]={"ID","NAME","SALARY"};         
        
        final JTable jt=new JTable(data,column);    
        jt.setCellSelectionEnabled(true);  
        
        ListSelectionModel select= jt.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        
        select.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int[] rows = jt.getSelectedRows();
            int[] cols = jt.getSelectedColumns();
            StringBuilder sb = new StringBuilder("Selected: ");
            for (int r : rows) {
                for (int c : cols) {
                    sb.append(jt.getValueAt(r, c)).append(" ");
                }
            }
            System.out.println(sb.toString());
        });
        
        JScrollPane sp=new JScrollPane(jt);    
        f.add(sp);  
        f.setSize(300, 200);  
        f.setVisible(true);  

	}

}
