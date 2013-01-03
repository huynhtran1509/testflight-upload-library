package com.willowtreeapps.uploader.hockeyapp;

import com.willowtreeapps.uploader.AbstractUploader;
import com.willowtreeapps.request.UploadRequest;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jbeck
 * Date: 1/2/13
 * Time: 6:34 PM
 */
public class HockeyAppUploader extends AbstractUploader
{
    private static final String HOST = "rink.hockeyapp.net";

    private static final String POST = "/api/2/apps";

    public Map prepare(UploadRequest ur) throws IOException, org.json.simple.parser.ParseException
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpHost targetHost = new HttpHost(HOST);
        HttpPost httpPost = new HttpPost(POST);
        FileBody fileBody = new FileBody(ur.file);

        MultipartEntity entity = new MultipartEntity();
        entity.addPart("notes", new StringBody(ur.buildNotes));
        entity.addPart("ipa", fileBody);

        if (ur.dsymFile != null)
        {
            FileBody dsymFileBody = new FileBody(ur.dsymFile);
            entity.addPart("dsym", dsymFileBody);
        }

        entity.addPart("notify", new StringBody(ur.notifyTeam ? "1" : "0"));
        httpPost.setEntity(entity);

        httpPost.setHeader("X-HockeyAppToken", ur.apiToken);

        return this.upload(ur, httpClient, targetHost, httpPost);
    }
}
