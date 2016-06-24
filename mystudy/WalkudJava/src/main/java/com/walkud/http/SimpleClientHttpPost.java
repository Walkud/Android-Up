package com.walkud.http;

import com.walkud.utils.IoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端模拟Post请求
 * Created by Walkud on 16/6/24.
 */
public class SimpleClientHttpPost {

    private Socket mClientSocket;
    private String url;
    private Map<String, String> mParams = new HashMap<>();

    public SimpleClientHttpPost(String url) {
        this.url = url;
    }

    public void addParam(String key, String value) {
        mParams.put(key, value);
    }

    public void exceute() {
        PrintStream outputStream = null;
        BufferedReader inputStream = null;
        try {
            mClientSocket = new Socket(this.url, SimpleHttpServer.HTTP_PORT);
            outputStream = new PrintStream(mClientSocket.getOutputStream());
            inputStream = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            final String boundary = "my_boundary_1";

            writeHeader(boundary, outputStream);

            writeParam(boundary, outputStream);

            outputStream.flush();
            waitResponse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.closeQuitly(outputStream);
            IoUtil.closeQuitly(inputStream);
            IoUtil.closeSocket(mClientSocket);
        }
    }

    /**
     * 写入头部参数
     *
     * @param boundary
     * @param outputStream
     */
    private void writeHeader(String boundary, PrintStream outputStream) {
        outputStream.println("POST /api/login/ HTTP/1.1");
        outputStream.println("content-length:123");
        outputStream.println("Host:" + this.url + ":" + SimpleHttpServer.HTTP_PORT);
        outputStream.println("Content-Type:multipart/form-data;boundary=" + boundary);
        outputStream.println("User-Agent:android");
        outputStream.println();
    }

    /**
     * 写入请求参数
     *
     * @param boundary
     * @param outputStream
     */
    private void writeParam(String boundary, PrintStream outputStream) {
        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            outputStream.println("--" + boundary);//请求参数起始行
            outputStream.println("Content-Disposition:form-data;name=" + entry.getKey());
            outputStream.println();
            outputStream.println(entry.getValue());
        }

        //结束符
        outputStream.println("--" + boundary + "--");
    }

    /**
     * 输出返回参数
     *
     * @param inputStream
     * @throws IOException
     */
    private void waitResponse(BufferedReader inputStream) throws IOException {
        System.out.println("请求结果:");
        String responseLine = inputStream.readLine();
        while (responseLine == null || responseLine.contains("HTTP")) {
            responseLine = inputStream.readLine();
        }

        //输出Response
        while ((responseLine = inputStream.readLine()) != null) {
            System.out.println(responseLine);
        }

    }

    public static void main(String[] args) {
        SimpleClientHttpPost httpPost = new SimpleClientHttpPost("localhost");

        httpPost.addParam("username", "walkudLove@gmail.com");
        httpPost.addParam("pwd", "waludPassword");
        httpPost.exceute();
    }
}
