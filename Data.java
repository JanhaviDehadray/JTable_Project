package pack1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Data extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField id;
	private JTextField fname;
	private JTextField lname;
	private JTextField email;
	private JTextField contact;
	private JTextField address;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Data frame = new Data();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
Connection conn=null;
PreparedStatement pst=null;
ResultSet rs=null;
private JTable table_j;
void clear_field() {
	id.setText(null);
	fname.setText(null);
	lname.setText(null);
	email.setText(null);
	contact.setText(null);
	address.setText(null);
}
public void refresh() {
	try {
		String sql="select * from Stud_Details";
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Students","root","Janhavi@31");
		pst=conn.prepareStatement(sql);
		rs=pst.executeQuery();
		table_j.setModel(DbUtils.resultSetToTableModel(rs));
	}catch(Exception ex) {
		
	}
}
	/**
	 * Create the frame.
	 */
	public Data() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Students","root","Janhavi@31");
					String sql="insert into Stud_Details(ID,First_name,Last_name,Email_ID,Contact_No,Address) values(?,?,?,?,?,?)";
					pst=conn.prepareStatement(sql);
					pst.setInt(1,Integer.parseInt(id.getText()));
					pst.setString(2,fname.getText());
					pst.setString(3,lname.getText());
					pst.setString(4,email.getText());
					pst.setLong(5,Long.parseLong(contact.getText()));
					pst.setString(6,address.getText());
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Inserted Successfully");
					clear_field();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex);
			}
				refresh();
			}
			});
		btnInsert.setBounds(25, 378, 156, 44);
		contentPane.add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				/*conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Students","root","Janhavi@31");
				String sql="Update Stud_Details set First_name=?,Last_name=?,Email_ID=?,Contact_No=?,Address=? where ID=?";
				pst=conn.prepareStatement(sql);
				pst.setInt(6,Integer.parseInt(id.getText()));
				pst.setString(1,fname.getText());
				pst.setString(2,lname.getText());
				pst.setString(3,email.getText());
				pst.setLong(4,Long.parseLong(contact.getText()));
				pst.setString(5,address.getText());
				
				
				pst.executeUpdate();*/
				String sql = "UPDATE stud_details SET First_name = COALESCE(?, First_name), " +
		                "Last_name = COALESCE(?, Last_name), " +
		                "Contact_No = COALESCE(?, Contact_No), " +
		                "Email_ID = COALESCE(?, Email_ID), " +
		                "Address = COALESCE(?, Address) " +
		                "WHERE ID = ?";

		        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "Janhavi@31");
		        pst = conn.prepareStatement(sql);

		        if (id.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "ID is required for update.");
		            return;
		        } else {
		            pst.setInt(6, Integer.parseInt(id.getText()));
		        }

		        pst.setString(1, fname.getText().isEmpty() ? null : fname.getText());
		        pst.setString(2, lname.getText().isEmpty() ? null : lname.getText());
		        
		        
		        
		        pst.setString(3, email.getText().isEmpty() ? null : email.getText());
		        if (!contact.getText().isEmpty()) {
		            pst.setLong(4, Long.parseLong(contact.getText()));
		        } else {
		            pst.setNull(4, java.sql.Types.BIGINT);
		        }
		        pst.setString(5, address.getText().isEmpty() ? null : address.getText());
		        if (fname.getText().isEmpty() && lname.getText().isEmpty() && email.getText().isEmpty()
		                && contact.getText().isEmpty() && address.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please fill in at least one field (other than ID) for update.");
		            return;
		        }

		        pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"Updated Successfully");
				
				clear_field();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null,ex);
		}
			refresh();
			
			}
		
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(25, 444, 156, 44);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Students","root","Janhavi@31");
					String sql="delete from Stud_Details where ID=?";
					pst=conn.prepareStatement(sql);
					pst.setInt(1,Integer.parseInt(id.getText()));
					
					
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Deleted Successfully");
					clear_field();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null,ex);
			}
				refresh();
			}

		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(213, 378, 156, 44);
		contentPane.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_field();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClear.setBounds(213, 444, 156, 44);
		contentPane.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(25, 3, 128, 58);
		contentPane.add(lblNewLabel);
		
		JLabel lblFirstname = new JLabel("Last_name");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstname.setBounds(25, 124, 128, 58);
		contentPane.add(lblFirstname);
		
		JLabel lblEmailid = new JLabel("Email_ID");
		lblEmailid.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEmailid.setBounds(25, 192, 128, 58);
		contentPane.add(lblEmailid);
		
		JLabel lblFirstname_2 = new JLabel("First_name");
		lblFirstname_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstname_2.setBounds(25, 58, 128, 58);
		contentPane.add(lblFirstname_2);
		
		JLabel lblContactno = new JLabel("Contact_No");
		lblContactno.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblContactno.setBounds(25, 256, 128, 58);
		contentPane.add(lblContactno);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddress.setBounds(25, 319, 128, 58);
		contentPane.add(lblAddress);
		
		id = new JTextField();
		id.setBounds(171, 10, 168, 38);
		contentPane.add(id);
		id.setColumns(10);
		
		fname = new JTextField();
		fname.setBounds(171, 71, 168, 38);
		contentPane.add(fname);
		fname.setColumns(10);
		
		lname = new JTextField();
		lname.setBounds(176, 134, 163, 44);
		contentPane.add(lname);
		lname.setColumns(10);
		
		email = new JTextField();
		email.setBounds(176, 204, 163, 44);
		contentPane.add(email);
		email.setColumns(10);
		
		contact = new JTextField();
		contact.setBounds(171, 267, 168, 41);
		contentPane.add(contact);
		contact.setColumns(10);
		
		address = new JTextField();
		address.setBounds(171, 319, 168, 41);
		contentPane.add(address);
		address.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=table_j.getSelectedRow();
				DefaultTableModel model=(DefaultTableModel) table_j.getModel();
				id.setText(model.getValueAt(i, 0).toString());
				fname.setText(model.getValueAt(i, 1).toString());
				lname.setText(model.getValueAt(i, 2).toString());
				email.setText(model.getValueAt(i, 3).toString());
				contact.setText(model.getValueAt(i, 4).toString());
				address.setText(model.getValueAt(i, 5).toString());
			}
		});
		scrollPane.setBounds(461, 10, 438, 350);
		contentPane.add(scrollPane);
		
		table_j = new JTable();
		scrollPane.setViewportView(table_j);
		
		JButton display = new JButton("Display Data");
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql="select * from Stud_Details";
					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Students","root","Janhavi@31");
					pst=conn.prepareStatement(sql);
					rs=pst.executeQuery();
					ResultSetMetaData rsmd=rs.getMetaData();
					DefaultTableModel model=(DefaultTableModel)table_j.getModel();
					model.setRowCount(0);
					int cols = rsmd.getColumnCount();
					String colName[]=new String[cols];
					for(int i=0;i<cols;i++)
						colName[i]=rsmd.getColumnName(i+1);
					model.setColumnIdentifiers(colName);
					String id,fname,lname,contact,email,address;
					while(rs.next()) {
						id=rs.getString(1);
						fname=rs.getString(2);
						lname=rs.getString(3);
						email=rs.getString(4);
						contact=rs.getString(5);
						address=rs.getString(6);
						String[] row= {id,fname,lname,email,contact,address};
						model.addRow(row);
						}
					
					
					
						
					
				}catch(Exception ex) {}
			}
		});
		display.setFont(new Font("Tahoma", Font.BOLD, 15));
		display.setBounds(461, 392, 156, 44);
		contentPane.add(display);
		
		JButton clear = new JButton("Clear Table\r\n");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_j.setModel(new DefaultTableModel());
			}
		});
		clear.setFont(new Font("Tahoma", Font.BOLD, 15));
		clear.setBounds(743, 392, 156, 44);
		contentPane.add(clear);
	}
}
