package com.videoimage.video.to.image;

// Libreria para los eventos
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// Librerias comunes
import java.io.File;
import java.lang.Exception;

// Libreria Swing
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EventsHandler implements MouseListener {
    VideoToImage programa;
    ConversorImagen conversor;
    ProgressBar barra;
    
    // Variable para guardar los frames totales
    int numeroFrames = 0;

    public EventsHandler(VideoToImage video_Img, ProgressBar barra){
        this.programa = video_Img;
        this.barra = barra;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if( e.getSource() instanceof JButton ){
            System.out.println("Has pulsado un boton "+e.getSource());
            JButton btn = (JButton) e.getSource();
            
            if( btn == programa.btnImg ){
                System.out.println("Boton de imagen");

                JFileChooser elegirCarpetaImg = new JFileChooser();
                elegirCarpetaImg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                // Extension para solo admitir directorios
                FileNameExtensionFilter filtroDirectorios = new FileNameExtensionFilter("Carpetas", "/");
                elegirCarpetaImg.setFileFilter(filtroDirectorios);

                elegirCarpetaImg.setCurrentDirectory(new File("src/main/java/com/videoimage/video/to/image/images/"));

                try {
                    if (JFileChooser.APPROVE_OPTION == elegirCarpetaImg.showOpenDialog(null)) {
                        File archivo = elegirCarpetaImg.getCurrentDirectory();
                        programa.inputImg.setText(archivo.getPath());
                    }

                } catch (Exception ei) {
                    System.out.println("Error al elegir carpeta de las imagenes: "+ ei);
                }
                
            }
            if( btn == programa.btnVideo ){
                System.out.println("Boton de video");

                JFileChooser elegirVideo = new JFileChooser();
                elegirVideo.setFileSelectionMode(JFileChooser.FILES_ONLY);
                // Extension para admitir videos
                FileNameExtensionFilter filtroVideo = new FileNameExtensionFilter("Videos", "mp4", ".mov");
                elegirVideo.setFileFilter(filtroVideo);

                elegirVideo.setCurrentDirectory(new File("src/main/java/com/videoimage/video/to/image/video/"));

                try {
                    
                    if (JFileChooser.APPROVE_OPTION == elegirVideo.showOpenDialog(null)) {
                        File archivo = elegirVideo.getSelectedFile();
                        programa.inputVideo.setText(archivo.getPath());
                    }
                    
                    Thread hiloAgregarVideo = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            programa.textProcesoVideo.setVisible(true);
                            
                            ConversorImagen cv = new ConversorImagen(programa.inputVideo.getText());
                            
                            numeroFrames = cv.getFrames();
                            String nombre = cv.getNombre();
                            String duracionVideo = cv.getDuracion();
                            double tamanio = cv.getTamanio();
                            
                            programa.textoNombreVideo.setText(String.format("Nombre: %s", nombre));
                            programa.textoInfoVideo.setText(String.format("Total: %d frames, Tamaño: %2.2fMB, Duracion: %ss", numeroFrames, tamanio, duracionVideo));

                            // barra.total = numeroFrames;
                            programa.textProcesoVideo.setVisible(false);
                        }
                    });

                    hiloAgregarVideo.start();
                }
                catch (Exception ev) {
                    System.out.println("Error al elegir archivo: " + ev);
                }
                
            }
            if( btn == programa.btnConvertir ){
                System.out.println("Boton de convertir");
                    Thread hiloConversion = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            // add(barra);
                            programa.textProcesoVideo.setText("");
                            
                            if (new File(programa.inputVideo.getText()).isFile() && new File(programa.inputImg.getText()).isDirectory()) {
                                
                                String rutaVideoString = programa.inputVideo.getText().substring(programa.inputVideo.getText().indexOf("src"));
                                String rutaCarpetaString = programa.inputImg.getText().substring(programa.inputImg.getText().lastIndexOf("src"));
                                int numeroImgs = 1;
                                
                                System.out.println("Ruta del video string: "+rutaVideoString);
                                System.out.println("Ruta de la carpeta string: "+rutaCarpetaString);
                                
                                numeroImgs = Integer.parseInt(programa.numeroImagenes.getText().replace(" ", ""));
                                
                                programa.btnConvertir.setVisible(false);
                                
                                // Clase para convertir el video y mostrar la barra de progreso
                                ConversorImagen conversor = new ConversorImagen(rutaVideoString, rutaCarpetaString, numeroImgs, barra, programa);
                                
                                programa.textProcesoVideo.setVisible(true);
                                
                                if (conversor.exito) {
                                    programa.textProcesoVideo.setText("Video convertido exitosamente!");
                                } else {
                                    programa.textProcesoVideo.setText("Conversion de video fallido");
                                }
                                
                            } else {
                                System.out.println("Ruta al video: "+programa.inputVideo.getText());
                                System.out.println("Ruta a la carpeta: "+programa.inputImg.getText());
                                
                                programa.textProcesoVideo.setVisible(true);
                                programa.textProcesoVideo.setText("Rutas invalidas");
                            }
                        }
                    });
                    hiloConversion.start();
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){  }
    
    @Override
    public void mouseReleased(MouseEvent e){  }
    
    @Override
    public void mouseEntered(MouseEvent e){  }
    
    @Override
    public void mouseExited(MouseEvent e){  }
}
