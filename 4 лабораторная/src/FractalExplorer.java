import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;

public class FractalExplorer
{
    private int displaySize;

    private JImageDisplay display;

    private FractalGenerator fractal;

    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        displaySize = size;

        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
        
    }

    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame myFrame = new JFrame("Fractal Explorer");

        myFrame.add(display, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");

        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComboBox myComboBox = new JComboBox();

        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);

        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);

        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        myFrame.add(myPanel, BorderLayout.NORTH);

        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        myFrame.add(myBottomPanel, BorderLayout.SOUTH);

        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setResizable(false);
        
    }
    private void drawFractal()
    {
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){

                double xCoord = fractal.getCoord(range.x,
                range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                range.y + range.height, displaySize, y);
                int iteration = fractal.numIterations(xCoord, yCoord);

                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }
                
                else {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    display.drawPixel(x, y, rgbColor);
                }
                
            }
        }
        display.repaint();
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                fractal = (FractalGenerator) mySource.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
                
            }

            else if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }

            else if (command.equals("Save")) {

                JFileChooser myFileChooser = new JFileChooser();

                FileFilter extensionFilter =
                new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);

                myFileChooser.setAcceptAllFileFilterUsed(false);

                int userSelection = myFileChooser.showSaveDialog(display);

                if (userSelection == JFileChooser.APPROVE_OPTION) {

                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();

                    try {
                        BufferedImage displayImage = display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }

                    catch (Exception exception) {
                        JOptionPane.showMessageDialog(display,
                        exception.getMessage(), "Cannot Save Image",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }

                else return;
            }
        }
    }
    
    /**
     * An inner class to handle MouseListener events from the display.
     */
    private class MouseHandler extends MouseAdapter
    {
        /**
         * When the handler receives a mouse-click event, it maps the pixel-
         * coordinates of the click into the area of the fractal that is being
         * displayed, and then calls the generator's recenterAndZoomRange()
         * method with coordinates that were clicked and a 0.5 scale. 
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            /** Get x coordinate of display area of mouse click. **/
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
            range.x + range.width, displaySize, x);
            
            /** Get y coordinate of display area of mouse click. **/
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
            range.y + range.height, displaySize, y);
            
            /**
             * Call the generator's recenterAndZoomRange() method with
             * coordinates that were clicked and a 0.5 scale.
             */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            
            /**
             * Redraw the fractal after the area being displayed has changed.
             */
            drawFractal();
        }
    }
    
    /**
     * A static main() method to launch FractalExplorer.  Initializes a new
     * FractalExplorer instance with a display-size of 600, calls 
     * createAndShowGUI() on the explorer object, and then calls 
     * drawFractal() on the explorer to see the initial view.
     */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}