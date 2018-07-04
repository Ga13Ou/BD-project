package com.projet.demo.Models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AudiPFE {

    private String id;
    private String type;
    private String annee;
    private String etablissement;
    private String nom;
    private String prenom;
    private String intituleSujet;
    private String domainePrincipalDuSujet;
    private String objectifDuProjet;
    private String contexteEtProblematique;
    private String retombeesAttendues;
    private String technologie;
    private String encadreurUniversitaire;
    private String encadreurEntreprise;
    private int note;
    //document metadata
    private String documentName;
    private String documentPath;
    private String documentContent;   //encoded in base64
    private String documentType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIntituleSujet() {
        return intituleSujet;
    }

    public void setIntituleSujet(String intituleSujet) {
        this.intituleSujet = intituleSujet;
    }

    public String getDomainePrincipalDuSujet() {
        return domainePrincipalDuSujet;
    }

    public void setDomainePrincipalDuSujet(String domainePrincipalDuSujet) {
        this.domainePrincipalDuSujet = domainePrincipalDuSujet;
    }

    public String getObjectifDuProjet() {
        return objectifDuProjet;
    }

    public void setObjectifDuProjet(String objectifDuProjet) {
        this.objectifDuProjet = objectifDuProjet;
    }

    public String getContexteEtProblematique() {
        return contexteEtProblematique;
    }

    public void setContexteEtProblematique(String contexteEtProblematique) {
        this.contexteEtProblematique = contexteEtProblematique;
    }

    public String getRetombeesAttendues() {
        return retombeesAttendues;
    }

    public void setRetombeesAttendues(String retombeesAttendues) {
        this.retombeesAttendues = retombeesAttendues;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public String getEncadreurUniversitaire() {
        return encadreurUniversitaire;
    }

    public void setEncadreurUniversitaire(String encadreurUniversitaire) {
        this.encadreurUniversitaire = encadreurUniversitaire;
    }

    public String getEncadreurEntreprise() {
        return encadreurEntreprise;
    }

    public void setEncadreurEntreprise(String encadreurEntreprise) {
        this.encadreurEntreprise = encadreurEntreprise;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Override
    public String toString() {
        return "AudiPFE{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", annee='" + annee + '\'' +
                ", etablissement='" + etablissement + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", intituleSujet='" + intituleSujet + '\'' +
                ", domainePrincipalDuSujet='" + domainePrincipalDuSujet + '\'' +
                ", objectifDuProjet='" + objectifDuProjet + '\'' +
                ", contexteEtProblematique='" + contexteEtProblematique + '\'' +
                ", retombeesAttendues='" + retombeesAttendues + '\'' +
                ", technologie='" + technologie + '\'' +
                ", encadreurUniversitaire='" + encadreurUniversitaire + '\'' +
                ", encadreurEntreprise='" + encadreurEntreprise + '\'' +
                ", note=" + note +
                ", documentName='" + documentName + '\'' +
                ", documentPath='" + documentPath + '\'' +
                ", documentContent='" + documentContent + '\'' +
                ", documentType='" + documentType + '\'' +
                '}';
    }
}
