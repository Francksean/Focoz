import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFokoz2 {
    static int BUFFER = 2048;
    static List<String> liste;
    public static void zip_unique(File fichier_src, File fichier_zip) throws IOException {
        byte[] buffer = new byte[BUFFER];
        FileOutputStream fileos = new FileOutputStream(fichier_zip);
        ZipOutputStream zipos = new ZipOutputStream(fileos);
        ZipEntry zipEntry = new ZipEntry(String.valueOf(fichier_src));
        zipos.putNextEntry(zipEntry);
        FileInputStream fis = new FileInputStream(String.valueOf(fichier_src));

        int data;
        while ((data = fis.read(buffer)) > 0)
            zipos.write(buffer, 0, data);

        fis.close();
        zipos.closeEntry();
        zipos.close();

    }
    public static void zip_multiple(File chemin_src, File compresser) throws IOException{
        File node = new File(String.valueOf(chemin_src));
        liste = new ArrayList<>();
        if(node.isFile()){
            String file = node.getAbsolutePath().toString();
            String chemin = file.substring((int) (chemin_src.length() + 1), file.length());
            liste.add(chemin);
        }
        if(node.isDirectory()){
            String[] sousFichier = node.list();
            for(String filename : sousFichier){
                new File(node, filename);
            }
        }

        byte[] buffer = new byte[BUFFER];
        FileOutputStream fileos = new FileOutputStream(compresser);
        ZipOutputStream zipos = new ZipOutputStream(fileos);
        zipos.setLevel(9);
        for (String file : liste){
            ZipEntry zipEntry = new ZipEntry(file);
            zipos.putNextEntry(zipEntry);
            FileInputStream fis = new FileInputStream(chemin_src + File.separator + file);
            int data;
            while ((data = fis.read(buffer))>0)
                zipos.write(buffer, 0, data);
            fis.close();
        }
        zipos.closeEntry();
        zipos.close();
    }

    public static void main(String[] args){
        File dir = new File("D:\\test");
        File compresser =  new File("D:\\test\\compresser.zip");
        try{
            zip_multiple(dir, compresser);
        }catch (IOException e){
            System.out.println(e);
        }
        System.out.println("Compression réussie");
    }
}