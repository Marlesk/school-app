package gr.aueb.cf.schoolapp.view_controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.schoolapp.Main;
import gr.aueb.cf.schoolapp.dao.CityDAOImpl;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.TeacherDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.exceptions.TeacherAlreadyExistsException;
import gr.aueb.cf.schoolapp.exceptions.TeacherNotFoundException;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.CityServiceImpl;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.TeacherServiceImpl;
import gr.aueb.cf.schoolapp.validator.TeacherValidator;

public class UpdateTeacherPage extends JFrame {

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
	private JComboBox<City> cityComboBox;
	private List<City> cities = new ArrayList<>();

	private final ICityDAO cityDao = new CityDAOImpl();
	private final ICityService cityService = new CityServiceImpl(cityDao);

	private final ITeacherDAO teacherDAO = new TeacherDAOImpl();
	private final ITeacherService teacherService = new TeacherServiceImpl(teacherDAO);

	TeacherReadOnlyDTO dto;

	public UpdateTeacherPage() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					cities = cityService.getAllCities();
					cities.forEach(cityComboBox::addItem);
				} catch (SQLException ex) {
					// ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Get cities fatal error.", "Error", JOptionPane.ERROR_MESSAGE);
				}

				fetchTeacherFromDatabase(Main.getViewTeachersPage().getSelectedId());

				errorFirstname.setText("");
				errorLastname.setText("");
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

		vatText = new JTextField();
		vatText.setColumns(10);
		vatText.setBounds(91, 198, 263, 37);
		contentPane.add(vatText);

		JLabel lblFathername = new JLabel("Πατρώνυμο*");
		lblFathername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFathername.setBounds(421, 202, 95, 29);
		contentPane.add(lblFathername);

		fathernameText = new JTextField();
		fathernameText.setColumns(10);
		fathernameText.setBounds(512, 198, 263, 37);
		contentPane.add(fathernameText);

		JLabel lblPhoneNumber = new JLabel("Τηλέφωνο*");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhoneNumber.setBounds(10, 275, 81, 29);
		contentPane.add(lblPhoneNumber);

		phoneNumberText = new JTextField();
		phoneNumberText.setColumns(10);
		phoneNumberText.setBounds(91, 271, 263, 37);
		contentPane.add(phoneNumberText);

		JLabel lblEmail = new JLabel("e-mail*");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmail.setBounds(460, 275, 56, 29);
		contentPane.add(lblEmail);

		emailText = new JTextField();
		emailText.setColumns(10);
		emailText.setBounds(512, 271, 263, 37);
		contentPane.add(emailText);

		JLabel lblStreet = new JLabel("Διεύθυνση*");
		lblStreet.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStreet.setBounds(10, 342, 81, 29);
		contentPane.add(lblStreet);

		streetText = new JTextField();
		streetText.setColumns(10);
		streetText.setBounds(91, 338, 263, 37);
		contentPane.add(streetText);

		JLabel lblStreetNumber = new JLabel("Αριθμός*");
		lblStreetNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStreetNumber.setBounds(446, 342, 70, 29);
		contentPane.add(lblStreetNumber);

		streetNumberText = new JTextField();
		streetNumberText.setColumns(10);
		streetNumberText.setBounds(512, 338, 263, 37);
		contentPane.add(streetNumberText);

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

		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setBounds(92, 167, 260, 29);
		contentPane.add(errorFirstname);

		errorLastname = new JLabel("");
		errorLastname.setForeground(new Color(255, 0, 0));
		errorLastname.setBounds(514, 167, 260, 29);
		contentPane.add(errorLastname);

		cityComboBox = new JComboBox<>();
		cityComboBox.setBounds(89, 407, 263, 37);
		contentPane.add(cityComboBox);


		JButton updateBtn = new JButton("Ενημέρωση");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Data binding
				TeacherUpdateDTO updateDTO = doDataBinding();


				// Validation
				Map<String, String> errors = TeacherValidator.validate(updateDTO);
				if (!errors.isEmpty()) {
					errorFirstname.setText(errors.getOrDefault("firstname", ""));
					errorLastname.setText(errors.getOrDefault("lastname", ""));
					return;
				}

				try {
					TeacherReadOnlyDTO teacherReadOnlyDTO = teacherService.updateTeacher(dto.getId(), updateDTO);
					Main.getUpdateSuccessPage().setUuidText(teacherReadOnlyDTO.getUuid());
					Main.getUpdateTeacherPage().setEnabled(false);
					Main.getUpdateSuccessPage().setVisible(true);
				} catch (TeacherDAOException ex) {
					JOptionPane.showMessageDialog(null, "DB Error", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (TeacherAlreadyExistsException ex) {
					JOptionPane.showMessageDialog(null, "Teacher already exists", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (TeacherNotFoundException ex) {
					JOptionPane.showMessageDialog(null, "Teacher not found", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		updateBtn.setForeground(new Color(255, 255, 255));
		updateBtn.setBackground(new Color(64, 128, 128));
		updateBtn.setBounds(512, 485, 263, 54);
		contentPane.add(updateBtn);

		JButton closeBtn = new JButton("Κλείσιμο");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getViewTeachersPage().setEnabled(true);
				Main.getUpdateTeacherPage().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 0));
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		closeBtn.setBackground(new Color(192, 192, 192));
		closeBtn.setBounds(91, 485, 263, 54);
		contentPane.add(closeBtn);
	}

	private TeacherUpdateDTO doDataBinding() {
		Integer id = dto.getId();
		String firstname = firstnameText.getText().trim();
		String lastname = lastnameText.getText().trim();
		String vat = vatText.getText();
		String fathername = fathernameText.getText();
		String phoneNumber = phoneNumberText.getText();
		String email = emailText.getText();
		String street = streetText.getText();
		String streetNumber = streetNumberText.getText();
		City selectedCity = (City) cityComboBox.getSelectedItem();
		int cityId = (selectedCity != null) ? selectedCity.getId() : 1;
		String zipcode = zipcodeText.getText();

		return new TeacherUpdateDTO(firstname, lastname, vat, fathername,
				phoneNumber, email, street, streetNumber, zipcode, cityId, id);
	}

	private void fetchTeacherFromDatabase(int id) {
		try {
			dto = teacherService.getTeacherById(id);

			firstnameText.setText(dto.getFirstname());
			lastnameText.setText(dto.getLastname());
			vatText.setText(dto.getVat());
			fathernameText.setText(dto.getFatherName());
			phoneNumberText.setText(dto.getPhoneNum());
			emailText.setText(dto.getEmail());
			streetText.setText(dto.getStreet());
			streetNumberText.setText(dto.getStreetNum());

			int cityIdFromDB = dto.getCityId(); // Get city_id from DB

			// Find the matching city using Streams
			City selectedCity = cities.stream()
					.filter(city -> city.getId() == cityIdFromDB)
					.findFirst()
					.orElse(null); // Returns null if no match is found

			// Select the city in the JComboBox
			if (selectedCity != null) {
				cityComboBox.setSelectedItem(selectedCity);
			} else cityComboBox.setSelectedIndex(0);

			zipcodeText.setText(dto.getZipCode());
			errorFirstname.setText("");
			errorLastname.setText("");

		} catch (TeacherDAOException | TeacherNotFoundException e) {
			//e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Select error in fetch teacher", "Error", JOptionPane.ERROR_MESSAGE);
			Main.getViewTeachersPage().setEnabled(true);
			Main.getUpdateTeacherPage().setVisible(false);
		}

	}




}