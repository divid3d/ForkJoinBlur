import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class ImageInputPanel extends JPanel {

    private JTextField urlField;
    private JTextField uriField;
    private JButton openImageButton;
    private JButton processImageButton;
    private JNumberTextField thresholdField;
    private JNumberTextField blurWidthField;
    private JCheckBox enableUrl;
    private JCheckBox enableFile;

    private static final String DEFAULT_THRESHOLD = "10000";
    private static final String DEFAULT_BLUR_RADIUS = "13";


    ImageInputPanel() {
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
        JLabel openLabel = new JLabel("Wybierz obraz z dysku: ");
        openLabel.setEnabled(false);

        enableUrl = new JCheckBox();
        enableUrl.setSelected(true);
        enableFile = new JCheckBox();
        enableFile.setSelected(false);

        enableUrl.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                urlLabel.setEnabled(true);
                urlField.setEnabled(true);
                enableFile.setSelected(false);

            } else {
                urlLabel.setEnabled(false);
                urlField.setEnabled(false);
            }
        });


        enableFile = new JCheckBox();
        enableFile.setSelected(false);
        enableFile.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                openLabel.setEnabled(true);
                uriField.setEnabled(true);
                openImageButton.setEnabled(true);
                enableUrl.setSelected(false);
            } else {
                openLabel.setEnabled(false);
                uriField.setEnabled(false);
                openImageButton.setEnabled(false);
            }
        });


        urlField = new JTextField();
        urlField.setPreferredSize(new Dimension(300, 20));
        uriField = new JTextField();
        uriField.setEditable(false);
        thresholdField = new JNumberTextField();
        thresholdField.setText(DEFAULT_THRESHOLD);
        thresholdField.setSize(new Dimension(100, 20));
        blurWidthField = new JNumberTextField();
        blurWidthField.setText(DEFAULT_BLUR_RADIUS);
        blurWidthField.setSize(new Dimension(100, 20));

        processImageButton = new JButton("Przetwórz obraz");
        processImageButton.addActionListener(actionEvent -> {
            if (enableUrl.isSelected()) {
                if (!urlField.getText().trim().isEmpty()) {
                    if (!thresholdField.getText().trim().isEmpty() && !blurWidthField.getText().trim().isEmpty()) {
                        try {
                            URL imageUrl = new URL(urlField.getText());
                            EventQueue.invokeLater(() -> new ImagesFrame(imageUrl, Integer.parseInt(thresholdField.getText()), Integer.parseInt(blurWidthField.getText())));

                        } catch (MalformedURLException e) {
                            JOptionPane.showMessageDialog(this, "Wprowadzony adres URL jest niepoprawny", "Błąd", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nie wprowadzono adresu URL", "Wprowadz adres URL obrazu", JOptionPane.INFORMATION_MESSAGE);

                }
            } else if (enableFile.isSelected()) {
                if (!uriField.getText().trim().isEmpty()) {
                    if (!thresholdField.getText().trim().isEmpty() && !blurWidthField.getText().trim().isEmpty()) {
                        String path = uriField.getText();
                        EventQueue.invokeLater(() -> new ImagesFrame(path, Integer.parseInt(thresholdField.getText()), Integer.parseInt(blurWidthField.getText())));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Nie wybrano obrazu", "Wybierz obraz z dysku", JOptionPane.INFORMATION_MESSAGE);

                }
            } else {
                JOptionPane.showMessageDialog(this, "Żródło obrazu nie zostało wybrane", "Żródło obrazu", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        openImageButton = new JButton("Wybierz obraz");
        openImageButton.setEnabled(false);
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
                        return extension.equals(Utils.ImageExtensions.tiff.toString()) ||
                                extension.equals(Utils.ImageExtensions.tif.toString()) ||
                                extension.equals(Utils.ImageExtensions.gif.toString()) ||
                                extension.equals(Utils.ImageExtensions.jpeg.toString()) ||
                                extension.equals(Utils.ImageExtensions.jpg.toString()) ||
                                extension.equals(Utils.ImageExtensions.png.toString());
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


        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagLayout gridBagLayout = new GridBagLayout();
        inputPanel.setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("<html><span style='font-size:20px'>" + "Źródło obrazu" + "</span></html>"), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(enableUrl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(urlLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(urlField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(enableFile, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(openLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        inputPanel.add(uriField, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        inputPanel.add(openImageButton, gbc);

        gbc.insets = new Insets(40, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("<html><span style='font-size:20px'>" + "Parametry przetwarzania" + "</span></html>"), gbc);
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Blur width: "), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(blurWidthField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Fork threshold: "), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 5;
        inputPanel.add(thresholdField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 6;
        inputPanel.add(processImageButton, gbc);

        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.add(inputPanel, BorderLayout.CENTER);

        this.add(parentPanel);
    }


}
