package com.zhejianglab.spring3web.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;

/**
 *
 * @author chenze
 * @date 2022/3/25
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /** 每次读取8kb */
    private static final int BUFFER_SIZE = 1024 * 8;

    /** 请求体 */
    private final String body;

    /**
     * 构造方法
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        BufferedReader reader = request.getReader();
        StringWriter writer = new StringWriter();
        int read;
        char[] buf = new char[BUFFER_SIZE];
        while( ( read = reader.read(buf) ) != -1 ) {
            writer.write(buf, 0, read);
        }
        this.body = writer.getBuffer().toString();
    }

    /**
     * 获取请求体
     *
     * @return 请求体
     */
    public String getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream()  {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read(){
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader(){
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
