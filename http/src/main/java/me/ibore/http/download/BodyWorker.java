package me.ibore.http.download;

import me.ibore.http.Response;
import me.ibore.http.connect.http.Call;

import java.io.IOException;

public class BodyWorker extends BasicWorker<BodyDownload> {

    BodyWorker(BodyDownload download) {
        super(download);
    }

    @Override
    protected Response requestNetwork(BodyDownload download) throws IOException {
        return new Call(download).execute();
    }
}