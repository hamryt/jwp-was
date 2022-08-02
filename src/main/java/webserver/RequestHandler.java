package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestHeader;
import request.RequestLine;
import request.UserBinder;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = bufferedReader.readLine();

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            RequestLine requestLine = new RequestLine(line);
            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestLine.getName());

            RequestHeader requestHeader = new RequestHeader();

            while (!line.isEmpty()) {
                logger.debug("header: {}", line);
                requestHeader.add(line);
                line = bufferedReader.readLine();
            }
            addUser(requestLine);

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void addUser(RequestLine requestLine) {
        User user = UserBinder.from(requestLine);
//        DataBase.addUser(user);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
