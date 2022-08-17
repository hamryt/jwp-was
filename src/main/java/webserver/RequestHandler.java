package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.*;
import response.HttpResponse;
import response.ResponseHeader;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.handler.CreateUserController;
import webserver.handler.LoginController;
import webserver.handler.UserListController;

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

            if (validateUserRequest(requestLine.getPath(), requestLine.getHttpMethod())) {
                HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
                CreateUserController createUserController = new CreateUserController();
                HttpResponse response = createUserController.handle(httpRequest);

                response.write(dos);
                return;
            }

            if (validateLoginRequest(requestLine.getPath(), requestLine.getHttpMethod())) {
                HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
                LoginController loginController = new LoginController();
                HttpResponse response = loginController.handle(httpRequest);

                response.write(dos);
                return;
            }

            if (validateUserList(requestLine.getPath(), requestLine.getHttpMethod())) {
                final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);
                final UserListController createUserController = new UserListController();
                final HttpResponse response = createUserController.handle(httpRequest);

                response.write(dos);
                return;
            }

            String fileExtension = FileIoUtils.getFileExtension(requestLine.getName());
            String contentType = ContentType.of(fileExtension).getMediaType();

            byte[] body = getBody(requestLine);

            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(RequestLine requestLine) throws IOException, URISyntaxException {
        byte[] body;

        if (requestLine.getPath().isTemplate()) {
            body = FileIoUtils.loadFileFromClasspath("templates" + requestLine.getName());
        } else {
            body = FileIoUtils.loadFileFromClasspath("static" + requestLine.getName());
        }

        return body;
    }

    private boolean validateUserList(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isGet()) return false;
        if (!path.isUser()) return false;
        return path.isList();
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

    private boolean validateUserRequest(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isPost()) return false;
        if (!path.isUser()) return false;
        return path.isCreate();
    }

    private boolean validateLoginRequest(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isPost()) return false;
        if (!path.isUser()) return false;
        return path.isLogin();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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
