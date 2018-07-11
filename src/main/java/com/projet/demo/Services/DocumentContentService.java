package com.projet.demo.Services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentContentService {
    public String getDocumentContent(byte[] file){
        String content=null;
        try {
            PDFTextStripper test=new PDFTextStripper();
            content=test.getText(PDDocument.load(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
