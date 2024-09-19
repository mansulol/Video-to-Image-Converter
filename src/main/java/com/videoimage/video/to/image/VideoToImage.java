package com.videoimage.video.to.image;

// Librerias comunes
import java.io.File;

// Libreria AWT
import java.awt.Dimension;
import java.awt.Font;

// Libreria Swing
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class VideoToImage extends JFrame{

    // Etiquetas
    JLabel textConversion;
    JLabel textNumeroImagenes;
    JLabel textoInfoVideo;
    JLabel textDuracionVideo;
    JLabel textoNombreVideo;
    JLabel textProcesoVideo;

    // Campos de texto
    JTextField inputVideo;
    JTextField inputImg;
    JTextField numeroImagenes;

    // Botones
    JButton btnVideo;
    JButton btnImg;
    JButton btnConvertir;
    
    // Variables
    int numeroFrames = 1;
    
    // Barra de progreso
    ProgressBar barra;
    
    // Para administrar los eventos del programa
    EventsHandler eventos;
    
    // Ajustes del programa
    Settings ajustes;

    public VideoToImage() {
        barra = new ProgressBar(200, 280);
        eventos = new EventsHandler(this, this.barra);
        ajustes = new Settings();
        
        addComponents();
    }

    private void addComponents() {
        // Personalizar el frame
        setTitle("Video a imagen");
        setSize( ajustes.TAMANIO_FRAME );
        // setIconImage(new ImageIcon(getClass().getResource("src/main/java/com/videoimage/video/to/image/assets/icono.png")).getImage());
        setLocationRelativeTo(null);
        setBackground(ajustes.COLOR_PRIMARIO);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Campo de texto para poner la ruta al video
        inputVideo = new JTextField("   Ruta del video...");
        inputVideo.setBounds(26, 55, ajustes.TAMANIO_INPUT_RUTAS.width, ajustes.TAMANIO_INPUT_RUTAS.height);
        inputVideo.setBackground(ajustes.COLOR_SECUNDARIO);
        inputVideo.setFont(ajustes.FUENTE_RUTAS);
        inputVideo.setBorder(null);
        add(inputVideo);

        btnVideo = new JButton("Examinar");
        btnVideo.setBounds(582, 55, ajustes.TAMANIO_BOTONES.width, ajustes.TAMANIO_BOTONES.height);
        btnVideo.setFont(ajustes.FUENTE_BOTONES);
        btnVideo.setBorder(null);
        btnVideo.setBackground(ajustes.COLOR_SECUNDARIO);
        btnVideo.addMouseListener(eventos);
        add(btnVideo);
        
        // Inputs para la carpeta de las imagenes
        inputImg = new JTextField(" Ruta de la carpeta de las imagenes...");
        inputImg.setBounds(26, 139, ajustes.TAMANIO_INPUT_RUTAS.width, ajustes.TAMANIO_INPUT_RUTAS.height);
        inputImg.setBackground(ajustes.COLOR_SECUNDARIO);
        inputImg.setFont(ajustes.FUENTE_RUTAS);
        inputImg.setBorder(null);
        add(inputImg);

        btnImg = new JButton("Examinar");
        btnImg.setBounds(582, 139, ajustes.TAMANIO_BOTONES.width, ajustes.TAMANIO_BOTONES.height);
        btnImg.setFont( ajustes.FUENTE_BOTONES );
        btnImg.setBackground(ajustes.COLOR_SECUNDARIO);
        btnImg.setBorder(null);
        btnImg.addMouseListener(eventos);
        add(btnImg);
        
        // Nombre del video
        textoNombreVideo = new JLabel("Nombre: N/A");
        textoNombreVideo.setBackground(null);
        textoNombreVideo.setBounds(26, 202, 322, 18);
        textoNombreVideo.setFont(ajustes.FUENTE_INFO);
        add(textoNombreVideo);
        
        // Numero de frames del video seleccionado
        textoInfoVideo = new JLabel("Total: 0 frames,  Tamaño: 0 KB,  Duracion: 0:00:000s");
        textoInfoVideo.setBackground(null);
        textoInfoVideo.setBounds(26, 233, 420, 18);
        textoInfoVideo.setFont(ajustes.FUENTE_INFO);
        add(textoInfoVideo);
        
        // Numero de imagenes
        textNumeroImagenes = new JLabel("Numero de imagenes:");
        textNumeroImagenes.setBackground(null);
        textNumeroImagenes.setBounds(403, 208, 146, 16);
        textNumeroImagenes.setFont( ajustes.FUENTE_INFO );
        add(textNumeroImagenes);

        // Campo de texto para el numero de imagenes
        numeroImagenes = new JTextField("1");
        numeroImagenes.setBackground(ajustes.COLOR_SECUNDARIO);
        numeroImagenes.setBounds(595, 206, ajustes.TAMANIO_NUMERO_IMAGENES.width, ajustes.TAMANIO_NUMERO_IMAGENES.height);
        numeroImagenes.setHorizontalAlignment(JTextField.RIGHT);
        numeroImagenes.setFont(ajustes.FUENTE_NUMERO_IMAGENES );
        numeroImagenes.setBorder(null);
        add(numeroImagenes);
        
        // Boton de convertir
        btnConvertir = new JButton("Convertir");
        btnConvertir.setBounds(230, 300, ajustes.TAMANIO_BTN_CONVERTIR.width, ajustes.TAMANIO_BTN_CONVERTIR.height);
        btnConvertir.setFont(ajustes.FUENTE_BOTONES);
        btnConvertir.setBackground(ajustes.COLOR_SECUNDARIO);
        btnConvertir.setForeground(ajustes.COLOR_DEFAULT_WHITE);
        btnConvertir.setBorder(null);
        btnConvertir.addMouseListener(eventos);
        add(btnConvertir);
        
        // Texto de procesando video
        textProcesoVideo = new JLabel("Procesando video...");
        textProcesoVideo.setBounds(180, 330, ajustes.TAMANIO_BTN_CONVERTIR.width+100, ajustes.TAMANIO_BTN_CONVERTIR.height);
        textProcesoVideo.setFont(ajustes.FUENTE_TEXTO_CONVERSION);
        textProcesoVideo.setHorizontalAlignment(JLabel.CENTER);
        add(textProcesoVideo);
        
        textProcesoVideo.setVisible(false);

        setResizable(false);
        setVisible(true);
    }
}