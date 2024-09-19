package com.videoimage.video.to.image;

// Libreria Swing
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

// Libreria AWT
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ProgressBar extends JPanel {
    // Ajustes Predeterminados
    Settings ajustes = new Settings();
    
    // Variables
    private int ancho = ajustes.ANCHO_PROGRESO;
    private int alto = 60;
    int porcentajeBarra = 0;
    int porcentajeProgreso = 0;
    String progresoFrame = "frame_0.jpg";
    String progresoItems = "0/0";
    
    public int barraTotal = 0;
    
    // Etiquetas
    JLabel numero;
    JLabel barraPane;
    JLabel infoProgreso;
    
    public ProgressBar(int x, int y){
        setSize(ancho , alto);
        setLocation(x, y);
        setLayout(null);
        
        infoProgreso = new JLabel(String.format("Progreso Total: %s, %s ", progresoItems, progresoFrame));
        infoProgreso.setBounds(0, 0, ancho, 18);
        infoProgreso.setFont(ajustes.FUENTE_INFO);
        add(infoProgreso);
        
        barraPane = new JLabel();
        barraPane.setBounds(0, 20, ajustes.ANCHO_PROGRESO_BARRA, ajustes.ALTO_PROGRESO_BARRA);
        barraPane.setBorder(BorderFactory.createLineBorder(ajustes.COLOR_DEFAULT_BLACK, 2));
        add(barraPane);
        
        numero = new JLabel("0%");
        numero.setBounds(ajustes.ANCHO_PROGRESO_BARRA +5, 20, 40, ajustes.ALTO_PROGRESO_BARRA);
        numero.setFont(new Font("Arial", Font.BOLD, 18));
        numero.setForeground(Color.BLACK);
        numero.setBackground(null);
        add(numero);
    }
    
    public void actualizarBarra(int valor){
        setPorcentaje(valor, barraTotal);
        numero.setText(String.valueOf(porcentajeProgreso)+"%");
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        // Limpio el componente antes de pintar con esta linea
        // super.paintComponent(g);
        
        barraPane.paintComponents(g);
        
        g.setColor(Color.green);
        g.fillRect(0, 0, porcentajeBarra, 30);
        barraPane.paint(g);
    }
    
    public void setPorcentaje(int valor, int total){
        this.porcentajeProgreso = porcentaje(valor, total);
        this.porcentajeBarra = porcentajeBarra(porcentajeProgreso);
        repaint();
    }
    
   private int porcentaje(int valorActual, int valorTotal ){
       if( valorActual > valorTotal ){
           System.out.println("El valor actual es mas grande que el total");
           return 100;
       }else{
           return Math.round((valorActual * 100) / valorTotal);
       }
   }
   
   private int porcentajeBarra(int valor){
       if( valor >= 0 && valor <= 100 ){
           return Math.round( (valor * barraTotal ) /100);
       }else{
           System.out.println("Valor invalido para barra");
           return -1;
       }
   }
}
