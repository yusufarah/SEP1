import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateReservationWindowGUI {
    private JFrame mainFrame;
    private JTabbedPane CreateReservationPane;
    private JPanel reservationPanel, leftPanel, rightPanel, guestDataLabels, guestDataTextFields, guestDataCheckBoxes, leftPanelButtons, rightPanelButtons;
    private JLabel firstNameLabel, middleNameLabel, lastNameLabel, countryLabel, cityLabel, postCodeLabel, streetLabel, phoneNumberLabel, nationalityLabel, dateOfBirthLabel, arrivalLabel, departureLabel, roomTypeLabel, bookingInitiatorLabel, lateArrivalNoticeLabel, priorityGuestLabel;
    private JTextField firstName, middleName, lastName, country, city, postCode, street, phoneNumber, nationality, dateOfBirth, arrival, departure;
    private JCheckBox bookingInitiator, lateArrivalNotice, priorityGuest;
    private JScrollPane allGuestScroll;
    private JButton save, clear, choose, refresh, cancel;
    private ArrayList<JLabel> allJLabels;
    private ArrayList<JTextField> allTextFields;
    private ArrayList<Reservation> allReservations;
    private MyButtonListener listener;
    private JComboBox<String> roomTypes;
    private KeyPressEvent presser;
    private JTable allGuests;
    private DefaultTableModel dtm;
    private String[] columnNames;
    private Object[][] resColumn;
    private ArrayList<Reservation> foundNames;
    private MyListSelectionListener tableSelect;
    private Reservation chosenReservation;
    private boolean canConvertToDateHandlerB, isCanConvertToDateHandlerA, isCanConvertToDateHandlerD;

    public CreateReservationWindowGUI() {

        left();
        takeAllGuest();
        right();
        prepareGUI();
    }

    public void prepareGUI() {
        listener = new MyButtonListener();
        presser = new KeyPressEvent();
        mainFrame = new JFrame("Create reservation");
        tableSelect = new MyListSelectionListener();
        left();

        right();

        reservationPanel = new JPanel();
        reservationPanel.setPreferredSize(new Dimension(1440, 960));
        reservationPanel.add(leftPanel, BorderLayout.WEST);
        reservationPanel.add(rightPanel, BorderLayout.EAST);
        bookingInitiator.doClick();
        CreateReservationPane = new JTabbedPane();
        CreateReservationPane.addTab("CreateReservation", reservationPanel);

        mainFrame.add(CreateReservationPane);
        mainFrame.setSize(1440, 960);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        // Exit the application when the window is closed
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center window to screen
        mainFrame.setLocationRelativeTo(null);
    }

    private class MyListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            int a = allGuests.getSelectedRow();
            chosenReservation = foundNames.get(a);
        }

    }


    private class MyButtonListener implements ActionListener {
        public boolean isValidDate(String str) {
            String[] arr = str.split("/");
            if (arr[0].chars().allMatch(Character::isDigit) && arr[0].length() == 2) {
                if (arr[1].chars().allMatch(Character::isDigit) && arr[1].length() == 2) {
                    if (arr[2].chars().allMatch(Character::isDigit) && arr[2].length() == 4) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean isValidPhoneNumber(String str) {
            Long a;
            try{
                a = Long.parseLong(str);
            } catch(Exception e) {
                return false;
            }
            return true;
        }

        public void clear() {
            firstName.setText("");
            middleName.setText("");
            lastName.setText("");
            country.setText("");
            city.setText("");
            postCode.setText("");
            street.setText("");
            phoneNumber.setText("");
            nationality.setText("");
            dateOfBirth.setText("");
            arrival.setText("");
            departure.setText("");
            if (lateArrivalNotice.isSelected()) {
                lateArrivalNotice.doClick();
            }
            if (priorityGuest.isSelected()) {
                priorityGuest.doClick();
            }
        }
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == save) {
                // dateOfBirth, arrival, departure is checked when pressing save
                if (!(isValidDate(dateOfBirth.getText()))) {
                    JOptionPane.showMessageDialog(null, "Date of birth " + dateOfBirth.getText() + " is not a valid date");
                }
                if (!(isValidDate(arrival.getText()))) {
                    JOptionPane.showMessageDialog(null, "Arrival date " + arrival.getText() + " is not a valid date");
                }
                if (!(isValidDate(departure.getText()))) {
                    JOptionPane.showMessageDialog(null, "Departure date " + departure.getText() + " is not a valid date");
                }
                if (!(isValidPhoneNumber(phoneNumber.getText()))){
                    JOptionPane.showMessageDialog(null, "Phone number " + phoneNumber.getText() + "is not a phone number");
                }
                createrForReservation();
                clear();

            } else if (e.getSource() == clear) {
                clear();
            } else if (e.getSource() == refresh) {
                takeAllGuest();
                createReservationTable(new ArrayList<Reservation>());
            } else if (e.getSource() == choose) {
                firstName.setText(chosenReservation.getGuest().getName().getFirstName());
                middleName.setText(chosenReservation.getGuest().getName().getMiddleName());
                lastName.setText(chosenReservation.getGuest().getName().getLastName());
                country.setText(chosenReservation.getGuest().getAddress().getCountry());
                city.setText(chosenReservation.getGuest().getAddress().getCity());
                postCode.setText(chosenReservation.getGuest().getAddress().getPostCode());
                street.setText(chosenReservation.getGuest().getAddress().getStreet());
                phoneNumber.setText(String.valueOf(chosenReservation.getGuest().getPhoneNumber()));
                nationality.setText(chosenReservation.getGuest().getNationality());
                dateOfBirth.setText(chosenReservation.getGuest().getDateOfBirth());
                roomTypes.setSelectedItem(chosenReservation.getRoomType());
                if (chosenReservation.isPriorityGuest()) {
                    priorityGuest.doClick();
                }
            } else if (e.getSource() == cancel) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you really want to exit the create reservation window?", "Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }

    }

    class KeyPressEvent implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
//            if(e.getKeyCode() == KeyEvent.VK_ENTER){
//
//            }
//            //System.out.println(e.getKeyChar());

        }

        public void keyReleased(KeyEvent e) {
            foundNames = new ArrayList<Reservation>();
            createReservationTable(allReservations);

            for (int i = 0; i < resColumn.length; i++) {
                String fullName = String.valueOf(resColumn[i][0].toString().toLowerCase());
                if (fullName.contains(firstName.getText().toLowerCase())) {
                    foundNames.add(allReservations.get(i));
                }
            }
            createReservationTable(foundNames);


        }
    }


    public void left() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(700, 650));

        save = new JButton("SAVE");
        save.addActionListener(listener);
        clear = new JButton("CLEAR");
        clear.addActionListener(listener);

        guestDataLabels = new JPanel(new GridLayout(13, 1, 2, 2));
        guestDataLabels.setPreferredSize(new Dimension(250, 450));

        guestDataCheckBoxes = new JPanel(new GridLayout(3, 3, 2, 2));
        guestDataCheckBoxes.setPreferredSize(new Dimension(450, 150));

        guestDataTextFields = new JPanel(new GridLayout(13, 1, 2, 2));
        guestDataTextFields.setPreferredSize(new Dimension(200, 450));

        leftPanelButtons = new JPanel();
        leftPanelButtons.setPreferredSize(new Dimension(600, 100));

        allTextFields = new ArrayList<JTextField>();
        allJLabels = new ArrayList<JLabel>();
        String[] roomTypesForComboBox = {"single room", "double room", "double room-twin beds", "double room-kingsize",
                "single bedroom suite", "double bedroom suite", "three bedroom suite"};
        roomTypes = new JComboBox<String>(roomTypesForComboBox);


        allTextFields.add(firstName = new JTextField());
        allTextFields.add(middleName = new JTextField());
        allTextFields.add(lastName = new JTextField());
        allTextFields.add(country = new JTextField());
        allTextFields.add(city = new JTextField());
        allTextFields.add(postCode = new JTextField());
        allTextFields.add(street = new JTextField());
        allTextFields.add(phoneNumber = new JTextField());
        allTextFields.add(nationality = new JTextField());
        allTextFields.add(dateOfBirth = new JTextField());
        allTextFields.add(arrival = new JTextField());
        allTextFields.add(departure = new JTextField());

        firstName.addKeyListener(presser);
        phoneNumber.addKeyListener(presser);
        allJLabels.add(firstNameLabel = new JLabel("First name"));
        allJLabels.add(middleNameLabel = new JLabel("Middle name"));
        allJLabels.add(lastNameLabel = new JLabel("Last name"));
        allJLabels.add(countryLabel = new JLabel("Country"));
        allJLabels.add(cityLabel = new JLabel("City"));
        allJLabels.add(postCodeLabel = new JLabel("Post code"));
        allJLabels.add(streetLabel = new JLabel("Street"));
        allJLabels.add(phoneNumberLabel = new JLabel("Phone number"));
        allJLabels.add(nationalityLabel = new JLabel("Nationality"));
        allJLabels.add(dateOfBirthLabel = new JLabel("Date of birth (dd/mm/yyyy)"));
        allJLabels.add(arrivalLabel = new JLabel("Arrival (dd/mm/yyyy)"));
        allJLabels.add(departureLabel = new JLabel("Departure (dd/mm/yyyy)"));
        allJLabels.add(roomTypeLabel = new JLabel("Room type"));


        guestDataCheckBoxes.add(bookingInitiatorLabel = new JLabel("Booking initiator"));
        guestDataCheckBoxes.add(bookingInitiator = new JCheckBox());
        guestDataCheckBoxes.add(lateArrivalNoticeLabel = new JLabel("Late arrival notice"));
        guestDataCheckBoxes.add(lateArrivalNotice = new JCheckBox());
        guestDataCheckBoxes.add(priorityGuestLabel = new JLabel("Priority guest"));
        guestDataCheckBoxes.add(priorityGuest = new JCheckBox());

        for (int i = 0; i < allJLabels.size(); i++) {
            guestDataLabels.add(allJLabels.get(i));
        }

        for (int i = 0; i < allTextFields.size(); i++) {
            guestDataTextFields.add(allTextFields.get(i));
        }
        guestDataTextFields.add(roomTypes);
        leftPanelButtons.add(save);
        leftPanelButtons.add(clear);
        leftPanel.add(guestDataLabels);
        leftPanel.add(guestDataTextFields);
        leftPanel.add(guestDataCheckBoxes);
        leftPanel.add(leftPanelButtons);
    }


    public void right() {

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(600, 650));

        rightPanelButtons = new JPanel();
        rightPanelButtons.setPreferredSize(new Dimension(600, 100));

        refresh = new JButton("REFRESH");
        refresh.addActionListener(listener);

        choose = new JButton("CHOOSE");
        choose.addActionListener(listener);

        cancel = new JButton("CANCEL");
        cancel.addActionListener(listener);

        columnNames = new String[5];
        columnNames[0] = "First name";
        columnNames[1] = "Middle name";
        columnNames[2] = "Last name";
        columnNames[3] = "Country";
        columnNames[4] = "Phone number";
        dtm = new DefaultTableModel(columnNames, 0);

        allGuests = new JTable(dtm) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        allGuests.getSelectionModel().addListSelectionListener(tableSelect);
        allGuestScroll = new JScrollPane(allGuests);
        allGuestScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        allGuestScroll.setPreferredSize(new Dimension(600, 600));
        allGuests.revalidate();
        rightPanelButtons.add(refresh);
        rightPanelButtons.add(choose);
        rightPanelButtons.add(cancel);
        rightPanel.add(allGuestScroll);
        rightPanel.add(rightPanelButtons);

    }

    public void takeAllGuest() {
        FileAdapter fa = new FileAdapter();
        ArrayList<Reservation> reservations = fa.getAllGuests("reservations.bin");
        ArrayList<Reservation> pastReservation = fa.getAllGuests("pastReservations.bin");
        ArrayList<Reservation> inHouse = fa.getAllGuests("inHouseGuests.bin");
        ArrayList<Reservation> temp = new ArrayList<Reservation>();
        allReservations = new ArrayList<Reservation>();

        temp.addAll(reservations);

        temp.addAll(pastReservation);

        temp.addAll(inHouse);

        for (int i = 0; i < temp.size(); i++) {
            allReservations.add(temp.get(i));
        }

    }

    // we decided to use the same method Allan is using in order to update the reservation table

    public void createReservationTable(ArrayList<Reservation> res) {
        resColumn = new Object[res.size()][5];

        for (int i = 0; i < res.size(); i++) {
            resColumn[i][0] = res.get(i).getGuest().getName().getFirstName();
            resColumn[i][1] = res.get(i).getGuest().getName().getMiddleName();
            resColumn[i][2] = res.get(i).getGuest().getName().getLastName();
            resColumn[i][3] = res.get(i).getGuest().getAddress().getCountry();
            resColumn[i][4] = res.get(i).getGuest().getPhoneNumber();
        }

        dtm = new DefaultTableModel(resColumn, columnNames);
        allGuests.setModel(dtm);
        allGuests.revalidate();


    }

    public void createrForReservation() {


        DateHandler date = new DateHandler(Integer.parseInt(dateOfBirth.getText().split("/")[0]),
                Integer.parseInt(dateOfBirth.getText().split("/")[1]),
                Integer.parseInt(dateOfBirth.getText().split("/")[2]));

        Arrival arr = new Arrival(new DateHandler(Integer.parseInt(arrival.getText().split("/")[0]),
                Integer.parseInt(arrival.getText().split("/")[1]),
                Integer.parseInt(arrival.getText().split("/")[2])));
        Departure dep = new Departure(new DateHandler(Integer.parseInt(departure.getText().split("/")[0]),
                Integer.parseInt(departure.getText().split("/")[1]),
                Integer.parseInt(departure.getText().split("/")[2])));
        Name name = new Name(firstName.getText(), middleName.getText(), lastName.getText());
        Address add = new Address(country.getText(), city.getText(), postCode.getText(), street.getText());
        Guest guest = new Guest(name, Long.parseLong(phoneNumber.getText()), add, nationality.getText(), dateOfBirth.getText());
        Reservation rs = new Reservation(guest, arr, dep, roomTypes.getName(), bookingInitiator.isSelected(),
                lateArrivalNotice.isSelected(), priorityGuest.isSelected());
        HotelManager hm = new HotelManager();
        hm.createReservation(rs);
    }


    public static void main(String[] args) {
        CreateReservationWindowGUI a = new CreateReservationWindowGUI();
    }

}