package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class Payment extends JFrame {
	
	static DecimalFormat priceformatter = new DecimalFormat("#0.00");

	private JPanel contentPane;
	private JTextField custpayfield;

	/**
	 * Create the frame.
	 */
	public Payment(String orderid, double payment) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String selectorbutton[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Cancel payment? You order data still keep until you close Order window. Make a payment by click Pay button on Order window","Exit Payment Window",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,selectorbutton,selectorbutton[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            dispose();
		            NewOrder.setpaymentframenull();
		        }
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Payment.class.getResource("/main/logo/logo.png")));
		setTitle(Main.getappname());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 844, 479);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(105, 88, 95));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(51, 102, 0));
		
		JLabel lblPayment = new JLabel("Payment for Order ID: " + orderid);
		lblPayment.setIcon(new ImageIcon(Payment.class.getResource("/main/logo/mobile-payment.png")));
		lblPayment.setForeground(Color.WHITE);
		lblPayment.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 835, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(lblPayment)
					.addContainerGap(718, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 68, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(15, Short.MAX_VALUE)
					.addComponent(lblPayment)
					.addGap(21))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 204, 204));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 153, 51));
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(204, 153, 51));
		
		JLabel lblNewLabel_1_1 = new JLabel("Enter Amount (RM)");
		lblNewLabel_1_1.setBackground(new Color(153, 51, 0));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		custpayfield = new JTextField();
		custpayfield.setFont(new Font("SansSerif", Font.PLAIN, 16));
		custpayfield.setColumns(10);
		GroupLayout gl_panel_2_1 = new GroupLayout(panel_2_1);
		gl_panel_2_1.setHorizontalGroup(
			gl_panel_2_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2_1.createParallelGroup(Alignment.LEADING)
						.addComponent(custpayfield, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2_1.setVerticalGroup(
			gl_panel_2_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1_1)
					.addGap(10)
					.addComponent(custpayfield, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_2_1.setLayout(gl_panel_2_1);
		
		
		JRadioButton creditcardtype = new JRadioButton("Credit Card");
		creditcardtype.setBackground(new Color(255, 255, 153));
		JRadioButton cashtype = new JRadioButton("Cash");
		cashtype.setBackground(new Color(255, 255, 153));
		JRadioButton debittype = new JRadioButton("Debit");
		debittype.setBackground(new Color(255, 255, 153));
		
		cashtype.setActionCommand("Cash");
		debittype.setActionCommand("Debit");
		creditcardtype.setActionCommand("Credit Card");
		
		ButtonGroup paymenttypeselector = new ButtonGroup();
		paymenttypeselector.add(cashtype);
		paymenttypeselector.add(debittype);
		paymenttypeselector.add(creditcardtype);
		
		
		JButton btnNewButton = new JButton("Pay");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// PROCESS STATE
				boolean process = false;
				
				// ERROR STATE
				boolean custpayvalueerror = false;
				boolean paymenttypeerror = false;
				boolean insufficientbalance = false;
				
				double custpayvalue = 0;
				String paymenttype = null;
				
				//GET PAYMENT TYPE
				try {
					paymenttype = paymenttypeselector.getSelection().getActionCommand();
				}catch(Exception e1) {
					paymenttypeerror = true;
					System.out.println("No Value Selected: " + e1.getMessage());
				}
				
				//GET PAY VALUE
				try {
					custpayvalue = Double.parseDouble(custpayfield.getText());
					//CHECK IF PAY VALUE BELOW THAN PRICE
					if(custpayvalue < payment) {
						insufficientbalance = true;
					}
				}catch(Exception e1) {
					custpayvalueerror = true;
					System.out.println("INVALID PAY VALUE: " + e1.getMessage());
				}
				
				// ERROR MESSAGE
				if (custpayvalueerror || paymenttypeerror || insufficientbalance) {
					String error = "Payment unable to proceed:";
					if (custpayvalueerror) {
						error += "\nInvalid pay value";
					}
					if (paymenttypeerror) {
						error += "\nPayment type not selected";
					}
					if (insufficientbalance) {
						error += "\nInsufficient balance";
					}
					JOptionPane.showMessageDialog(null, error, "Error Payment. ID: " + orderid, JOptionPane.ERROR_MESSAGE);
				} else {
					process = true;
				}
				
				//PAYMENT PROCESS DATA
				if(process == true) {
					Main.getpayment().add(new Paymentclass(orderid, paymenttype, payment, custpayvalue));
					Receipt receiptframe = new Receipt(orderid);
					receiptframe.setVisible(true);
					Cashierframe.getorderframe().dispose();
					Cashierframe.showdata();
					NewOrder.listpricecust = 0;
					NewOrder.finalprice = 0;
					NewOrder.regularcustomer = false;
					dispose();
				}
			}
		});
		btnNewButton.setBackground(new Color(51, 153, 255));
		btnNewButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 810, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2_1, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(341, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(182)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(521, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_2_1, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
					.addGap(43)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		
		JLabel lblNewLabel_1 = new JLabel("Total");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel pricedisplay = new JLabel("RM " + priceformatter.format(payment));
		pricedisplay.setFont(new Font("SansSerif", Font.PLAIN, 18));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(pricedisplay, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addGap(20))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pricedisplay, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
					.addGap(6))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Choose Payment Type");
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(25)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(debittype)
					.addGap(18)
					.addComponent(cashtype, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(creditcardtype, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(392))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(23, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(creditcardtype, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cashtype, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(debittype, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(15))
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
	}
}
