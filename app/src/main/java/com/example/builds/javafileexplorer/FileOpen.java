package com.example.builds.javafileexplorer;

import java.io.File;
import android.content.Context;
import android.net.Uri;
import android.content.Intent;
import android.widget.Toast;
import android.support.v4.content.FileProvider;

public class FileOpen {
    public static void openFile(Context context, File url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //Uri uri = Uri.fromFile(url); // for Marshmallow and earlier API versions

            Uri uri = FileProvider.getUriForFile(context.getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider", url); // using the file provider for Nougat and higher APIs (build version > 23)

            if (uri.toString().contains(".doc") || uri.toString().contains(".docx")) {
                i.setDataAndType(uri, "application/msword"); // Word document
            } else if (uri.toString().contains(".pdf")) {
                i.setDataAndType(uri, "application/pdf"); // PDF file
            } else if (uri.toString().contains(".ppt") || uri.toString().contains(".pptx")) {
                i.setDataAndType(uri, "application/vnd.ms-powerpoint"); // Powerpoint file
            } else if (uri.toString().contains(".xls") || uri.toString().contains(".xlsx")) {
                i.setDataAndType(uri, "application/vnd.ms-excel"); // Excel file
            } else if (uri.toString().contains(".zip") || uri.toString().contains(".7z") || uri.toString().contains(".rar")) {
                i.setDataAndType(uri, "application/zip"); // Compressed file
            } else if (uri.toString().contains(".gz") || uri.toString().contains(".rz") || uri.toString().contains(".tz") ||
                    uri.toString().contains(".bz2")) {
                i.setDataAndType(uri, "application/x-compressed"); // Compressed file
            } else if (uri.toString().contains(".rtf")) {
                i.setDataAndType(uri, "application/rtf"); // RTF file
            } else if (uri.toString().contains(".json")) {
                i.setDataAndType(uri, "application/json"); // JSON file
            } else if (uri.toString().contains(".gif")) {
                i.setDataAndType(uri, "image/gif"); // GIF file
            } else if (uri.toString().contains(".tif")) {
                i.setDataAndType(uri, "image/tiff"); // TIFF file
            } else if (uri.toString().contains(".jpg") || uri.toString().contains(".jpeg") || uri.toString().contains(".jfif") ||
                    uri.toString().contains(".bmp") || uri.toString().contains(".png")) {
                i.setDataAndType(uri, "image/jpeg"); // JPG/PNG file
            } else if (uri.toString().contains(".css")) {
                i.setDataAndType(uri, "text/css"); // CSS file
            } else if (uri.toString().contains(".htm")) {
                i.setDataAndType(uri, "text/html"); // HTML file
            } else if (uri.toString().contains(".txt")) {
                i.setDataAndType(uri, "text/plain"); // Text file
            } else if (uri.toString().contains(".xml")) {
                i.setDataAndType(uri, "text/xml"); // XML file
            } else if (uri.toString().contains(".mp3") || uri.toString().contains(".wma")) {
                i.setDataAndType(uri, "audio/*"); // Audio files
            } else if (uri.toString().contains(".3gp") || uri.toString().contains(".mpg") || uri.toString().contains(".mpeg") ||
                    uri.toString().contains(".wav") || uri.toString().contains(".wmv") || uri.toString().contains(".flv") ||
                    uri.toString().contains(".mov") || uri.toString().contains(".ogg") || uri.toString().contains(".qt") ||
                    uri.toString().contains(".mpe") || uri.toString().contains(".mp4") || uri.toString().contains(".avi")) {
                i.setDataAndType(uri, "video/*"); // Video files
            } else { // Currently overridden by the catch code
                Toast.makeText(context.getApplicationContext(), "No default application is defined for this file type",
                        Toast.LENGTH_SHORT).show();  // other file extensions
                i.setDataAndType(uri, "*/*"); // XML file
            }
            context.startActivity(i);
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "This file type is not supported",
                    Toast.LENGTH_SHORT).show();  // other file extensions
        }
    }
}
