package hello.proxy.blog.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerInterfaceImpl implements ServerInterface {

    @Override
    public void operation() {
        log.info("ServerInterfaceImpl start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("serverInterfaceImpl end");
    }
}
