package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.*;
import response.ResponseHeader;
import utils.FileIoUtils;
import utils.IOUtils;

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
            DataOutputStream dos = new DataOutputStream(out);

            String line = bufferedReader.readLine();

            RequestLine requestLine = new RequestLine(line);

            RequestHeader requestHeader = new RequestHeader();
            addRequestHeader(bufferedReader, requestHeader);

            RequestBody requestBody = getRequestBody(requestHeader, bufferedReader);

            addUser(requestLine, requestBody, dos);


            login(requestLine, requestBody, dos);


            byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestLine.getName());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void addRequestHeader(BufferedReader bufferedReader, RequestHeader requestHeader) throws IOException {
        String line = bufferedReader.readLine();

        while (!line.isEmpty()) {
            requestHeader.add(line);
            line = bufferedReader.readLine();
        }
    }

    private RequestBody getRequestBody(RequestHeader requestHeader, BufferedReader bufferedReader) throws IOException {
        if (requestHeader.hasRequestBody()) {
            String body = IOUtils.readData(bufferedReader, requestHeader.getContentLength());
            logger.debug("body : {}", body);

            return new RequestBody(body);
        }

        return new RequestBody("");
    }

    private void addUser(RequestLine requestLine, RequestBody requestBody, DataOutputStream dos) {
        Path path = requestLine.getPath();
        HttpMethod httpMethod = requestLine.getHttpMethod();
        if (!validateUserRequest(path, httpMethod)) return;

        User user = UserBinder.from(requestBody.getParameters());
        logger.debug("user = {}", user);

        DataBase.addUser(user);

        response302Header(dos);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean validateUserRequest(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isPost()) return false;
        if (!path.isUser()) return false;
        return path.isCreate();
    }

    private void login(RequestLine requestLine, RequestBody requestBody, DataOutputStream dos) {
        if (!validateLoginRequest(requestLine.getPath(), requestLine.getHttpMethod())) return;

        User user = UserBinder.from(requestBody.getParameters());

        boolean isAuthenticated = Optional.ofNullable(DataBase.findUserById(user.getUserId()))
            .map(it -> it.getPassword().equals(user.getPassword()))
            .orElse(false);

        if (!isAuthenticated) {
            ResponseHeader responseHeader = new ResponseHeader();
            responseHeader.add("Location", "/user/login_failed.html");
            responseHeader.add("Set-Cookie", "logined=false; Path=/");
            response302Header(dos, responseHeader);
            return;
        }

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.add("Location", "/index.html");
        responseHeader.add("Set-Cookie", "logined=true; Path=/");
        response302Header(dos, responseHeader);
    }

    private void response302Header(DataOutputStream dos, ResponseHeader responseHeader) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            for (String header : responseHeader.getHeaders().keySet()) {
                dos.writeBytes(header + ": " + responseHeader.get(header) + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateLoginRequest(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isPost()) return false;
        if (!path.isUser()) return false;
        return path.isLogin();
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
