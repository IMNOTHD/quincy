package com.piggy.quincy.utils.picture;

import io.grpc.*;

import java.util.concurrent.TimeUnit;

/**
 * @author IMNOTHD
 * @date 2020/10/11 23:36
 */
public class PictureSpiderClient {

    private final ManagedChannel channel;

    private final PictureSpiderGrpc.PictureSpiderBlockingStub pictureSpiderBlockingStub;

    public PictureSpiderClient(String host, int port) {

        channel = ManagedChannelBuilder.forAddress(host, port)
                .build();

        pictureSpiderBlockingStub = PictureSpiderGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    public PictureSpiderService.PictureUrlResponse pictureUrl(PictureSpiderService.PictureUrlRequest pictureUrlRequest) {
        PictureSpiderService.PictureUrlResponse pictureUrlResponse = pictureSpiderBlockingStub.pictureUrl(pictureUrlRequest);

        return pictureUrlResponse;
    }

    public static void main(String[] args) throws InterruptedException {
        PictureSpiderClient pictureSpiderClient = new PictureSpiderClient("img.impiggy.cn", 443);

        PictureSpiderService.PictureUrlRequest pictureUrlRequest = PictureSpiderService.PictureUrlRequest
                .newBuilder()
                .setType("pixiv")
                .setId("68396815")
                .build();

        PictureSpiderService.PictureUrlResponse pictureUrlResponse = pictureSpiderClient.pictureUrl(pictureUrlRequest);
        System.out.println(pictureUrlResponse);

        pictureSpiderClient.shutdown();
    }

}
