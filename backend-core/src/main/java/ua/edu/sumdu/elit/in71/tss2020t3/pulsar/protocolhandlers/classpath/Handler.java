package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.protocolhandlers.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import org.apache.log4j.Logger;

/**
 * This class is an implementation of {@link URLStreamHandler} that provides
 * a support of handling {@link URL}s with {@code classpath} protocol
 *
 * @see ClassLoader
 * */
public class Handler extends URLStreamHandler {

    public static final String PROTOCOL_NAME = "classpath";

    private static final Logger LOGGER = Logger.getLogger(Handler.class);

    private final ClassLoader classLoader;

    /**
     * Builds a {@link Handler} with provided {@link ClassLoader}
     *
     * @param classLoader   a {@link ClassLoader} that will be used
     *                      for searching a resource
     * */
    public Handler(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Builds a {@link Handler} with a {@link ClassLoader}
     * of {@link Handler} class
     * */
    public Handler() {
        classLoader = getClass().getClassLoader();
    }

    /**
     * Opens a {@link URLConnection} to resource identified by the {@code url}
     *
     * @param       u   a {@link URL} instance which protocol equals to
     *                  {@link #PROTOCOL_NAME} and specifies a resource
     *                  that is located in the classpath
     * @throws          IOException if the {@link #classLoader} can not find
     *                  the resource specified by {@code u}
     * @exception       IllegalArgumentException if the {@code u} protocol
     *                  differs from the {@link #PROTOCOL_NAME}
     * */
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        if (!PROTOCOL_NAME.equalsIgnoreCase(u.getProtocol())) {
            throw new IllegalArgumentException(
                "Unsupported protocol. Expected " + PROTOCOL_NAME
                    + ", found " + u.getProtocol()
            );
        }
        URL url = classLoader.getResource(u.getPath());
        if (url != null) {
            return url.openConnection();
        } else {
            String errMsg = u + " is not found by " + classLoader;
            LOGGER.warn(errMsg);
            throw new IOException(errMsg);
        }
    }
}
