package me.ibore.http.download;

import me.ibore.http.Response;
import me.ibore.http.connect.http.Call;

import java.io.IOException;

public class UrlWorker extends BasicWorker<UrlDownload> {

    UrlWorker(UrlDownload download) {
        super(download);
    }

    @Override
    protected Response requestNetwork(UrlDownload download) throws IOException {
        return new Call(download).execute();
    }
}