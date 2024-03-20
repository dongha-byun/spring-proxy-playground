package hello.proxy.blog.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DataClient {
    private final DataInterface dataInterface;

    public void execute() {
        dataInterface.getData();
    }
}
