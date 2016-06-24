package com.walkud.http;

import com.walkud.utils.IoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Http 服务端Socket
 * Created by Walkud on 16/6/24.
 */
public class SimpleHttpServer extends Thread {

    public static final int HTTP_PORT = 20000;//Http监听端口

    ServerSocket mServerSocket;//服务端Socket


    public SimpleHttpServer() {
        try {
            mServerSocket = new ServerSocket(HTTP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            while (true) {//无线循环接受http请求
                System.out.println("开始监听请求");//输出日志

                //开启线程处理接受的http请求,在这里accept()方法会处于阻塞,直到收到http请求
                new DliverThread(mServerSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端接受请求处理类
     */
    private class DliverThread extends Thread {

        private Socket mClientSocket;//传入的Socket对象
        private BufferedReader mInputStream;//输入流
        private PrintStream mOutputStream;//输出流
        String httpMethod;//请求方法(Get,Post)
        String subPath;//子路径
        String boundary;//分隔符
        Map<String, String> mHeaders = new HashMap<>();//头部参数集合
        Map<String, String> mParams = new HashMap<>();//数据参数集合
        private boolean isParseHeader = false;//是否已经解析完Header

        public DliverThread(Socket socket) {
            this.mClientSocket = socket;
        }

        @Override
        public void run() {

            try {
                mInputStream = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                mOutputStream = new PrintStream(mClientSocket.getOutputStream());
                //解析请求
                parseRequest();

                //返回响应
                handleReponse();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IoUtil.closeQuitly(mInputStream);
                IoUtil.closeQuitly(mOutputStream);
                IoUtil.closeSocket(mClientSocket);
            }

        }

        /**
         * 解析请求
         */
        private void parseRequest() throws IOException {
            String line;
            int lineNum = 0;

            while ((line = mInputStream.readLine()) != null) {

                //是否为请求第一行
                if (lineNum == 0) {
                    parseRequestLine(line);
                }

                //是否为结束符
                if (isEnd(line)) {
                    break;
                }

                //解析Header参数
                if (lineNum != 0 && !isParseHeader) {
                    parseHeaders(line);
                }

                //解析请求参数
                if (isParseHeader) {
                    parseRequestParams(line);
                }

                lineNum++;
            }

        }

        /**
         * 是否为数据结束行
         *
         * @param line
         * @return
         */
        private boolean isEnd(String line) {
            Pattern pattern = Pattern.compile("^--([\\w\\W]*)--$");
            Matcher m = pattern.matcher(line);
            return m.matches();
        }

        /**
         * 解析请求第一行
         *
         * @param firstLine
         */
        private void parseRequestLine(String firstLine) {
            String[] tempStrings = firstLine.split(" ");
            httpMethod = tempStrings[0];
            subPath = tempStrings[1];
            System.out.println("请求方式:" + httpMethod);
            System.out.println("请求子路径:" + subPath);
            System.out.println("Http版本:" + tempStrings[2]);
        }

        /**
         * 解析头部参数
         *
         * @param headerLine
         */
        private void parseHeaders(String headerLine) {

            if (headerLine.equals("")) {
                isParseHeader = true;
                System.out.println("----->>>>>header解析完成");
                return;
            } else if (headerLine.contains("boundary")) {//解析Header
                boundary = parseSecondField(headerLine);
                System.out.println("分隔符:" + boundary);
            } else {
                //解析普通Header参数
                parseHeaderParam(headerLine);
            }

        }

        /**
         * 解析Header中的第二个参数
         *
         * @param line
         */
        private String parseSecondField(String line) {
            String[] headerArray = line.split(";");
            parseHeaderParam(headerArray[0]);
            if (headerArray.length > 1) {
                return headerArray[1].split("=")[1];
            }
            return "";
        }

        /**
         * 解析单个Header
         *
         * @param headerLine
         */
        private void parseHeaderParam(String headerLine) {
            String[] keyValue = headerLine.split(":");
            mHeaders.put(keyValue[0], keyValue[1]);
            System.out.println("header参数名:" + keyValue[0] + ",header参数值:" + keyValue[1]);
        }

        /**
         * 解析请求参数
         *
         * @param paramLine
         */
        private void parseRequestParams(String paramLine) throws IOException {
            //是否为参数开头
            if (paramLine.equals("--" + boundary)) {
                //解析参数名
                String contentDispoition = mInputStream.readLine();
                String paramName = parseSecondField(contentDispoition);
                //读取空行
                mInputStream.readLine();

                //解析参数值
                String paramValue = mInputStream.readLine();
                mParams.put(paramName, paramValue);
                System.out.println("参数名:" + paramName + ",参数值" + paramValue);
            }
        }

        /**
         * 请求响应
         */
        private void handleReponse() {
            mOutputStream.println("HTTP/1.1 200 OK");
            mOutputStream.println("Content-Type:application/json");
            mOutputStream.println();
            mOutputStream.println("{\"StatusCode\":\"success\"}");
        }
    }

    public static void main(String[] args) {
        new SimpleHttpServer().start();
    }
}
