package com.willowtreeapps.request;

import java.io.File;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jbeck
 * Date: 1/2/13
 * Time: 7:48 PM
 */
public class UploadRequest implements Serializable
{
    public String apiToken;
    public String teamToken;
    public Boolean notifyTeam;
    public String buildNotes;
    public File file;
    public File dsymFile;
    public String lists;
    public Boolean replace;
    public Boolean status;
    public Boolean privateDownload;
}