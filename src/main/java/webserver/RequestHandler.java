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
import webserver.handler.*;
import webserver.session.HttpSessionIdHolder;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String SESSION_COOKIE_NAME = "JWP_SESSION_ID";

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

            HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);
            Controller controller = RequestMapping.map(httpRequest.getRequestLine());
            HttpResponse httpResponse = controller.handle(httpRequest)
                .addCookie(SESSION_COOKIE_NAME, HttpSessionIdHolder.getSessionId());

            httpResponse.write(dos);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
