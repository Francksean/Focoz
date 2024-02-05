import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.List;
import java.util.ArrayList;

public class DeZipFokoz {
    static List<String> liste;
    public static void dezip(File chemin_dezip, File fichier_zip) throws IOException{
        File dossier = new File(String.valueOf(chemin_dezip));
        if(!dossier.exists()){
            dossier.mkdir();
        }

        FileInputStream fis =  new FileInputStream(fichier_zip);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry;

        while ((entry = zis.getNextEntry())!= null){
            String name = entry.getName();
            String filePath = chemin_dezip + File.separator + name;

            if(!entry.isDirectory()){
                FileOutputStream fos =  new FileOutputStream(filePath);
                byte[] buffer = new byte[1024];
                int data;
                        while ((data = zis.read(buffer)) != -1)
                            fos.write(buffer, 0, data);
                        fos.close();
            }else {
                File dir =  new File(filePath);
                dir.mkdir();
            }
            zis.close();
        }
    }

    public static void deZip_multiple(File dossier_src, File dossier_dst) throws IOException {
        File dossier = new File(String.valueOf(dossier_dst));
        if (!dossier.exists()) {
            dossier.mkdir();
        }
        FileInputStream fis = new FileInputStream(dossier_src);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry;
        liste = new ArrayList<>();
        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            String filePath = dossier_dst + File.separator + name;
            liste.add(filePath);


            for (String file : liste) {
                if (!entry.isDirectory()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int data;
                    while ((data = zis.read(buffer)) != -1)
                        fos.write(buffer, 0, data);
                    fos.close();
                } else {
                    for (String file1 : liste) {
                        File dir = new File(file1);
                        dir.mkdir();
                    }
                    zis.close();
                }
            }
        }


    }

    public static void main(String[] args) {
        File fichier_zip = new File("D:\\monFichier.zip");
        File dossier = new File("D:\\Nouveau dossier");

        try {
            deZip_multiple(fichier_zip, dossier);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Décompression terminée");
    }
}
