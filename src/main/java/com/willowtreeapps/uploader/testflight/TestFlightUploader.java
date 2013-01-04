package com.willowtreeapps.uploader.testflight;

/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * Created by IntelliJ IDEA.
 * User: jbeck
 * Date: 10/11/12
 * Time: 4:03 PM
 */
public class TestFlightUploader extends AbstractUploader
{
    private static final String HOST = "testflightapp.com";

    private static final String POST = "/api/builds.json";

    public Map upload(UploadRequest ur) throws IOException, org.json.simple.parser.ParseException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpHost targetHost = new HttpHost(HOST);
        HttpPost httpPost = new HttpPost(POST);
        FileBody fileBody = new FileBody(ur.file);

        MultipartEntity entity = new MultipartEntity();
        entity.addPart("api_token", new StringBody(ur.apiToken));
        entity.addPart("team_token", new StringBody(ur.teamToken));
        entity.addPart("notes", new StringBody(ur.buildNotes));
        entity.addPart("file", fileBody);

        if (ur.dsymFile != null)
        {
            FileBody dsymFileBody = new FileBody(ur.dsymFile);
            entity.addPart("dsym", dsymFileBody);
        }

        if (ur.lists.length() > 0)
        {
            entity.addPart("distribution_lists", new StringBody(ur.lists));
        }

        entity.addPart("notify", new StringBody(ur.notifyTeam ? "True" : "False"));
        entity.addPart("replace", new StringBody(ur.replace ? "True" : "False"));
        httpPost.setEntity(entity);

        return this.send(ur, httpClient, targetHost, httpPost);
    }
}
