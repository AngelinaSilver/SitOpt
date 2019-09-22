import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Vector;

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
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton addBtn = new JButton("Add New Employee");
        headerPanel.add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Company company = SimpleExample.create_company1();
                AddEmployeeDialog dialog = new AddEmployeeDialog(frame, "Add new Employee", MainPanel.company);
                dialog.setSize(500, 1000);
                dialog.pack();
                dialog.setVisible(true);

            }
        });

        JButton solveBtn = new JButton("Solve Occupancy");
        //solveBtn.setBackground(Color.);
        solveBtn.setToolTipText("One or more employees don't have attributed place! solve!");
        headerPanel.add(solveBtn);

        JButton applyBtn = new JButton("Apply Solution");
        headerPanel.add(applyBtn);

        JButton cancelBtn = new JButton("Cancel Solution");
        headerPanel.add(cancelBtn);

        applyBtn.setVisible(false);
        cancelBtn.setVisible(false);

        solveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //save from xml
                //read
                try {
                    MainPanel.backup_comp = new Company();
                    SimpleExample.save_to_xml("C:\\Hackathon\\backup.xml", MainPanel.company);
                    MainPanel.backup_comp = SimpleExample.load_xml("C:\\Hackathon\\backup.xml");
                    boolean res = MainPanel.solver.populate(MainPanel.company);
                    System.out.println("res = " + res);
                    if(res == true) {
                        UIBuildingPopulation bp = new UIBuildingPopulation(Seating.this);
                        bp.populateOffice(MainPanel.backup_comp, MainPanel.company);
                        applyBtn.setVisible(true);
                        cancelBtn.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(bodyPanel, "No changes required. All Employees ");
                    }

                }
                catch (Exception exc) {
                    System.out.println(exc.getMessage());
                }
                //if res = true -> something has changed

            }
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UIBuildingPopulation bp = new UIBuildingPopulation(Seating.this);
                bp.populateOffice(MainPanel.company, null);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainPanel.company = SimpleExample.load_xml("C:\\Hackathon\\backup.xml");
                UIBuildingPopulation bp = new UIBuildingPopulation(Seating.this);
                bp.populateOffice(MainPanel.company, null);
            }
        });
        //body panel
        bodyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        bodyPanel.setLayout(null);

        //occupyPlace(space);
        //bodyPanel.removeAll();





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

/*
    public class MyCircle extends JPanel{
        int x, y;

        public MyCircle(int x, int y) {
            this.x = x;
            this.y = y;


        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            //g2.drawOval(x,y,30,30);
            g2.fillOval(x,y,30,30);


        }

        /*public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 10, 10);

            g2d.setColor(Color.GRAY);
            g2d.fill(circle);
        }

    }*/



    //X AND Y- THE TOP LEFT CORNER
    public void occupyPlace(Space space, Vector<Employee> leave, Vector<Employee> add ){
        int x=space.get_x();
        int y=space.get_y();
        int w=space.get_w();
        int h=space.get_h();
        int capacity= space.get_capacity();
        int numOfSeats = space._seats.size();
        int countr = numOfSeats;
        //int toHightlightCounter=hightlightVacant;
        int dist=0;

        for(int i=0; i<capacity; i++){
            final GuiPerson person = new GuiPerson();
            person.setSize(15, 15);
            person.setLocation(x+dist, y);
            if(countr >0){
                //taken seat
                person.setBackground(Color.RED);
                Employee e = space._seats.elementAt(i);
                person.setEmployee(e);
                person.setToolTipText("<html> Current Occupant: ID= " +e.get_id()+" Name="+e.get_name()+"</html>");
                if(leave.contains(e)){
                    //need to highlight this person
                    person.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                    if(add.size()>0){ //there is new person
                        Employee em = add.get(0);
                        String toolTip = "<html> Current Occupant: ID= " +e.get_id()+" Name="+e.get_name()+" <br> Future Occupation: ID= "+ em.get_id() +"Name="+ em.get_name()+" </html> ";
                        person.setToolTipText(toolTip);
                        //toolTip
                        add.remove(0);
                    }

                }
                countr--;
            }
            else{
                person.setBackground(Color.GREEN);
                if(add.size()>0){
                    person.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                    Employee e = add.get(0);
                    person.setToolTipText("<html> Future Occupation: ID= " +e.get_id()+" Name="+e.get_name()+"</html>");
                    add.remove(0);
                    //change ToolTip + delete person
                }

                /*if(toHightlightCounter>0){//need to highlight this person
                    //hightlight
                    person.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
                    toHightlightCounter--;
                }*/
            }
            dist = dist +20;
            //return this code
            //button.setBackground(color);
        /*button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setSelected(true);
                //return this
                //infoLabel.setText("Information about this place: \n"+tempMethod(color)+" sits here");
                bottomPanel.repaint();
            }
        });*/
            bodyPanel.add(person);
            bodyPanel.repaint();

        }

    }

    public void clearComponents(){
        bodyPanel.removeAll(); //removeAllButtons
    }

    private String tempMethod(Color color) {
        if (color== Color.RED){
            return "The Red Team";
        }else{
            return "The green Team";
        }
    }

    public class GuiPerson extends JButton{
        Employee employee;

        public GuiPerson(){
            super();
        }

        public void setEmployee(Employee employee) {
            this.employee = employee;
        }
    }

}
