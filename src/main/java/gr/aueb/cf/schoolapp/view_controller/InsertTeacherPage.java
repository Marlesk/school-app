package gr.aueb.cf.schoolapp.view_controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.CityServiceImpl;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertTeacherPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JTextField vatText;
	private JTextField fathernameText;
	private JTextField phoneNumberText;
	private JTextField emailText;
	private JTextField streetText;
	private JTextField streetNumberText;
	private JTextField zipcodeText;
	private JLabel errorFirstname;
	private JLabel errorLastname;
	private JLabel errorVat;
	private JLabel errorPhoneNumber;
	private JLabel errorStreet;
	private JLabel errorCity;
	private JLabel errorFathername;
	private JLabel errorEmail;
	private JLabel errorStreetNumber;
	private JLabel errorZipCode;
	private JComboBox<City> cityComboBox;
	private List<City> cities = new ArrayList<>();

	private final ICityDAO cityDAO = new CityDAOImpl();
	private final ICityService cityService = new CityServiceImpl(cityDAO);

	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

	public InsertTeacherPage() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					City placeholderCity = new City(0, "--Επιλέξτε πόλη--");
					cityComboBox.addItem(placeholderCity);

					//cities = cityService.getAllCities();
					cityService.getAllCities().forEach(cityComboBox::addItem);
					//resetInputForm();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Get cities fatal error.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Ποιότητα στην Εκπαίδευση");
		setBounds(100, 100, 857, 701);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTeacherInfo = new JLabel("Στοιχεία Εκπαιδευτή");
		lblTeacherInfo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTeacherInfo.setBounds(267, 63, 341, 44);
		contentPane.add(lblTeacherInfo);
		
		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(new Color(0, 52, 117));
		header.setBounds(0, 0, 842, 52);
		contentPane.add(header);
		
		JLabel govImage = new JLabel("");
		govImage.setIcon(new ImageIcon(InsertTeacherPage.class.getResource("/images/gov_logo_small.png")));
		govImage.setBounds(0, 0, 100, 52);
		header.add(govImage);
		
		JLabel firstLastName = new JLabel("ΜΑΡΙΑ ΛΕΣΚΑ");
		firstLastName.setForeground(Color.WHITE);
		firstLastName.setBounds(649, 11, 183, 30);
		header.add(firstLastName);
		
		JPanel footer = new JPanel();
		footer.setLayout(null);
		footer.setBounds(0, 574, 842, 90);
		contentPane.add(footer);
		
		JLabel lbl_manual = new JLabel("Εγχειρίδιο Χρήσης");
		lbl_manual.setForeground(new Color(0, 52, 117));
		lbl_manual.setBounds(199, 37, 151, 29);
		footer.add(lbl_manual);
		
		JLabel lbl_oftenQuestions = new JLabel("Συχνές Ερωτήσεις");
		lbl_oftenQuestions.setForeground(new Color(0, 52, 117));
		lbl_oftenQuestions.setBounds(360, 37, 151, 29);
		footer.add(lbl_oftenQuestions);
		
		JLabel lbl_support = new JLabel("Υποστήριξη Πολιτών");
		lbl_support.setForeground(new Color(0, 52, 117));
		lbl_support.setBounds(521, 37, 151, 29);
		footer.add(lbl_support);
		
		JSeparator lineBottom = new JSeparator();
		lineBottom.setBackground(Color.BLUE);
		lineBottom.setBounds(0, 0, 850, 2);
		footer.add(lineBottom);
		
		firstnameText = new JTextField();
		firstnameText.setBounds(91, 131, 263, 37);
		contentPane.add(firstnameText);
		firstnameText.setColumns(10);
		
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname;
				inputFirstname = firstnameText.getText().trim();

				errorFirstname.setText(inputFirstname.equals("") ? "Το όνομα είναι υποχρεωτικό" : "");
			}
		});
		
		lastnameText = new JTextField();
		lastnameText.setColumns(10);
		lastnameText.setBounds(512, 131, 263, 37);
		contentPane.add(lastnameText);
		
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname;	
				inputLastname = lastnameText.getText().trim();	
				
				errorLastname.setText(inputLastname.equals("") ? "Το επώνυμο είναι υποχρεωτικό" : "");
			}
		});
		
		vatText = new JTextField();
		vatText.setColumns(10);
		vatText.setBounds(91, 198, 263, 37);
		contentPane.add(vatText);
		
		vatText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputVat;	
				inputVat = vatText.getText().trim();	
				
				errorVat.setText(inputVat.equals("") ? "Το ΑΦΜ είναι υποχρεωτικό" : "");
			}
		});
		
		
		
		JLabel lblFirstname = new JLabel("Όνομα*");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstname.setBounds(29, 135, 62, 29);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Επώνυμο*");
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastname.setBounds(432, 135, 84, 29);
		contentPane.add(lblLastname);
		
		
		JLabel lblVat = new JLabel("ΑΦΜ*");
		lblVat.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVat.setBounds(41, 202, 50, 29);
		contentPane.add(lblVat);
		
		JLabel lblFathername = new JLabel("Πατρώνυμο*");
		lblFathername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFathername.setBounds(421, 202, 95, 29);
		contentPane.add(lblFathername);
		
		fathernameText = new JTextField();
		fathernameText.setColumns(10);
		fathernameText.setBounds(512, 198, 263, 37);
		contentPane.add(fathernameText);
		
		fathernameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFathername;	
				inputFathername = fathernameText.getText().trim();	
				
				errorFathername.setText(inputFathername.equals("") ? "Το πατρώνυμο είναι υποχρεωτικό" : "");
			}
		});
		
		JLabel lblPhoneNumber = new JLabel("Τηλέφωνο*");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhoneNumber.setBounds(10, 275, 81, 29);
		contentPane.add(lblPhoneNumber);
		
		phoneNumberText = new JTextField();
		phoneNumberText.setColumns(10);
		phoneNumberText.setBounds(91, 271, 263, 37);
		contentPane.add(phoneNumberText);
		
		phoneNumberText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputPhoneNumber;	
				inputPhoneNumber = phoneNumberText.getText().trim();	
				
				errorPhoneNumber.setText(inputPhoneNumber.equals("") ? "Το τηλέφωνο είναι υποχρεωτικό" : "");
			}
		});
		
		JLabel lblEmail = new JLabel("e-mail*");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(460, 275, 56, 29);
		contentPane.add(lblEmail);
		
		emailText = new JTextField();
		emailText.setColumns(10);
		emailText.setBounds(512, 271, 263, 37);
		contentPane.add(emailText);
		
		emailText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputEmail;	
				inputEmail = emailText.getText().trim();	
				
				errorEmail.setText(inputEmail.equals("") ? "Το email είναι υποχρεωτικό" : "");
			}
		});
		
		JLabel lblStreet = new JLabel("Διεύθυνση*");
		lblStreet.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStreet.setBounds(10, 342, 81, 29);
		contentPane.add(lblStreet);
		
		streetText = new JTextField();
		streetText.setColumns(10);
		streetText.setBounds(91, 338, 263, 37);
		contentPane.add(streetText);
		
		streetText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputStreet;	
				inputStreet = streetText.getText().trim();	
				
				errorStreet.setText(inputStreet.equals("") ? "Η Διεύθυνση είναι υποχρεωτική" : "");
			}
		});
		
		JLabel lblStreetNumber = new JLabel("Αριθμός*");
		lblStreetNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStreetNumber.setBounds(446, 342, 70, 29);
		contentPane.add(lblStreetNumber);
		
		streetNumberText = new JTextField();
		streetNumberText.setColumns(10);
		streetNumberText.setBounds(512, 338, 263, 37);
		contentPane.add(streetNumberText);
		
		streetNumberText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputStreetNumber;	
				inputStreetNumber = streetNumberText.getText().trim();	
				
				errorStreetNumber.setText(inputStreetNumber.equals("") ? "Ο αριθμός είναι υποχρεωτικός" : "");
			}
		});
		
		JLabel lblCity = new JLabel("Πόλη*");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCity.setBounds(41, 411, 50, 29);
		contentPane.add(lblCity);
		
		JLabel lblZipCode = new JLabel("ΤΚ*");
		lblZipCode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblZipCode.setBounds(481, 411, 35, 29);
		contentPane.add(lblZipCode);
		
		zipcodeText = new JTextField();
		zipcodeText.setColumns(10);
		zipcodeText.setBounds(512, 407, 263, 37);
		contentPane.add(zipcodeText);
		
		zipcodeText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputzipcode;	
				inputzipcode = zipcodeText.getText().trim();	
				
				errorZipCode.setText(inputzipcode.equals("") ? "Ο ΤΚ είναι υποχρεωτικός" : "");
			}
		});
		
		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setBounds(92, 167, 260, 29);
		contentPane.add(errorFirstname);
		
		errorLastname = new JLabel("");
		errorLastname.setForeground(new Color(255, 0, 0));
		errorLastname.setBounds(514, 167, 260, 29);
		contentPane.add(errorLastname);
		
		errorVat = new JLabel("");
		errorVat.setForeground(new Color(255, 0, 0));
		errorVat.setBounds(92, 235, 260, 29);
		contentPane.add(errorVat);
		
		errorPhoneNumber = new JLabel("");
		errorPhoneNumber.setForeground(new Color(255, 0, 0));
		errorPhoneNumber.setBounds(92, 307, 260, 29);
		contentPane.add(errorPhoneNumber);
		
		errorStreet = new JLabel("");
		errorStreet.setForeground(new Color(255, 0, 0));
		errorStreet.setBounds(92, 374, 260, 29);
		contentPane.add(errorStreet);
		
		errorCity = new JLabel("");
		errorCity.setForeground(new Color(255, 0, 0));
		errorCity.setBounds(91, 445, 260, 29);
		contentPane.add(errorCity);
		
		errorFathername = new JLabel("");
		errorFathername.setForeground(new Color(255, 0, 0));
		errorFathername.setBounds(514, 236, 260, 29);
		contentPane.add(errorFathername);
		
		errorEmail = new JLabel("");
		errorEmail.setForeground(new Color(255, 0, 0));
		errorEmail.setBounds(514, 307, 260, 29);
		contentPane.add(errorEmail);
		
		errorStreetNumber = new JLabel("");
		errorStreetNumber.setForeground(new Color(255, 0, 0));
		errorStreetNumber.setBounds(514, 375, 260, 29);
		contentPane.add(errorStreetNumber);
		
		errorZipCode = new JLabel("");
		errorZipCode.setForeground(new Color(255, 0, 0));
		errorZipCode.setBounds(514, 446, 260, 29);
		contentPane.add(errorZipCode);
		
		cityComboBox = new JComboBox<>();
		cityComboBox.setBounds(89, 407, 263, 37);
		contentPane.add(cityComboBox);
        
		
		JButton insertBtn = new JButton("Υποβολή");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Data Binding
				TeacherReadOnlyDTO teacherReadOnlyDTO;
				TeacherInsertDTO insertDTO = doDataBinding();


				// Validation
				Map<String , String> errors = TeacherValidator.validate(insertDTO);
				if (!errors.isEmpty()) {
					errorFirstname.setText(errors.getOrDefault("firstname", ""));
					errorLastname.setText(errors.getOrDefault("lastname", ""));
					errorVat.setText(errors.getOrDefault("vat", ""));
					errorFathername.setText(errors.getOrDefault("fathername", ""));
					errorEmail.setText(errors.getOrDefault("email", ""));
					errorPhoneNumber.setText(errors.getOrDefault("phoneNum", ""));
					errorStreet.setText(errors.getOrDefault("street", ""));
					errorStreetNumber.setText(errors.getOrDefault("streetNum", ""));
					errorZipCode.setText(errors.getOrDefault("zipCode", ""));
					errorCity.setText(errors.getOrDefault("city", ""));
					return;
				}



				try {
					teacherReadOnlyDTO = teacherService.insertTeacher(insertDTO);
					Main.getInsertSuccessPage().setUuidText(teacherReadOnlyDTO.getUuid());
					Main.getInsertTeacherPage().setEnabled(false);
					Main.getInsertSuccessPage().setVisible(true);
					resetInputForm();
				} catch (TeacherDAOException ex) {
					JOptionPane.showMessageDialog(null, "DB Error", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (TeacherAlreadyExistsException ex) {
					JOptionPane.showMessageDialog(null, "Teacher already exists", "Error", JOptionPane.ERROR_MESSAGE);
				}
               
				

			}
		});
		
		insertBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		insertBtn.setForeground(new Color(255, 255, 255));
		insertBtn.setBackground(new Color(64, 128, 128));
		insertBtn.setBounds(512, 485, 263, 54);
		contentPane.add(insertBtn);
		
		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getInsertTeacherPage().setVisible(false);
				Main.getDashboard().setEnabled(true);
				Main.getViewTeachersPage().refreshTable();


			}
		});

		closeBtn.setForeground(new Color(0, 0, 0));
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		closeBtn.setBackground(new Color(192, 192, 192));
		closeBtn.setBounds(91, 485, 263, 54);
		contentPane.add(closeBtn);

	}

	private TeacherInsertDTO doDataBinding() {
		final int DEFAULT_CITY_ID = 1;
		String firstname = firstnameText.getText().trim();
		String lastname = lastnameText.getText().trim();
		String vat = vatText.getText().trim();
		String fathername = fathernameText.getText().trim();
		String phoneNumber = phoneNumberText.getText().trim();
		String email = emailText.getText().trim();
		String street = streetText.getText().trim();
		String streetNumber = streetNumberText.getText().trim();
		City selectedCity = (City) cityComboBox.getSelectedItem();
		int cityId = (selectedCity != null) ? selectedCity.getId() : DEFAULT_CITY_ID;
		String zipcode = zipcodeText.getText().trim();

		return new TeacherInsertDTO(firstname, lastname, vat, fathername,
				phoneNumber, email, street, streetNumber, zipcode, cityId);
	}

	private void resetInputForm() {
		firstnameText.setText("");
		lastnameText.setText("");
		vatText.setText("");
		fathernameText.setText("");
		phoneNumberText.setText("");
		emailText.setText("");
		streetText.setText("");
		streetNumberText.setText("");
		cityComboBox.setSelectedIndex(0);
		zipcodeText.setText("");
		errorFirstname.setText("");
		errorLastname.setText("");
		errorVat.setText("");
		errorPhoneNumber.setText("");
		errorStreet.setText("");
		errorCity.setText("");
		errorFathername.setText("");
		errorEmail.setText("");
		errorStreetNumber.setText("");
		errorZipCode.setText("");
	}
	

}