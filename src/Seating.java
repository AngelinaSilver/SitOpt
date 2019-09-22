import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;

enum EOccupancy{
    OCCUPIED{
        @Override
        public String getText() {
            return "Occupied";
        }

        @Override
        public Color getColor() {
            return Color.RED;
        }
    },
    VACANT{
        @Override
        public String getText() {
            return "Vacant";
        }

        @Override
        public Color getColor() {
            return Color.GREEN;
        }
    };

    public abstract String getText();
    public abstract Color getColor();

    @Override
    public String toString() {
        return getText();
    }
}
public class Seating {

    ImagePanel bodyPanel = new ImagePanel();
    JPanel headerPanel = new JPanel();
    JPanel bottomPanel = new JPanel();

    JTextArea infoLabel = new JTextArea();

    public void initPanel() {
        JFrame frame = new JFrame("Hello Office");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());

        contentPane.setOpaque(true);
        contentPane.setBackground(Color.WHITE);

        //header panel
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c= new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JLabel occupancyLabel = new JLabel("Occupancy: ");
        headerPanel.add(occupancyLabel, c);

        c.gridx = 1;
        final JComboBox<EOccupancy> occupancyCbx = new JComboBox<>();
        occupancyCbx.addItem(EOccupancy.OCCUPIED);
        occupancyCbx.addItem(EOccupancy.VACANT);
        headerPanel.add(occupancyCbx, c);


        c.gridy = 1;
        c.gridx = 0;
        JLabel locationLabel = new JLabel("Location: ");
        headerPanel.add(locationLabel, c);

        c.gridx = 1;
        final JTextField locationTxtX = new JTextField();
        headerPanel.add(locationTxtX, c);

        c.gridy = 2;
        final JTextField locationTxtY = new JTextField();
        headerPanel.add(locationTxtY, c);


        c.insets = new Insets(0, 70, 0, 0);
        c. gridx=2;
        c.gridy=1;
        c.ipadx = 10;
        JButton addBtn = new JButton("Add New Employee");
        headerPanel.add(addBtn, c);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*int x = Integer.parseInt(locationTxtX.getText());
                int y = Integer.parseInt(locationTxtY.getText());
                EOccupancy occupancyItem = (EOccupancy) occupancyCbx.getSelectedItem();
                JButton btn = new JButton();

                occupyPlace(x, y, 16, 16, occupancyItem.getColor());*/
                Company company = SimpleExample.create_company1();
                AddEmployeeDialog dialog = new AddEmployeeDialog(frame, "Add new Employee", company);
                dialog.setSize(500, 1000);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        //body panel
        bodyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        bodyPanel.setLayout(null);

        //JPanel imagePanel = new JPanel();
        //JPanel cardpanel = new JPanel(new CardLayout());

        //bottom panel
        infoLabel.setBackground(bottomPanel.getBackground());
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(infoLabel);

        contentPane.add(headerPanel, BorderLayout.NORTH);
        //contentPane.add(cardpanel, BorderLayout.CENTER);
        contentPane.add(bodyPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.repaint();

        frame.setContentPane(contentPane);
        frame.setSize(1310, 1125);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    private void populatePlace(Space space){

    }

    private void occupyPlace(int x, int y, int width, int height, final Color color){
        final JButton button = new JButton();
        button.setSize(width, height);
        button.setLocation(x, y);
        button.setBackground(color);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setSelected(true);
                infoLabel.setText("Information about this place: \n"+tempMethod(color)+" sits here");
                bottomPanel.repaint();
            }
        });
        bodyPanel.add(button);
        bodyPanel.repaint();
    }

    private String tempMethod(Color color) {
        if (color== Color.RED){
            return "The Red Team";
        }else{
            return "The green Team";
        }
    }

}
