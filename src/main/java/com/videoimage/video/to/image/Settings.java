package com.videoimage.video.to.image;

// Librerias
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class Settings {
    // Fuentes
       public static final Font FUENTE_RUTAS = new Font("Arial", Font.PLAIN, 15);
       public static final Font FUENTE_INFO = new Font("Arial", Font.PLAIN, 14);
       public static final Font FUENTE_BOTONES = new Font("Inria Sans", Font.BOLD, 12);
       public static final Font FUENTE_TEXTO_CONVERSION = new Font("Inter", Font.BOLD, 16);
       public static final Font FUENTE_BTN_CONVERTIR = new Font("Inter", Font.BOLD, 14);
       public static final Font FUENTE_NUMERO_IMAGENES = new Font("Consolas", Font.PLAIN, 15);
       
    // Colores
       public static final Color COLOR_DEFAULT_WHITE = Color.decode("#FFFFFF");
       public static final Color COLOR_DEFAULT_BLACK = Color.decode("#000000");
       public static final Color COLOR_PRIMARIO = Color.decode("#AEF9FE");
       public static final Color COLOR_SECUNDARIO = Color.decode("#86A0A2");
       
    // Tamaños
       public static final Dimension TAMANIO_FRAME = new Dimension(700, 400);
       public static final Dimension TAMANIO_INPUT_RUTAS = new Dimension(530, 28);
       public static final Dimension TAMANIO_BOTONES = new Dimension(92, 28);
       public static final Dimension TAMANIO_NUMERO_IMAGENES = new Dimension(72, 22);
       public static final Dimension TAMANIO_BTN_CONVERTIR = new Dimension(236, 28);
       public static final Dimension TAMANIO_TEXTO_CONVERSION = new Dimension(300, 20);
       
    // Tamaños enteros
       public static final int ANCHO_PROGRESO = 360;
       public static final int ANCHO_PROGRESO_BARRA = 300;
       public static final int ALTO_PROGRESO_BARRA = 26;
       
    // Bordes
       public static final MatteBorder BORDE_RUTAS = new MatteBorder(10, 5, 10, 5, COLOR_SECUNDARIO);
       
       public Settings(){
           
       }
}

