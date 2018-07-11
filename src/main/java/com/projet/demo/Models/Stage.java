package com.projet.demo.Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Stage {
    @JsonProperty("id")
    private String _id;
    private String type;
    private String annee;
    private String etablissement;
    private Person candidat;
    private String intituleSujet;
    private String domainePrincipal;
    private String objectifProjet;
    private String contexteProblematique;
    private String retombeesAttendues;
    private String technologie;
    private String encadreurUniversitaire;
    private String encadreurEntreprise;
    private int note;
    //document metadata
    private String path;
    private String file;   //encoded in base64
    private Date uploadDate;
    private User uploadedBy;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Person getCandidat() {
        return candidat;
    }

    public void setCandidat(Person candidat) {
        this.candidat = candidat;
    }

    public String getIntituleSujet() {
        return intituleSujet;
    }

    public void setIntituleSujet(String intituleSujet) {
        this.intituleSujet = intituleSujet;
    }

    public String getDomainePrincipal() {
        return domainePrincipal;
    }

    public void setDomainePrincipal(String domainePrincipal) {
        this.domainePrincipal = domainePrincipal;
    }

    public String getObjectifProjet() {
        return objectifProjet;
    }

    public void setObjectifProjet(String objectifProjet) {
        this.objectifProjet = objectifProjet;
    }

    public String getContexteProblematique() {
        return contexteProblematique;
    }

    public void setContexteProblematique(String contexteProblematique) {
        this.contexteProblematique = contexteProblematique;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
