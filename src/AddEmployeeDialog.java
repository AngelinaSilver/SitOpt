import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class AddEmployeeDialog extends JDialog {
    JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameTxt = new JTextField( 20);
    //JLabel roomLabel = new JLabel("Type of room: ");
    String room[] = { "Cubic", "Office" };
    static JComboBox roomType;
    static JComboBox group;
    JCheckBox isOffice = new JCheckBox("Need an office");
    JCheckBox isAlone = new JCheckBox("Must sit alone");

    private JButton confirmBtn = new JButton("Add");

    public AddEmployeeDialog(JFrame frame, String title, Company company) {
        super(frame, title, false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameTxt, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        group = new JComboBox();
        Iterator groupIt = company._groups.iterator();
        //group.addItem("");
        while (groupIt.hasNext()) {
            Group g = (Group) groupIt.next();
            group.addItem(g.get_name());
        }
        panel.add(group , gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(isOffice, gbc);



        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(isAlone, gbc);

        JLabel spacer = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(spacer,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(confirmBtn, gbc );


        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Employee emp = new Employee(company.get_next_employee_id(), nameTxt.getText());
                emp.set_mustSitAlone(isAlone.isSelected());
                emp.set_mustSitInRoom(isOffice.isSelected());
                //TO DO RACHEL - add group
                //emp.set_group(gr);
                String group_name = group.getSelectedItem().toString();
                emp.set_group(company.getGroupByName(group_name));
                company.add_employee(emp);
                System.out.println("A new employee: " + emp.get_name() + " has been added with ID: " + emp.get_id() + " from group " + emp.get_group().get_name());
            }
        } );

        add(panel);
        pack();
        setLocationRelativeTo(frame);
    }


}