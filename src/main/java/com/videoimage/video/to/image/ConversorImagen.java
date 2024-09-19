package com.videoimage.video.to.image;

// Librerias comunes
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;

// Libreria JavaX
import javax.imageio.ImageIO;

// Libreria AWT
import java.awt.image.BufferedImage;

// Libreria JavaCV para interacturar con imagenes y videos
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

// Clase que nos permite convertir un video a images usando la libreria javacv
public class ConversorImagen {
    // Strings para guardar rutas
    String rutaVideo;
    String rutaCarpeta;
    String nombreVideo = "";
    
    // Variables de clase
    static int imgNum = 0;
    static int saltoFrames = 0;

    boolean exito = false;

    // Guardar el numero de imagenes
    int numeroImgsDefinido = 0;
    
    ProgressBar barraDeProgreso;
    VideoToImage interfaz;
    
    // Creamos un objeto frame para guardar la imagen
    Frame frame;
    
    public ConversorImagen(String rutaVideo){
        this.rutaVideo = rutaVideo;
        nombreVideo = getNombre();
    }

    // Constructor principal, este constructor llama a metodo videoToImage para iniciar el proceso
    public ConversorImagen(String rutaVideo, String directorio, int numeroImgs, ProgressBar b, VideoToImage interfaz) {
        this.rutaVideo = rutaVideo;
        this.rutaCarpeta = directorio;
        this.numeroImgsDefinido = numeroImgs;
        
        nombreVideo = getNombre();
        this.interfaz = interfaz;
        
        this.barraDeProgreso = b;
        
        try {
            videoToImage();
        } catch (Exception e) {
            System.out.println("Error al convertir a imagen en conversorImagen, " +e);
        }
        
    }
    
    public String getNombre(){
        String nombreExtension = new File(rutaVideo).getName();
        return nombreExtension.substring(0, nombreExtension.lastIndexOf('.'));
    }
    
    public String getDuracion(){
        // Este objeto nos permite convertir los frames del video a BufferedImage
        Java2DFrameConverter conversorVideo = new Java2DFrameConverter();
        try{
            // Este objeto me permite obtener los frames del video
            FFmpegFrameGrabber obtenedorFrames = new FFmpegFrameGrabber(rutaVideo);
            obtenedorFrames.start(); // Empezamos a obtener los frames con el metodo start()
            
            // Convertir microsegundos(u) a un formato mas legible
            long duracionMicrosegundos = obtenedorFrames.getLengthInTime();

            // Convertir la duración a milisegundos
            long duracionMilisegundos = duracionMicrosegundos / 1000;

            // Extraer los milisegundos restantes
            long milisegundos = duracionMilisegundos % 1000;

            // Convertir la duración total a segundos
            long duracionSegundos = duracionMilisegundos / 1000;

            // Extraer los segundos restantes
            long segundos = duracionSegundos % 60;

            // Convertir la duración total a minutos
            long duracionMinutos = duracionSegundos / 60;

            // Extraer los minutos restantes
            long minutos = duracionMinutos % 60;
            
            String milisegundosString = String.valueOf(milisegundos);
            String segundosString = String.valueOf(segundos);
            String minutosString = String.valueOf(minutos);
            
            if( milisegundos < 10 ){
                milisegundosString = "0"+milisegundos;
            }
            
            if( segundos < 10 ){
                segundosString = "0"+segundos;
            }
            
            if( minutos < 10 ){
                minutosString = "0"+minutos;
            }
            
            // Formatear como "HH:mm"
            return String.format("%s:%s:%s", minutosString, segundosString, milisegundosString);
        }
        catch(Exception e){
            System.out.println("Error al obtener duracion: "+e);
            
            return "-1";
        }
    }
    
    public double getTamanio(){
        File video = new File(rutaVideo);
        
        int tamanioBytes = (int) video.length();
        double tamanioMB = tamanioBytes / 1024;
        
        return tamanioMB/1000;
    }
    
    public int getFrames(){
        // Este objeto nos permite convertir los frames del video a BufferedImage
        Java2DFrameConverter conversorVideo = new Java2DFrameConverter();
        try{
            // Este objeto me permite obtener los frames del video
            FFmpegFrameGrabber obtenedorFrames = new FFmpegFrameGrabber(rutaVideo);
            obtenedorFrames.start(); // Empezamos a obtener los frames con el metodo start()
            
            return obtenedorFrames.getLengthInFrames();
        }
        catch(Exception e){
            System.out.println("Error al obtener frames");
            return -1;
        }
    }

    private void videoToImage() throws Exception {
        // Este objeto nos permite convertir los frames del video a BufferedImage
        Java2DFrameConverter conversorVideo = new Java2DFrameConverter();

        // Este objeto me permite obtener los frames del video
        FFmpegFrameGrabber obtenedorFrames = new FFmpegFrameGrabber(rutaVideo);
        obtenedorFrames.start(); // Empezamos a obtener los frames con el metodo start()

        // Obtenemos los fps y los guardamos en un double
        double fps = obtenedorFrames.getFrameRate();
        
        // Creo una variable para guardar los frames que debo saltar
        saltoFrames = Math.round(obtenedorFrames.getLengthInFrames()/numeroImgsDefinido);
        
        // Creacion de carpeta para guardar las imagenes
        File carpetaFrames = new File(rutaCarpeta + File.separator + nombreVideo);
        
        barraDeProgreso.barraTotal = obtenedorFrames.getLengthInFrames();
        interfaz.add(barraDeProgreso);
        
        if(carpetaFrames.mkdir()){
            System.out.println("Carpeta "+nombreVideo+" creada");
        }else{
            System.out.println("No se pudo crear carpeta "+nombreVideo);
        }
        
        // Recorro los frames del video
        for (int i = 1; i <= numeroImgsDefinido; i++) {
            imgNum = i;
            System.out.println("Imagen numero "+imgNum+", Frame numero: "+saltoFrames*imgNum);

            if(saltoFrames > obtenedorFrames.getLengthInFrames()){
                saltoFrames = obtenedorFrames.getLengthInFrames();
            }

            // Establezco el frame que quiero obtener con mi objeto frameGrabber
            obtenedorFrames.setFrameNumber(saltoFrames*imgNum);

            // Guardo en mi variable frame el frame establecido anteriormente
            frame = obtenedorFrames.grabImage();

            Timer timer = new Timer();

            TimerTask tarea = new TimerTask(){
                @Override
                public void run(){
                    try {
                        // Convierto mi frame a bufferedImage usando mi objeto Java2DFrameConverter
                        BufferedImage buffer = conversorVideo.convert(frame);

                        String ruta = carpetaFrames.getPath() + File.separator + "frame_" + imgNum + ".jpg";
                        ImageIO.write(buffer, "jpg", new File(ruta));

                        barraDeProgreso.actualizarBarra( saltoFrames*imgNum );
                    } 
                    catch( IllegalArgumentException il ){
                        System.out.println("Error imagen numero "+imgNum+", frame "+saltoFrames*imgNum+" nulo: "+il);
                    }
                    catch (IOException e) {
                        System.out.println("Error al convertir a bufferedImage: "+e);
                    }
                }
            };

            timer.schedule(tarea, (int) 10/obtenedorFrames.getLengthInFrames());
        }

        obtenedorFrames.close();
        exito = true;
    }
}
