import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageInputPanel extends JPanel {

    private JTextField urlField;
    private JTextField uriField;
    private JButton urlGetImageButton;
    private JButton openImageButton;
    private JButton processImage;
    private JTextField thresholdField;


    public String getUrl() {
        return urlField.getText();
    }

    public ImageInputPanel() {
        super();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);

        createComponents();

    }

    private void createComponents() {
        JLabel urlLabel = new JLabel("Wprowadź adres URL obrazu: ");
        JLabel openLabel = new JLabel("Kliknij by otworzyć obraz z dysku: ");
        urlField = new JTextField();
        uriField = new JTextField();
        uriField.setEditable(false);
        thresholdField = new JTextField();

        urlGetImageButton = new JButton("Wybierz");
        urlGetImageButton.addActionListener(actionEvent -> {
            //coś tam
            try {
                URL imageUrl = new URL(urlField.getText());
                EventQueue.invokeLater(() ->  new ImagesFrame(imageUrl, Integer.parseInt(thresholdField.getText())));

            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(this, "Wprowadzony adres URL jest niepoprawny", "Błąd", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
        openImageButton = new JButton("Wybierz");
        openImageButton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return true;
                    }

                    String extension = Utils.getExtension(file);
                    if (extension != null) {
                        if (extension.equals(Utils.tiff) ||
                                extension.equals(Utils.tif) ||
                                extension.equals(Utils.gif) ||
                                extension.equals(Utils.jpeg) ||
                                extension.equals(Utils.jpg) ||
                                extension.equals(Utils.png)) {
                            return true;
                        } else {
                            return false;
                        }
                    }

                    return false;
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);
            int response = fileChooser.showDialog(this, "OK");

            if (response == JFileChooser.APPROVE_OPTION) {
                uriField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });


        processImage = new JButton("Przetwarzaj");
        processImage.addActionListener(actionEvent -> {
            if (!uriField.getText().trim().isEmpty()) {
                String path = uriField.getText();
                EventQueue.invokeLater(() ->  new ImagesFrame(path, Integer.parseInt(thresholdField.getText())));            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridLayout gridLayout = new GridLayout(0, 4);
        gridLayout.setHgap(5);
        gridLayout.setVgap(20);
        inputPanel.setLayout(gridLayout);
        inputPanel.add(urlLabel);
        inputPanel.add(urlField);
        inputPanel.add(urlGetImageButton);
        //inputPanel.add(Box.createHorizontalGlue());
        inputPanel.add(thresholdField);
        inputPanel.add(openLabel);
        inputPanel.add(uriField);
        inputPanel.add(openImageButton);
        inputPanel.add(processImage);

        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.add(inputPanel, BorderLayout.CENTER);

        this.add(parentPanel);
    }



}
