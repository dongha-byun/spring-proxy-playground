package hello.proxy.blog.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClientObject {
    private final ServerInterface serverInterface;

    public void execute() {
        log.info("ClientObject execute start");
        serverInterface.operation();
        log.info("ClientObject execute end");
    }
}
