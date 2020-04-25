import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

// наследования от класса JComponent
public class JImageDisplay extends JComponent {

    private BufferedImage bufferedImage;

    // конструктор
    public JImageDisplay(int width, int height) {
        // инициализация нового изображения
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // вызываем метод, благодаря которому компанент
        // будет включен в пользовательский интерфейс
        super.setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        // вызываем метод суперкласса
        super.paintComponent(g);
        // рисуем изображение в компоненте
        g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    // метод, устанавливающий все пиксели в черный цвет
    public void clearImage() {
        int imageArea = bufferedImage.getWidth()*bufferedImage.getHeight();
        bufferedImage.setRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), new int[imageArea], 0, 0);
    }

    // метод, устанавливающий пиксели в определенный цвет
    public void drawPixel(int x, int y, int rgbColor) {
        bufferedImage.setRGB(x, y, rgbColor);
    }
}